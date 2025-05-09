package ast;

public class Constant extends Node {
    public String value;

    public Constant(String value) {
        super(NodeType.CONST);
        this.value = value;
    }

    @Override
    public String toString() {
        return String.format("Constant(%s)", value);
    }

    @Override
    public double exec(RuntimeContext ctx) {
        return Double.parseDouble(value);
    }
}
