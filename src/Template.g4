grammar Template;

// Lexer/Parser should be generated with (in the src/ dir)
// $ java -jar antlr-4.11.1-complete.jar Template.g4

options {
    language = Java;
}

@header {
import ast.*;
}

@parser::members {
// Commented out, just for example
/*
    public static void main(String[] args) throws Exception {
        // Example input from string
        CharStream inputStream = CharStreams.fromString("+ 1 2");

        TemplateLexer lex = new TemplateLexer(inputStream);
        CommonTokenStream tokens = new CommonTokenStream (lex);
        TemplateParser parser = new TemplateParser(tokens);
        System.out.println("=" + parser.start().toStringTree(parser));
    }
*/
}


start returns [ast.Node node]
    : { ast.NodeList nodeList = new ast.NodeList(); }
     (
        (item=line { nodeList.add($item.node); } )? COMMENT? LF
     )*
     EOF
     { $node = nodeList; }
    ;

line returns [ast.Node node]
    : ID '=' arg=expr       { $node = new ast.MemoryAssign($ID.text, $arg.node); }
    | DEL ID                { $node = new ast.MemoryDelete($ID.text); }
    | KW_SCAN '(' arglist ')' { $node = new ast.Scan($arglist.ids); }
    | val=expr              { $node = $val.node; }
    | KW_FUNC fname=ID '(' arglist ')' 'returns' body=expr
                            { $node = new ast.Function($fname.text, $arglist.ids, $body.node);  }

    | KW_IF '(' cond=expr ')' '{' trueCase=sequence '}' (KW_ELSE '{' falseCase=sequence '}')?
                            { $node = new ast.IfElse($cond.node, $trueCase.nodeList, $KW_ELSE != null ? $falseCase.nodeList : null); }

    | KW_SWITCH '(' expr ')' '{' switchBody '}' { $node = new ast.Switch($expr.node, $switchBody.cases); }

    | KW_WHILE '(' cond=expr ')' '{' whileBody=sequence '}'
                            { $node = new ast.While($cond.node, $whileBody.nodeList); }
    | KW_FOR '(' ID '=' init=expr ';' cond=expr ';' postOp=expr ')' '{' forBody=sequence '}'
                            { /* NOTE!: this is a bit of hack as the init, cond and postOp could be optional, and should allow almost anything
                                This is only used as an example for a "syntax sugar"
                              */
                            $node = ast.While.buildFor($ID.text, $init.node, $cond.node, $postOp.node, $forBody.nodeList);
                            }
    ;

switchBody returns [java.util.List<ast.Case> cases]
    : { $cases = new java.util.ArrayList<>(); }
      (LF* KW_CASE NUM':' sequence KW_BREAK?
        { ast.Node node = new ast.Constant($NUM.text);
            System.out.println($KW_BREAK); //bug it matches on the first in the second case when there is no break there
          boolean br = $KW_BREAK == null ? false : true;
          ast.Case caseNode = new Case(false, node, $sequence.nodeList, br);
          $cases.add(caseNode);
        }
      )*
      (LF* KW_DEFAULT':' sequence KW_BREAK?
        { ast.Node node = new ast.Constant("0");
           boolean br = $KW_BREAK == null ? false : true;
           ast.Case caseNode = new Case(true, node, $sequence.nodeList, br);
           $cases.add(caseNode);
        }
      )?
      LF*
    ;

sequence returns [ast.NodeList nodeList]
    : { $nodeList = new ast.NodeList(); }
    (
      LF*
      (item=line { $nodeList.add($item.node); } )? COMMENT? LF+
    )*
    ;

arglist returns [java.util.ArrayList<String> ids]
    :                                               { $ids = new java.util.ArrayList<>(); }
    |                                               { $ids = new java.util.ArrayList<>(); }
        arg1=ID                                     { $ids.add($arg1.text); }
        (',' arg2=ID                                { $ids.add($arg2.text); }
        )*
    ;

expr returns [ast.Node node]
    : fstop=relationop { $node = $fstop.node; }
      (OPRELATION nxtop=relationop { $node = new ast.BinaryOp($OPRELATION.text, $node, $nxtop.node); })*
    ;

relationop returns [ast.Node node]
    : fstop=addop { $node = $fstop.node; }
      (OPADD nxtop=addop { $node = new ast.BinaryOp($OPADD.text, $node, $nxtop.node); })*
    ;

addop returns [ast.Node node ]
    : fstop=mulop       { $node = $fstop.node; }
      (OPMUL nxtop=mulop { $node = new ast.BinaryOp($OPMUL.text, $node, $nxtop.node); })*
    ;

mulop returns [ast.Node node]
    : fstop=fct { $node = $fstop.node; }
        (OPPWR nxtop=mulop { $node = new ast.BinaryOp($OPPWR.text, $node, $nxtop.node); })?
    ;

fct returns [ast.Node node]
    : NUM                                        { $node = new ast.Constant($NUM.text); }
    | '(' expr ')'                                { $node = $expr.node; }
    | 'abs' '(' arg1=expr ')'                     { $node = new ast.UnaryOp("abs", $arg1.node); }
    | OPMINMAX '(' fstop=expr                     { $node = $fstop.node; }
        (',' nxtop=expr            { $node = new ast.BinaryOp($OPMINMAX.text, $node, $nxtop.node ); }
        ) *
        ')'
    | OPADD arg=fct                           { $node = new ast.UnaryOp($OPADD.text, $arg.node); }
    | '('cond=expr')' '?' trueExpr=expr ':' falseExpr=expr
                        { $node = new ast.Ternary($cond.node, $trueExpr.node, $falseExpr.node); }
    | KW_TIME                                 { $node = new ast.MemoryAccess($KW_TIME.text); }
    | ID                                      { $node = new ast.MemoryAccess($ID.text); }
    | ID '(' callargs ')'                     { $node = new ast.FunctionCall($ID.text, $callargs.args); }
    ;

callargs returns [java.util.ArrayList<ast.Node> args]
    :                                       { $args = new java.util.ArrayList<ast.Node>(); }
    |   first=expr                          { $args = new java.util.ArrayList<ast.Node>();
                                              $args.add($first.node);
                                             }
        (',' more=expr                      { $args.add($more.node); }
        )*
    ;

LF       : '\n' ;
LE       : ';';
WS       : [ \t\r]+ ->skip ;
NUM      : [0-9]+('.' [0-9]+)? ;
OPRELATION: '<' | '>' | '<=' | '>=' | '==' | '!=';
OPADD    : '+' | '-';
OPMUL    : '*' | '/' ;
OPPWR    : '^' ;
OPMINMAX : 'min' | 'max' ;
COMMENT  : '#' (~[\n])* ;
KW_TIME  : 'TIME';
KW_FUNC  : 'func';
KW_SCAN  : 'scan';
KW_IF    : 'if';
KW_ELSE  : 'else';
KW_SWITCH: 'switch';
KW_CASE  : 'case';
KW_BREAK : 'break';
KW_DEFAULT: 'default';
KW_WHILE : 'while';
KW_FOR   : 'for';
DEL      : 'del';
ID       : [a-zA-Z_][a-zA-Z_0-9]*;
