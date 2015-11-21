package graphicator.graphs;

import junit.framework.TestCase;

public class DynamicGraphTest extends TestCase {

  private DynamicGraph graph;

  public DynamicGraphTest(String testName) {
    super(testName);
    graph = new DynamicGraph("acceptanceRate over time", 2);
    graph.setColumnHeader(0, "tempo");
    graph.setColumnHeader(1, "aceitação");
  }

  public void testAddLine() {
    graph.addLine("1", new String[]{ "52", "0.54" });
    graph.addLine("2", new String[]{ "94", "0.56" });
    assertEquals("52", graph.getTable()[0][0]);
    assertEquals("0.54", graph.getTable()[0][1]);
    assertEquals("94", graph.getTable()[1][0]);
    assertEquals("0.56", graph.getTable()[1][1]);
  }
}
