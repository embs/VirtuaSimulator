package simulator.network.components.virtual;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import simulator.network.components.physical.PhysicalNode;

public class VirtualNodeTest extends TestCase {

  private VirtualNode virtualNode;

  public VirtualNodeTest(String testName) {
    super(testName);
    virtualNode = new VirtualNode(1, 100.2);
  }

  public static Test suite() {
    return new TestSuite(VirtualNodeTest.class);
  }

  public void testGetId() {
    assertEquals(1, virtualNode.getId());
  }

  public void testGetCapacity() {
    assertEquals(100.2, virtualNode.getCapacity());
  }

  public void testGetPhysicalHostNode() {
    assertNull(virtualNode.getPhysicalHostNode());
  }

  public void testIsAllocated() {
    assertFalse(virtualNode.isAllocated());
  }

  public void testSetPhysicalHostNode() {
    PhysicalNode hostNode = new PhysicalNode(1, 100.1);
    virtualNode.setPhysicalHostNode(hostNode);
    assertEquals(hostNode, virtualNode.getPhysicalHostNode());
  }
}
