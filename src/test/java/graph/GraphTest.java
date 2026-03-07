package graph;

import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

public class GraphTest {

    @Test
    public void testAddNode() {
        Graph g = new Graph();
        g.addNode("a");
        g.addNode("a");
        assertEquals(1, g.getNodeCount());
    }

    @Test
    public void testAddNodes() {
        Graph g = new Graph();
        g.addNodes(new String[]{"a", "b", "c"});
        assertEquals(3, g.getNodeCount());
    }

    @Test
    public void testAddEdge() {
        Graph g = new Graph();
        g.addEdge("a", "b");
        g.addEdge("a", "b");
        assertEquals(2, g.getNodeCount());
        assertEquals(1, g.getEdgeCount());
    }

    @Test
    public void testParseGraph() throws Exception {
        Path temp = Files.createTempFile("graph", ".dot");
        Files.writeString(temp, """
                digraph {
                    a -> b;
                    b -> c;
                    e;
                }
                """);

        Graph g = GraphParser.parseGraph(temp.toString());

        assertEquals(4, g.getNodeCount());
        assertEquals(2, g.getEdgeCount());
        assertTrue(g.getNodes().contains("e"));
    }

    @Test
    public void testOutputDOTGraph() throws Exception {
        Graph g = new Graph();
        g.addEdge("a", "b");

        Path temp = Files.createTempFile("output", ".dot");
        g.outputDOTGraph(temp.toString());

        String content = Files.readString(temp);
        assertTrue(content.contains("a -> b;"));
    }
}