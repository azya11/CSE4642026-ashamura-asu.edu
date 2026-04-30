package graph;

import java.util.ArrayList;

public class Path {
    public ArrayList<Node> nodes = new ArrayList<>();

    public Path() {
    }

    public Path(Path other) {
        for (Node node : other.nodes) {
            nodes.add(new Node(node.label));
        }
    }

    public void addNode(Node n) {
        nodes.add(n);
    }

    public Node getLastNode() {
        if (nodes.isEmpty()) {
            return null;
        }
        return nodes.get(nodes.size() - 1);
    }

    public Path copyAndAppend(Node n) {
        Path copied = new Path(this);
        copied.addNode(new Node(n.label));
        return copied;
    }

    public String toDebugString() {
        StringBuilder sb = new StringBuilder("Path{nodes=[");
        for (int i = 0; i < nodes.size(); i++) {
            sb.append("Node{").append(nodes.get(i).label).append("}");
            if (i < nodes.size() - 1) {
                sb.append(", ");
            }
        }
        sb.append("]}");
        return sb.toString();
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
