package graph;

public interface SearchStrategy {
    Path search(Graph graph, Node src, Node dst);
}
