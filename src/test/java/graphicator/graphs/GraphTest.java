package graphicator.graphs;

import junit.framework.TestCase;

public class GraphTest extends TestCase {

  private Graph graph;

  public GraphTest(String testName) {
    super(testName);
  }

  public void setUp() {
    graph = new Graph("TestGraph", 4, 3);
    graph.setLineHeader(0, "20 nós");
    graph.setLineHeader(1, "30 nós");
    graph.setLineHeader(2, "50 nós");
    graph.setLineHeader(3, "100 nós");
    graph.setColumnHeader(0, "HRA");
    graph.setColumnHeader(1, "Greedy");
    graph.setColumnHeader(2, "DViNE");
    graph.setLine(0, new String[]{ "1", "2", "3" });
    graph.setLine(1, new String[]{ "4", "5", "6" });
    graph.setLine(2, new String[]{ "7", "8", "9" });
    graph.setLine(3, new String[]{ "10", "11", "12" });
  }

  public void testGraphTableLinesHeaders() {
    assertEquals("20 nós", graph.getLinesHeaders()[0]);
    assertEquals("30 nós", graph.getLinesHeaders()[1]);
    assertEquals("50 nós", graph.getLinesHeaders()[2]);
    assertEquals("100 nós", graph.getLinesHeaders()[3]);
  }

  public void testGraphTableColumnsHeaders() {
    assertEquals("HRA", graph.getColumnsHeaders()[0]);
    assertEquals("Greedy", graph.getColumnsHeaders()[1]);
    assertEquals("DViNE", graph.getColumnsHeaders()[2]);
  }

  public void testGraphTableValues() {
    assertEquals("1", graph.getTable()[0][0]);
    assertEquals("2", graph.getTable()[0][1]);
    assertEquals("3", graph.getTable()[0][2]);
    assertEquals("4", graph.getTable()[1][0]);
    assertEquals("5", graph.getTable()[1][1]);
    assertEquals("6", graph.getTable()[1][2]);
    assertEquals("7", graph.getTable()[2][0]);
    assertEquals("8", graph.getTable()[2][1]);
    assertEquals("9", graph.getTable()[2][2]);
    assertEquals("10", graph.getTable()[3][0]);
    assertEquals("11", graph.getTable()[3][1]);
    assertEquals("12", graph.getTable()[3][2]);
  }
}
