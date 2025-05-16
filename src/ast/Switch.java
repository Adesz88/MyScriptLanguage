package ast;

import java.util.List;

public class Switch extends Node{
    public Node expression;
    public List<Case> cases;

    public Switch(Node expression, List<Case> cases) {
        super(NodeType.SWITCH);
        this.expression = expression;
        this.cases = cases;
    }

    @Override
    public String toString() {
        return "Switch";
    }

    @Override
    public double exec(RuntimeContext ctx) throws Exception {
        double expressionValue = expression.exec(ctx);
        boolean br = true;
        boolean match = false;

        for (Case caseNode: cases) {
            double caseValue = caseNode.value.exec(ctx);
            if (!caseNode.def && caseValue == expressionValue) {
                match = true;
                break;
            }
        }

        for (Case caseNode: cases) {
            double caseValue = caseNode.value.exec(ctx);
            if (!caseNode.def && caseValue == expressionValue || !match && caseNode.def || !br) {
                br = caseNode.exec(ctx) == 1.0;
                if (br) {
                    return Double.NaN;
                }
            }
        }
        return Double.NaN;
    }
}
