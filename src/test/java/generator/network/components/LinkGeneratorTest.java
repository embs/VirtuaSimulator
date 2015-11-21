package generator.network.components;

import junit.framework.TestCase;

import simulator.network.components.physical.*;
import simulator.network.components.virtual.*;

public class LinkGeneratorTest extends TestCase {

  private NodeGenerator nodeGenerator;
  private LinkGenerator linkGenerator;
  private PhysicalNode sourcePhysicalNode;
  private PhysicalNode destinyPhysicalNode;
  private PhysicalLink generatedPhysicalLink;
  private VirtualNode sourceVirtualNode;
  private VirtualNode destinyVirtualNode;
  private VirtualLink generatedVirtualLink;
  private String linkId;

  public LinkGeneratorTest(String testName) {
    super(testName);
    nodeGenerator = new NodeGenerator();
    linkGenerator = new LinkGenerator();
    sourcePhysicalNode = nodeGenerator.generatePhysicalNode(1);
    destinyPhysicalNode = nodeGenerator.generatePhysicalNode(2);
    linkId = "2:1";
    generatedPhysicalLink = linkGenerator.generatePhysicalLink(linkId,
      sourcePhysicalNode, destinyPhysicalNode);
    sourceVirtualNode = nodeGenerator.generateVirtualNode(1);
    destinyVirtualNode = nodeGenerator.generateVirtualNode(2);
    generatedVirtualLink = linkGenerator.generateVirtualLink(linkId,
      sourceVirtualNode, destinyVirtualNode);
  }

  public void testGeneratedPhysicalLinkId() {
    assertEquals(linkId, generatedPhysicalLink.getId());
  }

  public void testGeneratedPhysicalLinkSourceNode() {
    assertEquals(sourcePhysicalNode, generatedPhysicalLink.getSourceNode());
  }

  public void testGeneratedPhysicalLinkDestinyNode() {
    assertEquals(destinyPhysicalNode, generatedPhysicalLink.getDestinyNode());
  }

  public void testGeneratedPhysicalLinkBandwidth() {
    assertTrue(generatedPhysicalLink.getBandwidthCapacity() >= 50D);
    assertTrue(generatedPhysicalLink.getBandwidthCapacity() <= 100D);
  }

  public void testGeneratedVirtualLinkId() {
    assertEquals(linkId, generatedVirtualLink.getId());
  }

  public void testGeneratedVirtualLinkSourceNode() {
    assertEquals(sourceVirtualNode, generatedVirtualLink.getSourceNode());
  }

  public void testGeneratedVirtualLinkDestinyNode() {
    assertEquals(destinyVirtualNode, generatedVirtualLink.getDestinyNode());
  }

  public void testGeneratedVirtualLinkBandwidth() {
    assertTrue(generatedVirtualLink.getBandwidthCapacity() >= 0D);
    assertTrue(generatedVirtualLink.getBandwidthCapacity() <= 50D);
  }
}
