package ast;

public enum NodeType {
    INVALID,
    CONST,
    BINARYOP,
    UNARYOP,
    MEMORY_DECLARE,
    MEMORY_ACCESS,
    MEMORY_ASSIGN,
    MEMORY_DELETE,
    NODE_LIST,
    FUNCTION,
    FUNCTION_CALL,
    IF_ELSE,
    SWITCH,
    CASE,
    WHILE,
    TERNARY,
    SCAN
}
