package ast;

public class MemoryDelete extends Node{
    public String varName;

    public MemoryDelete(String name) {
        super(NodeType.MEMORY_DELETE);
        varName = name;
    }

    @Override
    public String toString() {
        return "";
    }

    @Override
    public double exec(RuntimeContext ctx) {
        ctx.variables.remove(varName);
        return 0;
    }
}
