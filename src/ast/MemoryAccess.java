package ast;

public class MemoryAccess extends Node{
    public String varName;

    public MemoryAccess(String name) {
        super(NodeType.MEMORY_ACCESS);
        varName = name;
    }

    @Override
    public String toString() {
        return String.format("MemoryAccess(%s)", varName);
    }

    @Override
    public double exec(RuntimeContext ctx) throws Exception{
        if ("TIME".equals(varName)) {
            return (double) System.currentTimeMillis() / 1000L;
        }
        try {
            return ctx.getVariable(varName);
        } catch (Exception e) {
            throw new Exception("Accessing undeclared variable");
        }
    }
}
