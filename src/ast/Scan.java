package ast;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Scan extends Node{
    public List<String> args;

    public Scan(List<String> args) {
        super(NodeType.SCAN);
        this.args = args;
    }

    @Override
    public String toString() {
        String result = "Scan(";
        for (String arg : args) {
            result += arg;
            result += ", ";
        }
        result += ")";
        return result;
    }

    @Override
    public double exec(RuntimeContext ctx) {
        double number = 0.0;
        try {
            System.out.println("Enter a number");
            Scanner in = new Scanner(System.in);
            number = in.nextDouble();
        } catch (InputMismatchException e) { }

        for (int i = 0; i < args.size(); i++) {
            ctx.variables.put(args.get(i), number);
        }
        return Double.NaN;
    }
}
