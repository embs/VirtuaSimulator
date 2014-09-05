package simulator.network.components.physical;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import simulator.network.components.physical.PhysicalNode;

public class PhysicalLinkTest extends TestCase {

  private PhysicalNode node1;
  private PhysicalNode node2;
  private PhysicalLink physicalLink;

  public PhysicalLinkTest(String testName) {
    super(testName);
    node1 = new PhysicalNode(1, 50);
    node2 = new PhysicalNode(2, 50);
    physicalLink = new PhysicalLink("1:2", node1, node2, 10.1, 40, 100);
  }

  public static Test suite() {
    return new TestSuite(PhysicalLinkTest.class);
  }

  public void testGetId() {
    assertEquals("1:2", physicalLink.getId());
  }

  public void testGetSourceNode() {
    assertEquals(node1, physicalLink.getSourceNode());
  }

  public void testGetDestinyNode() {
    assertEquals(node2, physicalLink.getDestinyNode());
  }

  public void testGetBandwidthCapacity() {
    assertEquals(10.1, physicalLink.getBandwidthCapacity());
  }

  public void testGetBandwidthLoad() {
    assertEquals(0.0, physicalLink.getBandwidthLoad());
  }

  public void testGetDelay() {
    assertEquals(40, physicalLink.getDelay());
  }

  public void testGetCost() {
    assertEquals(100, physicalLink.getCost());
  }

  public void testGetAvailability() {
    assertNotNull(physicalLink.getAvailability());
  }
}
