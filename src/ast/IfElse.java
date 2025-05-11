package ast;

public class IfElse extends Node {
    public Node condition;
    public NodeList trueCase;
    public NodeList falseCase;

    public IfElse(Node condition, NodeList trueCase, NodeList falseCase) {
        super(NodeType.IF_ELSE);
        this.condition = condition;
        this.trueCase = trueCase;
        this.falseCase = falseCase;
    }

    @Override
    public String toString() {
        return String.format("IfElse(%s, %s, %s)", condition.toString(), trueCase.toString(), String.valueOf(falseCase));
    }

    @Override
    public double exec(RuntimeContext ctx) {
        double conditionValue = condition.exec(ctx);
        if (conditionValue != 0.0) {
            return trueCase.exec(ctx);
        } else {
            return falseCase.exec(ctx);
        }
    }
}
