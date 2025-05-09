package ast;

public abstract class Node {
    public NodeType type = NodeType.INVALID;

    public Node(NodeType type) {
        this.type = type;
    }

    public abstract String toString();

    public abstract double exec(RuntimeContext ctx);
}
