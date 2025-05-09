package ast;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class RuntimeContext {
    public final static String MEMORY = "M";

    public Map<String, Double> variables;
    //public double memory = 0;
    public Map<String, Function> function;
    public Stack<Map<String, Double>> scope;

    public RuntimeContext() {
        scope = new Stack<Map<String, Double>>();
        variables = new HashMap<>();
        function = new HashMap<>();
    }

    public void addFunction(Function func) {
        this.function.put(func.name, func);
    }

    public void newFrame() {
        scope.push(variables);
        variables = new HashMap<String, Double>();
    }

    public void popFrame() {
        variables = scope.pop();
    }

    public double getVariable(String name) {
        Double local = variables.getOrDefault(name, null);
        if (local == null) {
            local = scope.getFirst().get(name);
        }
        return local;
    }
}
