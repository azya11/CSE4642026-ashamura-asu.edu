package graph;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Set;
import java.util.TreeSet;

public class Graph {
    private final Set<String> nodes;
    private final Set<String> edges;

    public Graph() {
        this.nodes = new TreeSet<>();
        this.edges = new TreeSet<>();
    }

    public Set<String> getNodes() {
        return nodes;
    }

    public Set<String> getEdges() {
        return edges;
    }

    public int getNodeCount() {
        return nodes.size();
    }

    public int getEdgeCount() {
        return edges.size();
    }

    public void addNode(String label) {
        if (label == null || label.trim().isEmpty()) {
            throw new IllegalArgumentException("Node label cannot be null or empty.");
        }
        nodes.add(label.trim());
    }

    public void addNodes(String[] labels) {
        if (labels == null) {
            throw new IllegalArgumentException("Labels array cannot be null.");
        }
        for (String label : labels) {
            addNode(label);
        }
    }

    protected void addNodeInternal(String label) {
        nodes.add(label);
    }

    protected void addEdgeInternal(String src, String dst) {
        nodes.add(src);
        nodes.add(dst);
        edges.add(src + "->" + dst);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Number of nodes: ").append(getNodeCount()).append(System.lineSeparator());
        sb.append("Nodes: ").append(nodes).append(System.lineSeparator());
        sb.append("Number of edges: ").append(getEdgeCount()).append(System.lineSeparator());
        sb.append("Edges:").append(System.lineSeparator());

        for (String edge : edges) {
            String[] parts = edge.split("->");
            sb.append(parts[0]).append(" -> ").append(parts[1]).append(System.lineSeparator());
        }

        return sb.toString();
    }

    public void outputGraph(String filepath) throws IOException {
        Files.writeString(Paths.get(filepath), toString());
    }
}