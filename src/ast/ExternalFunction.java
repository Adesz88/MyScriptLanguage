package ast;

import java.util.ArrayList;

public abstract class ExternalFunction extends Function{
    public ExternalFunction(String name) {
        super(name, null, null);
    }

    @Override
    public String toString() {
        return String.format("ExternalFunction(%s)", this.name);
    }

    @Override
    public double call(RuntimeContext ctx, ArrayList<Double> argValues) {
        return super.call(ctx, argValues);
    }
}
