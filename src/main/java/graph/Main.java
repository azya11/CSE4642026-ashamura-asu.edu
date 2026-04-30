package graph;

import java.util.HashSet;
import java.util.Set;

public class Main {
    public static void main(String[] args) throws Exception {
        Graph graph = GraphParser.parseGraph("graphs/input.dot");

        System.out.println("=== BFS Demo (a -> c) ===");
        graph.registerStrategy(Algorithm.BFS, new BfsSearchStrategy(true));
        Path bfsPath = graph.GraphSearch("a", "c", Algorithm.BFS);
        System.out.println("BFS Result: " + bfsPath);

        System.out.println();
        System.out.println("=== DFS Demo (a -> c) ===");
        graph.registerStrategy(Algorithm.DFS, new DfsSearchStrategy(true));
        Path dfsPath = graph.GraphSearch("a", "c", Algorithm.DFS);
        System.out.println("DFS Result: " + dfsPath);

        System.out.println();
        System.out.println("=== Random Walk Demo (a -> h) ===");
        Set<String> successfulPaths = new HashSet<>();
        int attempts = 0;
        int minAttempts = 5;
        int maxAttempts = 20;

        while (attempts < minAttempts || (successfulPaths.size() < 2 && attempts < maxAttempts)) {
            attempts++;
            System.out.println("random testing");
            graph.registerStrategy(Algorithm.RANDOM_WALK, new RandomWalkSearchStrategy());
            Path randomPath = graph.GraphSearch("a", "h", Algorithm.RANDOM_WALK);
            System.out.println(randomPath);

            if (randomPath != null) {
                successfulPaths.add(randomPath.toString());
            }
        }

        System.out.println("Distinct successful random-walk paths: " + successfulPaths.size());
        for (String p : successfulPaths) {
            System.out.println("Success path: " + p);
        }
    }
}
