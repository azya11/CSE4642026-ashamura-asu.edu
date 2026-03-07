package graph;

public class Main {
    public static void main(String[] args) {
        try {
            Graph graph = GraphParser.parseGraph("graphs/input.dot");
            System.out.println(graph);
            graph.outputGraph("graphs/output.txt");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}