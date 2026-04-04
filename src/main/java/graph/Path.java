package graph;

import java.util.ArrayList;

public class Path {
    public ArrayList<Node> nodes = new ArrayList<>();

    public void addNode(Node n) {
        nodes.add(n);
    }

    @Override
    public String toString() {
        String result = "";
        for (int i = 0; i < nodes.size(); i++) {
            result += nodes.get(i).label;
            if (i < nodes.size() - 1)
                result += " -> ";
        }
        return result;
    }
}
