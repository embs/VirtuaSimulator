package graphicator.io;

import java.util.HashMap;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class ViNEYardTraceReaderTest extends TestCase {

  private ViNEYardTraceReader reader;
  private HashMap<String, Object> metrics;

  public ViNEYardTraceReaderTest(String testName) {
    super(testName);
    reader = new ViNEYardTraceReader();
    metrics = reader.readTrace(
      "/media/embs/Data/OptFIVNMP_Instances_ViNE_format/20/eu_20_0_prob/MySimINFOCOM2009.out",
      "/media/embs/Data/OptFIVNMP_Instances_ViNE_format/20/eu_20_0_prob/time.out");
  }

  public static Test suite() {
    return new TestSuite(ViNEYardTraceReaderTest.class);
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
