package ast;

public class Ternary extends Node{
    public Node condition;
    public Node trueExpr;
    public Node falseExpr;

    public Ternary(Node condition, Node trueExpr, Node falseExpr) {
        super(NodeType.TERNARY);
        this.condition = condition;
        this.trueExpr = trueExpr;
        this.falseExpr = falseExpr;
    }


    @Override
    public String toString() {
        return String.format("Ternary(%s, %s, %s)", condition.toString(), trueExpr.toString(), falseExpr.toString());
    }

    @Override
    public double exec(RuntimeContext ctx) throws Exception {
        double conditionValue = condition.exec(ctx);
        if (conditionValue != 0.0) {
            return trueExpr.exec(ctx);
        } else {
            return falseExpr.exec(ctx);
        }
    }
}
