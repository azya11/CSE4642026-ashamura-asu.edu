package graph;

import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public abstract class AbstractGraphSearchTemplate<F> implements SearchStrategy {
    private final Random random;
    private final boolean traceVisits;

    protected AbstractGraphSearchTemplate() {
        this(new Random(), false);
    }

    protected AbstractGraphSearchTemplate(boolean traceVisits) {
        this(new Random(), traceVisits);
    }

    protected AbstractGraphSearchTemplate(Random random, boolean traceVisits) {
        this.random = random;
        this.traceVisits = traceVisits;
    }

    @Override
    public final Path search(Graph graph, Node src, Node dst) {
        if (graph == null || src == null || dst == null) {
            return null;
        }
        if (!graph.getNodes().contains(src.label) || !graph.getNodes().contains(dst.label)) {
            return null;
        }

        F frontier = createFrontier();
        Set<String> discovered = new HashSet<>();

        Path start = new Path();
        start.addNode(new Node(src.label));
        push(frontier, start);
        discovered.add(src.label);

        int expansions = 0;
        int maxExpansions = maxExpansions(graph);

        while (!isEmpty(frontier) && expansions < maxExpansions) {
            Path current = pop(frontier);
            if (traceVisits) {
                System.out.println("visiting " + current.toDebugString());
            }

            Node last = current.getLastNode();
            if (last != null && last.label.equals(dst.label)) {
                return current;
            }

            List<String> orderedNeighbors = graph.getNeighbors(last.label);
            List<String> selectedNeighbors = selectNeighbors(orderedNeighbors, current, dst, random);

            for (String neighbor : selectedNeighbors) {
                if (shouldExpandNeighbor(neighbor, discovered, current)) {
                    discovered.add(neighbor);
                    push(frontier, current.copyAndAppend(new Node(neighbor)));
                }
            }

            expansions++;
        }

        return null;
    }

    protected boolean shouldExpandNeighbor(String neighbor, Set<String> discovered, Path current) {
        return !discovered.contains(neighbor);
    }

    protected List<String> selectNeighbors(List<String> neighbors, Path current, Node dst, Random random) {
        return neighbors;
    }

    protected int maxExpansions(Graph graph) {
        return Integer.MAX_VALUE;
    }

    protected abstract F createFrontier();

    protected abstract void push(F frontier, Path path);

    protected abstract Path pop(F frontier);

    protected abstract boolean isEmpty(F frontier);
}
