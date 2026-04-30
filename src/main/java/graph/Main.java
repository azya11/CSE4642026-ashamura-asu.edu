package graph;

public class Main {
    public static void main(String[] args) throws Exception {
        Graph graph = GraphParser.parseGraph("graphs/input.dot");

        System.out.println("=== Strategy API: BFS / DFS ===");
        System.out.println("BFS path from a to c: " + graph.GraphSearch("a", "c", Algorithm.BFS));
        System.out.println("DFS path from a to c: " + graph.GraphSearch("a", "c", Algorithm.DFS));

        System.out.println();
        System.out.println("=== Random Walk on provided graph (a -> c) ===");
        graph.registerStrategy(Algorithm.RANDOM_WALK, new RandomWalkSearchStrategy());
        Path requiredPath = graph.GraphSearch("a", "c", Algorithm.RANDOM_WALK);
        System.out.println(requiredPath);

        Graph randomDemoGraph = new Graph();
        randomDemoGraph.addEdge("a", "b");
        randomDemoGraph.addEdge("a", "e");
        randomDemoGraph.addEdge("b", "c");
        randomDemoGraph.addEdge("e", "c");
        randomDemoGraph.addEdge("e", "g");
        randomDemoGraph.addEdge("e", "f");
        randomDemoGraph.addEdge("g", "h");

        System.out.println();
        System.out.println("=== Random Walk Testing (multiple runs to show randomness) ===");

        for (int i = 1; i <= 6; i++) {
            System.out.println("random testing");
            randomDemoGraph.registerStrategy(Algorithm.RANDOM_WALK, new RandomWalkSearchStrategy());
            Path randomPath = randomDemoGraph.GraphSearch("a", "c", Algorithm.RANDOM_WALK);
            System.out.println(randomPath);
        }
    }
}
