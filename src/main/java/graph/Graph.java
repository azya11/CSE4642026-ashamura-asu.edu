package graph;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class Graph {
    private static final String EDGE_SEPARATOR = "->";

    private final Set<String> nodes;
    private final Set<String> edges;
    private final Map<String, SortedSet<String>> adjacency;
    private final GraphSearchEngine searchEngine;

    public Graph() {
        this.nodes = new TreeSet<>();
        this.edges = new TreeSet<>();
        this.adjacency = new TreeMap<>();
        this.searchEngine = new GraphSearchEngine();
    }

    public void registerStrategy(Algorithm algorithm, SearchStrategy strategy) {
        searchEngine.registerStrategy(algorithm, strategy);
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
        String node = label.trim();
        nodes.add(node);
        adjacency.computeIfAbsent(node, key -> new TreeSet<>());
    }

    public void addNodes(String[] labels) {
        if (labels == null) {
            throw new IllegalArgumentException("Labels array cannot be null.");
        }
        for (String label : labels) {
            addNode(label);
        }
    }

    public void addEdge(String srcLabel, String dstLabel) {
        if (srcLabel == null || dstLabel == null ||
                srcLabel.trim().isEmpty() || dstLabel.trim().isEmpty()) {
            throw new IllegalArgumentException("Source and destination labels cannot be null or empty.");
        }

        String src = srcLabel.trim();
        String dst = dstLabel.trim();
        String edge = buildEdge(src, dst);

        addNode(src);
        addNode(dst);
        if (edges.add(edge)) {
            adjacency.computeIfAbsent(src, key -> new TreeSet<>()).add(dst);
        }
    }

    public void removeNode(String label) {
        if (!nodes.contains(label)) {
            throw new RuntimeException("Node not found: " + label);
        }
        nodes.remove(label);
        adjacency.remove(label);
        ArrayList<String> temp = new ArrayList<>();
        for (String e : edges) {
            if (e.startsWith(label + EDGE_SEPARATOR) || e.endsWith(EDGE_SEPARATOR + label)) {
                temp.add(e);
            }
        }
        for (String edge : temp) {
            removeEdgeInternal(edge);
        }
        for (SortedSet<String> neighbors : adjacency.values()) {
            neighbors.remove(label);
        }
    }

    public void removeNodes(String[] labels) {
        for (String label : labels) {
            removeNode(label);
        }
    }

    public void removeEdge(String srcLabel, String dstLabel) {
        String edge = buildEdge(srcLabel, dstLabel);
        if (!edges.contains(edge)) {
            throw new RuntimeException("Edge not found: " + edge);
        }
        removeEdgeInternal(edge);
    }

    private void removeEdgeInternal(String edge) {
        edges.remove(edge);
        String[] parts = splitEdge(edge);
        if (parts.length == 2) {
            String src = parts[0];
            String dst = parts[1];
            SortedSet<String> neighbors = adjacency.get(src);
            if (neighbors != null) {
                neighbors.remove(dst);
            }
        }
    }

    private String buildEdge(String srcLabel, String dstLabel) {
        return srcLabel + EDGE_SEPARATOR + dstLabel;
    }

    private String[] splitEdge(String edge) {
        return edge.split(EDGE_SEPARATOR, 2);
    }

    public List<String> getNeighbors(String srcLabel) {
        SortedSet<String> neighbors = adjacency.get(srcLabel);
        if (neighbors == null) {
            return new ArrayList<>();
        }
        return new ArrayList<>(neighbors);
    }

    public Path GraphSearch(String src, String dst, Algorithm algo) {
        return GraphSearch(new Node(src), new Node(dst), algo);
    }

    public Path GraphSearch(Node src, Node dst, Algorithm algo) {
        return searchEngine.search(this, src, dst, algo);
    }

    protected void addNodeInternal(String label) {
        nodes.add(label);
        adjacency.computeIfAbsent(label, key -> new TreeSet<>());
    }

    protected void addEdgeInternal(String src, String dst) {
        nodes.add(src);
        nodes.add(dst);
        if (edges.add(buildEdge(src, dst))) {
            adjacency.computeIfAbsent(src, key -> new TreeSet<>()).add(dst);
            adjacency.computeIfAbsent(dst, key -> new TreeSet<>());
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Number of nodes: ").append(getNodeCount()).append(System.lineSeparator());
        sb.append("Nodes: ").append(nodes).append(System.lineSeparator());
        sb.append("Number of edges: ").append(getEdgeCount()).append(System.lineSeparator());
        sb.append("Edges:").append(System.lineSeparator());

        for (String edge : edges) {
            String[] parts = splitEdge(edge);
            sb.append(parts[0]).append(" -> ").append(parts[1]).append(System.lineSeparator());
        }

        return sb.toString();
    }

    public void outputGraph(String filepath) throws IOException {
        Files.writeString(Paths.get(filepath), toString());
    }

    public void outputDOTGraph(String path) throws IOException {
        StringBuilder sb = new StringBuilder();
        sb.append("digraph {").append(System.lineSeparator());

        for (String edge : edges) {
            String[] parts = splitEdge(edge);
            sb.append("    ").append(parts[0]).append(" -> ").append(parts[1]).append(";")
                    .append(System.lineSeparator());
        }

        for (String node : nodes) {
            boolean used = false;
            for (String edge : edges) {
                if (edge.startsWith(node + EDGE_SEPARATOR) || edge.endsWith(EDGE_SEPARATOR + node)) {
                    used = true;
                    break;
                }
            }
            if (!used) {
                sb.append("    ").append(node).append(";").append(System.lineSeparator());
            }
        }

        sb.append("}").append(System.lineSeparator());
        Files.writeString(Paths.get(path), sb.toString());
    }

    public void outputGraphics(String path, String format) throws IOException, InterruptedException {
        if (!"png".equalsIgnoreCase(format)) {
            throw new IllegalArgumentException("Only png format is supported.");
        }

        String outputPath;
        if (path.toLowerCase().endsWith(".dot")) {
            outputPath = path.substring(0, path.length() - 4) + "." + format;
        } else {
            outputPath = path + "." + format;
        }

        ProcessBuilder pb = new ProcessBuilder("dot", "-T" + format, path, "-o", outputPath);
        pb.redirectErrorStream(true);
        Process process = pb.start();
        int exitCode = process.waitFor();

        if (exitCode != 0) {
            throw new IOException("Graphviz failed to generate the output image.");
        }
    }
}