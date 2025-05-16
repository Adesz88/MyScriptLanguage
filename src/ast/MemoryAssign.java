package ast;

public class MemoryAssign extends Node{
    public Node value;
    public String varName;

    public MemoryAssign(String name, Node value) {
        super(NodeType.MEMORY_ASSIGN);
        varName = name;
        this.value = value;
    }

    @Override
    public String toString() {
        return String.format("MemoryAssign(%s, %s)", varName, value.toString());
    }

    @Override
    public double exec(RuntimeContext ctx) throws Exception {
        if (ctx.variables.containsKey(varName)) {
            double value = this.value.exec(ctx);
            ctx.variables.put(varName, value);
            return value;
        } else {
            throw new Exception("Defining undeclared variable");
        }
    }
}
