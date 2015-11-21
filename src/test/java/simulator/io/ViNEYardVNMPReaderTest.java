package simulator.io;

import junit.framework.TestCase;

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
      "src/test/resources/ViNEYard/substrate/sub-50.txt",
      "src/test/resources/ViNEYard/requests/1");
    substrateNetwork = reader.getSubstrateNetwork();
    request = reader.getVirtualNetworkRequests().get(0);
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
    assertEquals(1, reader.getVirtualNetworkRequests().size());
  }

  public void testFirstVirtualNetworkRequestNullity() {
    assertNotNull(request);
  }

  public void testFirstVirtualNetworkRequestId() {
    assertEquals(492, request.getId());
  }

  public void testFirstVirtualNetworkRequestNumberOfNodes() {
    assertEquals(16, request.getAmountNodes());
  }

  public void testFirstVirtualNetworkRequestNumberOfLinks() {
    assertEquals(24, request.getAmountLinks());
  }

  public void testFirstVirtualNetworkRequestFirstNodeCapacity() {
    assertEquals(3.740279, request.getVirtualNodes().get(0).getCapacity());
  }

  public void testFirstVirtualNetworkRequestFirstLinkBandwidthCapacity() {
    assertEquals(10.307391, request.getVirtualLinks().get("9:0").
      getBandwidthCapacity());
  }

  public void testFirstVirtualNetworkRequestFirstLinkDelay() {
    assertEquals(70.310927, request.getVirtualLinks().get("9:0").
      getDelay());
  }
}
