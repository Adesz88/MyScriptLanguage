import ast.RuntimeContext;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

public class Main {

    public static void main(String[] args) {
        // Example input from string
        //print(a) ra nem jo
        String src = """
            a = 2
            if (a - 2) {
                a = 8
               
            } else {
                a = 7
                #print(a)
            }
            b = a + 4
            b + 1
            a = 1
            while(a < 2) {
                a = a + 1
            }
            
            for (a = 1; a < 4; a) {
                a = a + 1
            }
            print(2 + 3)
        """;

        CharStream inputStream = CharStreams.fromString(src);
        
        TemplateLexer lex = new TemplateLexer(inputStream);
        CommonTokenStream tokens = new CommonTokenStream (lex);
        TemplateParser parser = new TemplateParser(tokens);

        ast.Node node = parser.start().node;

        System.out.println("=" + node.toString());

        RuntimeContext ctx = new RuntimeContext();
        ctx.variables.put(RuntimeContext.MEMORY, -1111.0);

        System.out.println("R:\n" + node.exec(ctx));

        /*ast.Node root = new ast.BinaryOp("+", new ast.Constant("1"), new ast.Constant("2"));
        System.out.println("R ---> " + root.toString());*/
    }
}
