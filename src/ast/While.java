package ast;

public class While extends Node{
    public Node condition;
    public NodeList body;

    public While(Node condition, NodeList body) {
        super(NodeType.WHILE);
        this.condition = condition;
        this.body = body;
    }

    @Override
    public String toString() {
        return String.format("While(%s, {%s})\n", condition.toString(), body.toString());
    }

    @Override
    public double exec(RuntimeContext ctx) {
        while (condition.exec(ctx) != 0.0) {
            body.exec(ctx);
        }
        return 0;
    }

    public static Node buildFor(String varName, Node initValue, Node forCond, Node postOp, NodeList body) {
        NodeList whileBody = new NodeList();
        whileBody.add(body);
        whileBody.add(postOp);
        While whileNode = new While(forCond, body);

        NodeList finalNode = new NodeList();
        finalNode.add(new MemoryAssign(varName, initValue));
        finalNode.add(whileNode);

        return finalNode;
    }
}
