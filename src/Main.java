import ast.ExternalFunction;
import ast.RuntimeContext;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

import java.util.ArrayList;

public class Main {

    public static void main(String[] args) throws Exception {
        // Example input from string
        String src = """
            double b
            function triple_pow(a, b) returns 3*a^b
            
            scan(c)
            b = (c < 0) ? min(-10, c) : triple_pow(c, 3)
            print(b)
        """;

        CharStream inputStream = CharStreams.fromString(src);
        
        TemplateLexer lex = new TemplateLexer(inputStream);
        CommonTokenStream tokens = new CommonTokenStream (lex);
        TemplateParser parser = new TemplateParser(tokens);

        ast.Node node = parser.start().node;

        System.out.println("=" + node.toString());

        RuntimeContext ctx = new RuntimeContext();
        ast.ExternalFunction debugPrint = new ExternalFunction("print") {
            @Override
            public double call(RuntimeContext ctx, ArrayList<Double> argValues) {
                System.out.printf("[PRINT] %s\n", argValues);
                return 0;
            }
        };
        debugPrint.exec(ctx);

        System.out.println("R:\n" + node.exec(ctx));
        System.out.println("Variables: " + ctx.variables);
    }
}
