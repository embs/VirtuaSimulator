package simulator.network.components.virtual;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

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

  public void testNodesIquality() {
    VirtualNode equalNode = new VirtualNode(1, 100);
    assertEquals(equalNode, virtualNode);
  }

  public void testNodesInequality() {
    VirtualNode differentNode = new VirtualNode(2, 100);
    assertFalse(differentNode.equals(virtualNode));
  }
}
