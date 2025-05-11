package ast;

import java.util.ArrayList;

public class FunctionCall extends Node {
    public String name;
    public ArrayList<Node> args;

    public FunctionCall(String name, ArrayList<Node> args) {
        super(NodeType.FUNCTION_CALL);
        this.name = name;
        this.args = args;
    }

    @Override
    public String toString() {
        return String.format("FunctionCall(%s, (%s))", name, args.toString());
    }

    @Override
    public double exec(RuntimeContext ctx) {
        /*if (name.equals("print")) {
            System.out.println("PRINT CALL, args: ");
            for (int i = 0; i < args.size(); i++) {
                System.out.println(args.get(i));
            }
            System.out.printf("[PRINT] %s\n", args.get(0).exec(ctx));
            return 0;
        }*/

        Function func = ctx.function.get(name);

        ArrayList<Double> argValues = new ArrayList<Double>();
        for (Node arg : args) {
            argValues.add(arg.exec(ctx));
        }

        double result = func.call(ctx, argValues);
        return result;
    }
}
