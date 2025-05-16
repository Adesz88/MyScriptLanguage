package ast;

public class Case extends Node{
    public Node value;
    public NodeList body;
    public boolean def;
    private boolean br;

    public Case(boolean def, Node value, NodeList body, boolean br) {
        super(NodeType.CASE);
        this.def = def;
        this.value = value;
        this.body = body;
        this.br = br;
    }

    @Override
    public String toString() {
        return "Case";
    }

    @Override
    public double exec(RuntimeContext ctx) throws Exception {
        body.exec(ctx);
        return br ? 1.0 : 0.0;
    }
}
