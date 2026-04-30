package graph;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomWalkSearchStrategy extends AbstractGraphSearchTemplate<List<Path>> {
    public RandomWalkSearchStrategy() {
        super(true);
    }

    public RandomWalkSearchStrategy(Random random, boolean traceVisits) {
        super(random, traceVisits);
    }

    @Override
    protected List<Path> createFrontier() {
        return new ArrayList<>();
    }

    @Override
    protected void push(List<Path> frontier, Path path) {
        frontier.add(path);
    }

    @Override
    protected Path pop(List<Path> frontier) {
        return frontier.remove(frontier.size() - 1);
    }

    @Override
    protected boolean isEmpty(List<Path> frontier) {
        return frontier.isEmpty();
    }

    @Override
    protected List<String> selectNeighbors(List<String> neighbors, Path current, Node dst, Random random) {
        if (neighbors.isEmpty()) {
            return neighbors;
        }

        String pick = neighbors.get(random.nextInt(neighbors.size()));
        List<String> oneStep = new ArrayList<>();
        oneStep.add(pick);
        return oneStep;
    }

    @Override
    protected int maxExpansions(Graph graph) {
        return Math.max(1, graph.getNodeCount() * 10);
    }
}
