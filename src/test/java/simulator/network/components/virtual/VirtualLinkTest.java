package simulator.network.components.virtual;

import junit.framework.TestCase;

import simulator.network.components.virtual.VirtualNode;

public class VirtualLinkTest extends TestCase {

  private VirtualNode node1;
  private VirtualNode node2;
  private VirtualLink virtualLink;

  public VirtualLinkTest(String testName) {
    super(testName);
  }

  public void setUp() {
    node1 = new VirtualNode(1, 50.2);
    node2 = new VirtualNode(2, 50.0);
    virtualLink = new VirtualLink("1:2", node1, node2, 10.1, 40);
  }

  public void testGetId() {
    assertEquals("1:2", virtualLink.getId());
  }

  public void testGetSourceNode() {
    assertEquals(node1, virtualLink.getSourceNode());
  }

  public void testGetDestinyNode() {
    assertEquals(node2, virtualLink.getDestinyNode());
  }

  public void testGetBandwidthCapacity() {
    assertEquals(10.1, virtualLink.getBandwidthCapacity());
  }

  public void testSourceNodeAttachment() {
    assertTrue(node1.getAttachedLinks().contains(virtualLink));
  }

  public void testDestinyNodeAttachment() {
    assertTrue(node2.getAttachedLinks().contains(virtualLink));
  }

  public void testNodeAttachedToSourceNode() {
    assertEquals(node2, virtualLink.getNodeAttachedTo(node1));
  }

  public void testNodeAttachedToDestinyNode() {
    assertEquals(node1, virtualLink.getNodeAttachedTo(node2));
  }
}
