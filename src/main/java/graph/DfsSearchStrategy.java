package graph;

import java.util.ArrayDeque;
import java.util.Deque;

public class DfsSearchStrategy extends AbstractGraphSearchTemplate<Deque<Path>> {
    public DfsSearchStrategy() {
        super(false);
    }

    public DfsSearchStrategy(boolean traceVisits) {
        super(traceVisits);
    }

    @Override
    protected Deque<Path> createFrontier() {
        return new ArrayDeque<>();
    }

    @Override
    protected void push(Deque<Path> frontier, Path path) {
        frontier.push(path);
    }

    @Override
    protected Path pop(Deque<Path> frontier) {
        return frontier.pop();
    }

    @Override
    protected boolean isEmpty(Deque<Path> frontier) {
        return frontier.isEmpty();
    }
}
