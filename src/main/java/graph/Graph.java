package graph;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class Graph {
    private final Set<String> nodes;
    private final Set<String> edges;
    private final Map<Algorithm, SearchStrategy> strategies;

    public Graph() {
        this.nodes = new TreeSet<>();
        this.edges = new TreeSet<>();
        this.strategies = new EnumMap<>(Algorithm.class);
        registerDefaultStrategies();
    }

    private void registerDefaultStrategies() {
        strategies.put(Algorithm.BFS, new BfsSearchStrategy());
        strategies.put(Algorithm.DFS, new DfsSearchStrategy());
        strategies.put(Algorithm.RANDOM_WALK, new RandomWalkSearchStrategy());
    }

    public void registerStrategy(Algorithm algorithm, SearchStrategy strategy) {
        if (algorithm == null || strategy == null) {
            throw new IllegalArgumentException("Algorithm and strategy cannot be null.");
        }
        strategies.put(algorithm, strategy);
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

    public void addEdge(String srcLabel, String dstLabel) {
        if (srcLabel == null || dstLabel == null ||
                srcLabel.trim().isEmpty() || dstLabel.trim().isEmpty()) {
            throw new IllegalArgumentException("Source and destination labels cannot be null or empty.");
        }

        String src = srcLabel.trim();
        String dst = dstLabel.trim();

        addNode(src);
        addNode(dst);
        edges.add(src + "->" + dst);
    }

    public void removeNode(String label) {
        if (!nodes.contains(label)) {
            throw new RuntimeException("Node not found: " + label);
        }
        nodes.remove(label);
        ArrayList<String> temp = new ArrayList<>();
        for (String e : edges) {
            if (e.startsWith(label + "->") || e.endsWith("->" + label)) {
                temp.add(e);
            }
        }
        edges.removeAll(temp);
    }

    public void removeNodes(String[] labels) {
        for (String label : labels) {
            removeNode(label);
        }
    }

    public void removeEdge(String srcLabel, String dstLabel) {
        String edge = srcLabel + "->" + dstLabel;
        if (!edges.contains(edge)) {
            throw new RuntimeException("Edge not found: " + edge);
        }
        edges.remove(edge);
    }

    public List<String> getNeighbors(String srcLabel) {
        ArrayList<String> neighbors = new ArrayList<>();
        for (String edge : edges) {
            if (edge.startsWith(srcLabel + "->")) {
                String[] parts = edge.split("->", 2);
                neighbors.add(parts[1]);
            }
        }
        return neighbors;
    }

    public Path GraphSearch(String src, String dst, Algorithm algo) {
        return GraphSearch(new Node(src), new Node(dst), algo);
    }

    public Path GraphSearch(Node src, Node dst, Algorithm algo) {
        if (src == null || dst == null || algo == null) {
            return null;
        }

        SearchStrategy strategy = strategies.get(algo);
        if (strategy == null) {
            throw new IllegalArgumentException("No strategy configured for algorithm: " + algo);
        }
        return strategy.search(this, src, dst);
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

    public void outputDOTGraph(String path) throws IOException {
        StringBuilder sb = new StringBuilder();
        sb.append("digraph {").append(System.lineSeparator());

        for (String edge : edges) {
            String[] parts = edge.split("->");
            sb.append("    ").append(parts[0]).append(" -> ").append(parts[1]).append(";")
                    .append(System.lineSeparator());
        }

        for (String node : nodes) {
            boolean used = false;
            for (String edge : edges) {
                if (edge.startsWith(node + "->") || edge.endsWith("->" + node)) {
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