package graph;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class GraphParser {

    public static Graph parseGraph(String filepath) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(filepath));
        Graph graph = new Graph();

        for (String line : lines) {
            line = line.trim();

            if (line.isEmpty() || line.equals("digraph {") || line.equals("{") || line.equals("}")) {
                continue;
            }

            if (line.endsWith(";")) {
                line = line.substring(0, line.length() - 1).trim();
            }

            if (line.contains("->")) {
                String[] parts = line.split("->");
                if (parts.length == 2) {
                    String src = parts[0].trim();
                    String dst = parts[1].trim();
                    graph.addEdgeInternal(src, dst);
                }
            } else {
                graph.addNodeInternal(line);
            }
        }

        return graph;
    }
}