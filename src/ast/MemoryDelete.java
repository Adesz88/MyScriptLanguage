package ast;

public class MemoryDelete extends Node{
    public String varName;

    public MemoryDelete(String name) {
        super(NodeType.MEMORY_DELETE);
        varName = name;
    }

    @Override
    public String toString() {
        return String.format("MemoryDelete(%s)", varName);
    }

    @Override
    public double exec(RuntimeContext ctx) throws Exception {
        ctx.variables.remove(varName);
        return 0;
    }
}
