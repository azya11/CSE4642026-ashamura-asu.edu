package graph;

import java.util.EnumMap;
import java.util.Map;

public class GraphSearchEngine {
    private final Map<Algorithm, SearchStrategy> strategies = new EnumMap<>(Algorithm.class);

    public GraphSearchEngine() {
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

    public Path search(Graph graph, Node src, Node dst, Algorithm algorithm) {
        if (graph == null || src == null || dst == null || algorithm == null) {
            return null;
        }

        SearchStrategy strategy = strategies.get(algorithm);
        if (strategy == null) {
            throw new IllegalArgumentException("No strategy configured for algorithm: " + algorithm);
        }
        return strategy.search(graph, src, dst);
    }
}