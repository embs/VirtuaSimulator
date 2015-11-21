package simulator.network.components.physical;

import junit.framework.TestCase;

import simulator.network.components.virtual.VirtualNode;

public class PhysicalNodeTest extends TestCase {

  private PhysicalNode physicalNode;

  public PhysicalNodeTest(String testName) {
    super(testName);
  }

  public void setUp() {
    physicalNode = new PhysicalNode(1, 100.2);
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
    physicalNode.addLoad(50.2);
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

  public void testAddLoad() {
    double initialLoad = physicalNode.getLoad();
    double loadVar = 50.05;
    physicalNode.addLoad(loadVar);
    assertEquals(initialLoad + loadVar, physicalNode.getLoad());
  }

  public void testRemoveLoad() {
    double initialLoad = physicalNode.getLoad();
    double loadVar = 50.05;
    physicalNode.removeLoad(loadVar);
    assertEquals(initialLoad - loadVar, physicalNode.getLoad());
  }

  public void testAddLoadRounding() {
    physicalNode.addLoad(12.435488);
    physicalNode.addLoad(6.978488);
    assertEquals(19.413976D, physicalNode.getLoad());
  }

  public void testRemoveLoadRounding() {
    physicalNode.addLoad(17.409076);
    physicalNode.removeLoad(10.430588);
    physicalNode.removeLoad(6.978488);
    assertEquals(0D, physicalNode.getLoad());
  }
}
