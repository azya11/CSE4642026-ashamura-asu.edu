package graph;

public class Main {
    public static void main(String[] args) {
        try {
            Graph graph = GraphParser.parseGraph("graphs/input.dot");

            graph.addNode("x");
            graph.addNodes(new String[]{"y", "z"});
            graph.addEdge("x", "y");
            graph.addEdge("y", "z");
            graph.addEdge("a", "x");

            System.out.println(graph);

            graph.outputGraph("graphs/output.txt");
            graph.outputDOTGraph("graphs/output.dot");
            graph.outputGraphics("graphs/output.dot", "png");

            System.out.println("Generated output.txt, output.dot, and output.png");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}