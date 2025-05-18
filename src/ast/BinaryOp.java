package ast;

public class BinaryOp extends Node{
    String op;
    Node lhs;
    Node rhs;

    public BinaryOp(String op, Node lhs, Node rhs) {
        super(NodeType.BINARYOP);
        this.op = op;
        this.lhs = lhs;
        this.rhs = rhs;
    }

    @Override
    public String toString() {
        return String.format("BinaryOp(%s %s %s)", op, lhs.toString(), rhs.toString());
    }

    @Override
    public double exec(RuntimeContext ctx) throws Exception {
        double lhs = this.lhs.exec(ctx);
        double rhs = this.rhs.exec(ctx);
        switch (op) {
            case "+":
                return lhs + rhs;
            case "-":
                return lhs - rhs;
            case "*":
                return lhs * rhs;
            case "/":
                return lhs / rhs;
            case "^":
                return Math.pow(lhs, rhs);
            case "min":
                return Math.min(lhs, rhs);
            case "max":
                return Math.max(lhs, rhs);
            case "<":
                return (lhs < rhs) ? 1.0 : 0.0;
            case ">":
                return (lhs > rhs) ? 1.0 : 0.0;
            case "<=":
                return (lhs <= rhs) ? 1.0 : 0.0;
            case ">=":
                return (lhs >= rhs) ? 1.0 : 0.0;
            case "==":
                return (lhs == rhs) ? 1.0 : 0.0;
            case "!=":
                return (lhs != rhs) ? 1.0 : 0.0;
            default:
                return Double.NaN;
        }
    }
}
