package simulator.io;

import junit.framework.TestCase;

import simulator.network.components.physical.PhysicalNode;
import simulator.simulation.Request;

public class VirtuaVNMPReaderTest extends TestCase {

  private VirtuaVNMPReader reader;
  private Request request;

  public VirtuaVNMPReaderTest(String testName) {
    super(testName);
    reader = new VirtuaVNMPReader("src/test/resources/VirtuaSimulator/VNMPs/vVNMP1.txt");
    request = reader.getVirtualNetworkRequests().get(0);
  }

  public void testNumberOfSubstrateNodes() {
    assertEquals(20, reader.getSubstrateNetwork().getAmountNodes());
  }

  public void testNumberOfSubstrateLinks() {
    assertTrue(reader.getSubstrateNetwork().getAmountLinks() > 0);
  }

  public void testGetVirtualNetworkRequests() {
    assertEquals(40, reader.getVirtualNetworkRequests().size());
  }

  public void testFirstVirtualNetworkRequestNullity() {
    assertNotNull(request);
  }

  public void testFirstVirtualNetworkRequestNumberOfNodes() {
    assertTrue(request.getAmountNodes() >= 2);
    assertTrue(request.getAmountNodes() <= 10);
  }

  public void testFirstVirtualNetworkRequestNumberOfLinks() {
    assertTrue(request.getAmountLinks() >= 0);
  }
}
