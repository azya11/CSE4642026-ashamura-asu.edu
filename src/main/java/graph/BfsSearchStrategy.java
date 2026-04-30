package graph;

import java.util.LinkedList;
import java.util.Queue;

public class BfsSearchStrategy extends AbstractGraphSearchTemplate<Queue<Path>> {
    public BfsSearchStrategy() {
        super(false);
    }

    public BfsSearchStrategy(boolean traceVisits) {
        super(traceVisits);
    }

    @Override
    protected Queue<Path> createFrontier() {
        return new LinkedList<>();
    }

    @Override
    protected void push(Queue<Path> frontier, Path path) {
        frontier.offer(path);
    }

    @Override
    protected Path pop(Queue<Path> frontier) {
        return frontier.poll();
    }

    @Override
    protected boolean isEmpty(Queue<Path> frontier) {
        return frontier.isEmpty();
    }
}
