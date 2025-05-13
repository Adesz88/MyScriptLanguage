import ast.ExternalFunction;
import ast.RuntimeContext;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        // Example input from string
        //print(a) ra nem jo
        String src = """
            #a = 5 ^ min(2, 1) nem jo
            #a = TIME
            #scan(a)
            a = 1
            if (a == 2) {
                print(200)
            }
            a = (a == 5) ? 10 : abs(-1)
            print(a)
            del a
            
            scan(a)
            switch (a) {
                case 1:
                  print(1)
                  break
                  
                case 2:
                  print(2)
                  #break
                  
                default:
                  print(500)
                  break
            }
        """;

        CharStream inputStream = CharStreams.fromString(src);
        
        TemplateLexer lex = new TemplateLexer(inputStream);
        CommonTokenStream tokens = new CommonTokenStream (lex);
        TemplateParser parser = new TemplateParser(tokens);

        ast.Node node = parser.start().node;

        System.out.println("=" + node.toString());

        RuntimeContext ctx = new RuntimeContext();
        ctx.variables.put(RuntimeContext.MEMORY, -1111.0);
        ast.ExternalFunction debugPrint = new ExternalFunction("print") {
            @Override
            public double call(RuntimeContext ctx, ArrayList<Double> argValues) {
                System.out.printf("[PRINT] %s\n", argValues);
                return 0;
            }
        };
        debugPrint.exec(ctx);

        System.out.println("R:\n" + node.exec(ctx));
        System.out.println(ctx.variables);

        /*ast.Node root = new ast.BinaryOp("+", new ast.Constant("1"), new ast.Constant("2"));
        System.out.println("R ---> " + root.toString());*/
    }
}
