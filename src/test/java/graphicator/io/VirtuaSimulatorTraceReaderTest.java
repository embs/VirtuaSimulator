package graphicator.io;

import java.util.HashMap;

import junit.framework.TestCase;

public class VirtuaSimulatorTraceReaderTest extends TestCase {

  private VirtuaSimulatorTraceReader reader;
  private HashMap<String, Object> metrics;

  public VirtuaSimulatorTraceReaderTest(String testName) {
    super(testName);
    reader = new VirtuaSimulatorTraceReader();
    metrics = reader.readTrace(
      "src/test/resources/VirtuaSimulator/traces/eu_20_0_prob_simulation.txt");
  }

  public void testAcceptedRequests() {
    assertNotNull(metrics.get("acceptedRequests"));
  }

  public void testAcceptanceRate() {
    assertNotNull(metrics.get("acceptanceRate"));
  }

  public void testAverageNodesLoad() {
    assertNotNull(metrics.get("averageNodesLoad"));
  }

  public void testAverageLinksLoad() {
    assertNotNull(metrics.get("averageLinksLoad"));
  }

  public void testNodesLoadStandardDeviation() {
    assertNotNull(metrics.get("nodesLoadStandardDeviation"));
  }

  public void testLinksLoadStandardDeviation() {
    assertNotNull(metrics.get("linksLoadStandardDeviation"));
  }

  public void testExecutionTime() {
    assertNotNull(metrics.get("executionTime"));
  }

  public void testGetInexistentMetric() {
    assertNull(metrics.get("eita"));
  }
}
