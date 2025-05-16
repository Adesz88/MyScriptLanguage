package ast;

public class MemoryDeclare extends Node {
    public String varName;
    public Node value;

    public MemoryDeclare(String name) {
        super(NodeType.MEMORY_DECLARE);
        varName = name;
        this.value = new Constant("0");
    }

    public MemoryDeclare(String name, Node value) {
        super(NodeType.MEMORY_DECLARE);
        varName = name;
        this.value = value;
    }

    @Override
    public String toString() {
        return String.format("MemoryDeclare(%s, %s)", varName, value.toString());
    }

    @Override
    public double exec(RuntimeContext ctx) throws Exception {
        if (!ctx.variables.containsKey(varName)) {
            double value = this.value.exec(ctx);
            ctx.variables.put(varName, value);
            return value;
        } else {
            throw new Exception("Redeclaring variable");
        }
    }
}
