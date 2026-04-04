package graph;

public class Main {
    public static void main(String[] args) {
        // ── Part 1: removeNode, removeNodes, removeEdge ──────────────────────
        System.out.println("=== Part 1: Remove APIs ===");
        Graph g1 = new Graph();
        g1.addNodes(new String[]{"a", "b", "c", "d"});
        g1.addEdge("a", "b");
        g1.addEdge("b", "c");
        g1.addEdge("c", "d");
        System.out.println("Before removal:\n" + g1);

        g1.removeEdge("c", "d");
        System.out.println("After removeEdge(c, d):\n" + g1);

        g1.removeNode("b");
        System.out.println("After removeNode(b):\n" + g1);

        g1.removeNodes(new String[]{"a", "c"});
        System.out.println("After removeNodes([a, c]):\n" + g1);

        // removeNode exception
        try {
            g1.removeNode("z");
        } catch (RuntimeException e) {
            System.out.println("Exception caught: " + e.getMessage());
        }

        // removeEdge exception
        Graph g2 = new Graph();
        g2.addNode("x");
        g2.addNode("y");
        try {
            g2.removeEdge("x", "y");
        } catch (RuntimeException e) {
            System.out.println("Exception caught: " + e.getMessage());
        }

        // ── Part 5: GraphSearch with BFS and DFS ─────────────────────────────
        System.out.println("\n=== Part 5: GraphSearch (BFS & DFS) ===");
        Graph g3 = new Graph();
        g3.addEdge("a", "b");
        g3.addEdge("a", "c");
        g3.addEdge("b", "d");
        g3.addEdge("c", "d");
        g3.addEdge("d", "e");
        System.out.println("Graph:\n" + g3);

        Node src = new Node("a");
        Node dst = new Node("e");

        Path bfsPath = g3.GraphSearch(src, dst, Algorithm.BFS);
        System.out.println("BFS path from a to e: " + bfsPath);

        Path dfsPath = g3.GraphSearch(src, dst, Algorithm.DFS);
        System.out.println("DFS path from a to e: " + dfsPath);

        // no path
        Node isolated = new Node("z");
        g3.addNode("z");
        Path noPath = g3.GraphSearch(src, isolated, Algorithm.BFS);
        System.out.println("BFS path from a to z (no path): " + noPath);
    }
}
