package ast;

import java.util.ArrayList;

public class Function extends Node{
    public String name;
    public ArrayList<String> params;
    public Node body;

    public Function(String name, ArrayList<String> ids, Node body) {
        super(NodeType.FUNCTION);
        this.name = name;
        params = ids;
        this.body = body;
    }

    @Override
    public String toString() {
        return String.format("Function(%s)", name);
    }

    @Override
    public double exec(RuntimeContext ctx) {
        ctx.addFunction(this);
        return Double.NaN;
    }

    public double call(RuntimeContext ctx, ArrayList<Double> argValues) {
        ctx.newFrame();
        for (int i = 0; i < params.size(); i++) {
            ctx.variables.put(params.get(i), argValues.get(i));
        }
        double value = body.exec(ctx);
        ctx.popFrame();
        return value;
    }
}
