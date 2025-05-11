package ast;

public enum NodeType {
    INVALID,
    CONST,
    BINARYOP,
    UNARYOP,
    MEMORY_ACCESS,
    MEMORY_ASSIGN,
    MEMORY_DELETE,
    NODE_LIST,
    FUNCTION,
    FUNCTION_CALL,
    IF_ELSE,
    WHILE
}
