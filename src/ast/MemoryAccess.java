package ast;

public class MemoryAccess extends Node{
    // public static double MEMORY = 0;
    public String varName;

    public MemoryAccess(String name) {
        super(NodeType.MEMEORY_ACCESS);
        varName = name;
    }

    @Override
    public String toString() {
        return String.format("MemoryAccess(%s)", varName);
    }

    @Override
    public double exec(RuntimeContext ctx) {
        return ctx.getVariable(varName);
    }
}
