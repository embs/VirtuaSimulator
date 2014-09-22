package simulator.io;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import simulator.network.SubstrateNetwork;
import simulator.network.components.physical.PhysicalNode;
import simulator.simulation.Request;

public class ViNEYardVNMPReaderTest extends TestCase {

  private ViNEYardVNMPReader reader;
  private SubstrateNetwork substrateNetwork;
  private Request request;

  public ViNEYardVNMPReaderTest(String testName) {
    super(testName);
    reader = new ViNEYardVNMPReader(
      "/home/embs/Code/vine-yard/sub-50.txt",
      "/home/embs/Code/vine-yard/r-2000-50-20-10-10-10-50");
    substrateNetwork = reader.getSubstrateNetwork();
    request = reader.getVirtualNetworkRequests().get(0);
  }

  public static Test suite() {
    return new TestSuite(ViNEYardVNMPReaderTest.class);
  }

  public void testNumberOfSubstrateNodes() {
    assertEquals(50, substrateNetwork.getAmountNodes());
  }

  public void testNumberOfSubstrateLinks() {
    assertEquals(130, substrateNetwork.getAmountLinks());
  }

  public void testFirstSubstrateNodeCapacity() {
    assertEquals(61.199593, substrateNetwork.getHashNodes().get(0).
      getCapacity());
  }

  public void testFirstSubstrateLinkBandwidthCapacity() {
    assertEquals(61.833941, substrateNetwork.getHashLinks().get("43:0").
      getBandwidthCapacity());
  }

  public void testFirstSubstrateLinkDelay() {
    assertEquals(20.000000, substrateNetwork.getHashLinks().get("43:0").
      getDelay());
  }

  public void testGetVirtualNetworkRequests() {
    assertEquals(2000, reader.getVirtualNetworkRequests().size());
  }

  public void testFirstVirtualNetworkRequestNullity() {
    assertNotNull(request);
  }

  public void testFirstVirtualNetworkRequestId() {
    assertEquals(1508, request.getId());
  }

  public void testFirstVirtualNetworkRequestNumberOfNodes() {
    assertEquals(8, request.getAmountNodes());
  }

  public void testFirstVirtualNetworkRequestNumberOfLinks() {
    assertEquals(8, request.getAmountLinks());
  }

  public void testFirstVirtualNetworkRequestFirstNodeCapacity() {
    assertEquals(6.662476, request.getVirtualNodes().get(0).getCapacity());
  }

  public void testFirstVirtualNetworkRequestFirstLinkBandwidthCapacity() {
    assertEquals(10.430588, request.getVirtualLinks().get("4:0").
      getBandwidthCapacity());
  }

  public void testFirstVirtualNetworkRequestFirstLinkDelay() {
    assertEquals(43.785719, request.getVirtualLinks().get("4:0").
      getDelay());
  }
}
