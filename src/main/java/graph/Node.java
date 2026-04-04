package graph;

public class Node {
    public String label;

    public Node(String label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return label;
    }
}
