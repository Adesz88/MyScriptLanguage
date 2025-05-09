grammar Template;

// Lexer/Parser should be generated with (in the src/ dir)
// $ java -jar ../antlr-4.11.1-complete.jar Template.g4

options {
    language = Java;
}

@parser::members {
    //private double memorySlot = 0.0;
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
        ( (item=line { nodeList.add($item.node); } )? COMMENT? LF)*
        EOF { $node = nodeList; }
    ;

line returns [ast.Node node]
    : ID '=' arg=expr { $node = new ast.MemoryAssign($ID.text, $arg.node); }
    | val=expr { $node = $val.node; }
    | KW_FUNC fname=ID '(' arglist ')' 'returns' body=expr { $node = new ast.Function($fname.text, $arglist.ids, $body.node); }

    | KW_IF '(' cond=expr ')' trueCase=sequence
      (KW_ELSE falseCase=sequence)? { $node = new ast.IfElse($cond.node, $trueCase.nodeList, $falseCase.nodeList); }
    | KW_WHILE '(' whileCond=expr ')' whileBody=sequence { $node = new ast.While($whileCond.node, $whileBody.nodeList); }
    | KW_FOR '(' ID '=' initValue=expr ';' forCond=expr ';' postOp=expr ')' forBody=sequence
        {
            $node = ast.While.buildFor($ID.text, $initValue.node, $forCond.node, $postOp.node, $forBody.nodeList);
        }
    ;

sequence returns [ast.NodeList nodeList]
    : { $nodeList = new ast.NodeList(); }
        '{' LF*
            (item=line { $nodeList.add($item.node); } LF* )*

        '}'
    ;

arglist returns [java.util.ArrayList<String> ids]
    :                   { $ids = new java.util.ArrayList<>(); }
    |                   { $ids = new java.util.ArrayList<>(); }
        arg1=ID         { $ids.add($arg1.text); }
        ( ',' arg2=ID   { $ids.add($arg2.text); }
        )*
    ;

expr returns [ast.Node node]
    : fstop=addop { $node = $fstop.node; } (OPADD nxtop=addop { $node = new ast.BinaryOp($OPADD.text, $node, $nxtop.node); })*
    ;

addop returns [ast.Node node]
    : fstop=mulop { $node = $fstop.node; } (OPMUL nxtop=mulop { $node = new ast.BinaryOp($OPMUL.text, $node, $nxtop.node); })*
    ;

mulop returns [ast.Node node]
    : fstop=fct { $node = $fstop.node; } (OPPWR nxtop=mulop { $node = new ast.BinaryOp($OPPWR.text, $node, $nxtop.node); })?
    ;

fct returns [ast.Node node]
    : SZAM { $node = new ast.Constant($SZAM.text); }
    | '(' expr ')' { $node = $expr.node; }
    | 'abs' '(' arg1=expr ')' { $node = new ast.UnaryOp("abs", $arg1.node); }
    | OPMINMAX '(' fstop=expr { $node = $fstop.node; }
        (',' nxtop=expr  { $node = new ast.BinaryOp($OPMINMAX.text, $node, $nxtop.node); }) * ')'
    | OPADD arg=fct { $node = new ast.UnaryOp($OPADD.text, $arg.node); }
    | ID { $node = new ast.MemoryAccess($ID.text); }
    | ID '(' callargs ')' { $node = new ast.FunctionCall($ID.text, $callargs.args); }
    ;

callargs returns [java.util.ArrayList<ast.Node> args]
    :               { $args = new java.util.ArrayList<ast.Node>(); }
    | first=expr    { $args = new java.util.ArrayList<ast.Node>();
                      $args.add($first.node);
                    }
      ( ',' more=expr { $args.add($more.node); }
      )*
    ;

LF       : '\n' ;
WS       : [ \t\r]+ ->skip ;
SZAM     : [0-9]+('.' [0-9]+)? ;
OPADD    : '+' | '-'  | '<' | '>';
OPMUL    : '*' | '/' ;
OPPWR    : '^' ;

OPMINMAX : 'min' | 'max' ;
COMMENT  : '#' (~[\n])* ;
KW_FUNC  : 'func';
KW_IF    : 'if';
KW_ELSE  : 'else';
KW_WHILE : 'while';
KW_FOR   : 'for';
ID       : [a-zA-Z_][a-zA-Z_0-9]*;