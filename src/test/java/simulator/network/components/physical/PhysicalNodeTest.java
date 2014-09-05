package simulator.network.components.physical;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

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

  public void testGetHypervisorAvailability() {
    assertNotNull(physicalNode.getHypervisorAvailability());
  }

  public void testGetRouterAvailability() {
    assertNotNull(physicalNode.getRouterAvailability());
  }

  public void testGetVirtualMachineAvailability() {
    assertNotNull(physicalNode.getVirtualMachineAvailability());
  }
}
