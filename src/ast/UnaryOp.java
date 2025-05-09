package ast;

public class UnaryOp extends Node{
    public String op;
    public Node argument;

    public UnaryOp(String op, Node argument) {
        super(NodeType.UNARYOP);
        this.op = op;
        this.argument = argument;
    }

    @Override
    public String toString() {
        return String.format("UnaryOp(%s, %s)", op, argument.toString());
    }

    @Override
    public double exec(RuntimeContext ctx) {
        double value = argument.exec(ctx);
        switch (op) {
            case "+":
                return value;
            case "-":
                return -1 * value;
            case  "abs":
                return Math.abs(value);
            default:
                return Double.NaN;
        }
    }
}
