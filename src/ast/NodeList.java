package ast;

import java.util.ArrayList;

public class NodeList extends Node{

    public ArrayList<Node> nodes;

    public NodeList() {
        super(NodeType.NODE_LIST);
        nodes = new ArrayList<>();
    }

    public void add(Node node) {
        nodes.add(node);
    }

    @Override
    public String toString() {
        String result = "NodeList: ";
        for (Node node: nodes) {
            result += node.toString();
            result += "\n";
        }
        result += ")";
        return result;
    }

    @Override
    public double exec(RuntimeContext ctx) throws Exception {
        for (int i = 0; i < nodes.size(); i++) {
            Node line = nodes.get(i);
            double value = line.exec(ctx);
            //System.out.format("[DEBUG]: %d: %f\n", i, value);
        }
        return Double.NaN;
    }
}
