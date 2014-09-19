package simulator.network.components.physical;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import simulator.network.components.virtual.VirtualNode;

public class PhysicalNodeTest extends TestCase {

  private PhysicalNode physicalNode;

  public PhysicalNodeTest(String testName) {
    super(testName);
    physicalNode = new PhysicalNode(1, 100.2);
  }

  public static Test suite() {
    return new TestSuite(PhysicalNodeTest.class);
  }

  public void testGetId() {
    assertEquals(1, physicalNode.getId());
  }

  public void testGetCapacity() {
    assertEquals(100.2, physicalNode.getCapacity());
  }

  public void testGetLoad() {
    assertEquals(0.0, physicalNode.getLoad());
  }

  public void testGetRemainingCapacity() {
    physicalNode.setLoad(50.2);
    assertEquals(50.0, physicalNode.getRemainingCapacity());
  }

  public void testGetNodeAvailability() {
    assertNotNull(physicalNode.getNodeAvailability());
  }

  public void testGetIntermediaryNodeAvailability() {
    assertNotNull(physicalNode.getIntermediaryNodeAvailability());
  }

  public void testNodesIquality() {
    PhysicalNode equalNode = new PhysicalNode(1, 100);
    assertEquals(equalNode, physicalNode);
  }

  public void testNodesInequality() {
    PhysicalNode differentNode = new PhysicalNode(2, 100);
    assertFalse(differentNode.equals(physicalNode));
  }

  public void testCanHostWhenItCan() {
    VirtualNode virtualNode = new VirtualNode(1, physicalNode.getCapacity());
    assertTrue(physicalNode.canHost(virtualNode));
  }

  public void testCanHostWhenItCannot() {
    VirtualNode virtualNode = new VirtualNode(1, physicalNode.getCapacity() + 0.1);
    assertFalse(physicalNode.canHost(virtualNode));
  }

  public void testAvailabilitiesComparison() {
    assertEquals(1, physicalNode.getIntermediaryNodeAvailability().compareTo(
      physicalNode.getNodeAvailability()));
  }
}
