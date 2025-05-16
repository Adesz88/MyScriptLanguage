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
    public double exec(RuntimeContext ctx) throws Exception {

        Function func = ctx.function.get(name);

        ArrayList<Double> argValues = new ArrayList<Double>();
        for (Node arg : args) {
            argValues.add(arg.exec(ctx));
        }

        return func.call(ctx, argValues);
    }
}
