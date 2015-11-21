package generator.network.components;

import junit.framework.TestCase;

import simulator.network.components.physical.PhysicalNode;
import simulator.network.components.virtual.VirtualNode;

public class NodeGeneratorTest extends TestCase {

  private NodeGenerator nodeGenerator;
  private int nodeId;
  private PhysicalNode generatedPhysicalNode;
  private VirtualNode generatedVirtualNode;

  public NodeGeneratorTest(String testName) {
    super(testName);
    nodeGenerator = new NodeGenerator();
    nodeId = 1;
    generatedPhysicalNode = nodeGenerator.generatePhysicalNode(nodeId);
    generatedVirtualNode = nodeGenerator.generateVirtualNode(nodeId);
  }

  public void testGeneratedPhysicalNodeId() {
    assertEquals(nodeId, generatedPhysicalNode.getId());
  }

  public void testGeneratedPhysicalNodeCapacity() {
    assertTrue(generatedPhysicalNode.getCapacity() >= 50D);
    assertTrue(generatedPhysicalNode.getCapacity() <= 100D);
  }

  public void testGeneratedVirtualNodeId() {
    assertEquals(nodeId, generatedVirtualNode.getId());
  }

  public void testGeneratedVirtualNodeCapacity() {
    assertTrue(generatedVirtualNode.getCapacity() >= 0D);
    assertTrue(generatedVirtualNode.getCapacity() <= 20D);
  }
}
