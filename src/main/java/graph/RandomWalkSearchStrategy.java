package graph;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;

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
    protected List<String> selectNeighbors(
            List<String> neighbors,
            Path current,
            Node dst,
            Random random,
            Set<String> discovered
    ) {
        ArrayList<String> unvisited = new ArrayList<>();
        for (String neighbor : neighbors) {
            if (!discovered.contains(neighbor)) {
                unvisited.add(neighbor);
            }
        }

        if (unvisited.isEmpty()) {
            return unvisited;
        }

        String pick = unvisited.get(random.nextInt(unvisited.size()));
        List<String> oneStep = new ArrayList<>();
        oneStep.add(pick);
        return oneStep;
    }

    @Override
    protected void onNoNeighborProgress(Path current) {
        if (!isTraceVisitsEnabled()) {
            return;
        }

        Node last = current.getLastNode();
        if (last != null) {
            System.out.println("Reached dead end at node" + last.label);
        }
    }

    @Override
    protected int maxExpansions(Graph graph) {
        return Math.max(1, graph.getNodeCount() * 10);
    }
}
