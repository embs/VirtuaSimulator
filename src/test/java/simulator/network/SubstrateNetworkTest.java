package simulator.network;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.util.HashMap;

import simulator.network.components.physical.*;

public class SubstrateNetworkTest extends TestCase {

  private SubstrateNetwork substrateNetwork;
  private HashMap<Integer, PhysicalNode> physicalNodes;
  private HashMap<String, PhysicalLink> physicalLinks;

  public SubstrateNetworkTest(String testName) {
    super(testName);
    substrateNetwork = new SubstrateNetwork();
    physicalNodes = new HashMap<Integer, PhysicalNode>();
    physicalLinks = new HashMap<String, PhysicalLink>();
    for(int i = 1; i < 6; i++) {
      physicalNodes.put(i, new PhysicalNode(i, i * 100));
    }
    for(int i = 1; i < 5; i++) {
      String linkId = String.format("%s:%s", i, 5);
      PhysicalLink physicalLink = new PhysicalLink(linkId, physicalNodes.get(i),
                                                   physicalNodes.get(5), 5, 5, 5);
      physicalLinks.put(linkId, physicalLink);
    }
  }

  public static Test suite() {
    return new TestSuite(SubstrateNetworkTest.class);
  }

  public void testGetHashNodes() {
    assertNotNull(substrateNetwork.getHashNodes());
  }

  public void testGetHashLinks() {
    assertNotNull(substrateNetwork.getHashLinks());
  }

  public void testGetAmountNodes() {
    assertEquals(0, substrateNetwork.getAmountNodes());
  }

  public void testGetAmountLinks() {
    assertEquals(0, substrateNetwork.getAmountLinks());
  }

  public void testSetHashNodes() {
    substrateNetwork.setHashNodes(physicalNodes);
    assertEquals(physicalNodes.size(), substrateNetwork.getAmountNodes());
  }

  public void testSetHashLinks() {
    substrateNetwork.setHashLinks(physicalLinks);
    assertEquals(physicalLinks.size(), substrateNetwork.getAmountLinks());
  }

  public void testGetPhysicalNodesWithCapacityGreaterThan50() {
    substrateNetwork.setHashNodes(physicalNodes);
    assertEquals(5, substrateNetwork.
      getPhysicalNodesWithRemainingCapacityGreaterThan(50).size());
  }

  public void testGetPhysicalNodesWithCapacityGreaterThan100() {
    substrateNetwork.setHashNodes(physicalNodes);
    assertEquals(4, substrateNetwork.
      getPhysicalNodesWithRemainingCapacityGreaterThan(100).size());
  }

  public void testGetPhysicalNodesWithCapacityGreaterThan500() {
    substrateNetwork.setHashNodes(physicalNodes);
    assertEquals(0, substrateNetwork.
      getPhysicalNodesWithRemainingCapacityGreaterThan(500).size());
  }

  public void testCollectNodesLoad() {
    substrateNetwork.setHashNodes(physicalNodes);
    assertEquals(physicalNodes.size(),
      substrateNetwork.collectNodesLoad().getSize());
  }

  public void testCollectLinksBandwidthLoad() {
    substrateNetwork.setHashLinks(physicalLinks);
    assertEquals(physicalLinks.size(),
      substrateNetwork.collectLinksBandwidthLoad().getSize());
  }

  public void testGetAverageNodesLoad() {
    substrateNetwork.setHashNodes(physicalNodes);
    for(PhysicalNode node : physicalNodes.values()) {
      node.addLoad(10);
    }
    assertEquals(10D,
      substrateNetwork.getAverageNodesLoad());
  }

  public void testGetMaximumNodesLoad() {
    substrateNetwork.setHashNodes(physicalNodes);
    for(PhysicalNode node : physicalNodes.values()) {
      node.addLoad(10);
    }
    assertEquals(10D,
      substrateNetwork.getMaximumNodesLoad());
  }

  public void testGetNodesLoadStandardDeviation() {
    substrateNetwork.setHashNodes(physicalNodes);
    for(PhysicalNode node : physicalNodes.values()) {
      node.addLoad(10);
    }
    assertEquals(0D,
      substrateNetwork.getNodesLoadStandardDeviation());
  }

  public void testGetAverageLinksBandwidthLoad() {
    substrateNetwork.setHashLinks(physicalLinks);
    for(PhysicalLink link : physicalLinks.values()) {
      link.addBandwidthLoad(10);
    }
    assertEquals(10D,
      substrateNetwork.getAverageLinksBandwidthLoad());
  }

  public void testGetMaximumLinksBandwidthLoad() {
    substrateNetwork.setHashLinks(physicalLinks);
    for(PhysicalLink link : physicalLinks.values()) {
      link.addBandwidthLoad(10);
    }
    assertEquals(10D,
      substrateNetwork.getMaximumLinksBandwidthLoad());
  }

  public void testGetLinksBandwidthLoadStandardDeviation() {
    substrateNetwork.setHashLinks(physicalLinks);
    for(PhysicalLink link : physicalLinks.values()) {
      link.addBandwidthLoad(10);
    }
    assertEquals(0D,
      substrateNetwork.getLinksBandwidthLoadStandardDeviation());
  }
}
