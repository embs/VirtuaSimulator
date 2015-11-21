package simulator.mapping;

import junit.framework.TestCase;

import java.math.BigDecimal;
import java.math.RoundingMode;

import java.util.ArrayList;
import java.util.HashMap;

import simulator.network.components.virtual.*;
import simulator.network.components.physical.*;

public class MappingTest extends TestCase {

  private Mapping mapping;
  private PhysicalNode hostingNode;
  private ArrayList<PhysicalLink> hostingLinks;
  private VirtualNode virtualNode;
  private VirtualLink virtualLink;

  public MappingTest(String testName) {
    super(testName);
    mapping = new Mapping();
    hostingNode = new PhysicalNode(1, 100);
    virtualNode = new VirtualNode(1, 50);
    virtualLink = new VirtualLink("1:2", new VirtualNode(1, 50),
                                  new VirtualNode(2, 50), 5, 5);
    hostingLinks = new ArrayList<PhysicalLink>();
    for(int i = 0; i < 5; i++) {
      String linkId = String.format("%s:%s", i, i+1);
      hostingLinks.add(new PhysicalLink(linkId, new PhysicalNode(i, 100),
                                               new PhysicalNode(i+1, 100), 200, 5, 5));
    }
  }

  public void testAddNodeMapping() {
    mapping.addNodeMapping(virtualNode, hostingNode);
    assertEquals(hostingNode, mapping.getHostingNodeFor(virtualNode));
  }

  public void testAddLinkMapping() {
    mapping.addLinkMapping(virtualLink, hostingLinks);
    assertEquals(hostingLinks, mapping.getHostingLinksFor(virtualLink));
  }

  public void testIsNodeMappedWhenItIs() {
    mapping.addNodeMapping(virtualNode, hostingNode);
    assertTrue(mapping.isNodeMapped(virtualNode));
  }

  public void testIsNodeMappedWhenItIsnt() {
    assertFalse(mapping.isNodeMapped(virtualNode));
  }

  public void testIsLinkMappedWhenItIs() {
    mapping.addLinkMapping(virtualLink, hostingLinks);
    assertTrue(mapping.isLinkMapped(virtualLink));
  }

  public void testIsLinkMappedWhenItIsnt() {
    assertFalse(mapping.isLinkMapped(virtualLink));
  }

  public void testIsNodeInUseWhenItIs() {
    mapping.addNodeMapping(virtualNode, hostingNode);
    assertTrue(mapping.isNodeInUse(hostingNode));
  }

  public void testIsNodeInUseWhenItIsnt() {
    assertFalse(mapping.isNodeInUse(hostingNode));
  }

  public void testIsLinkInUseWhenItIs() {
    mapping.addLinkMapping(virtualLink, hostingLinks);
    assertTrue(mapping.isLinkInUse(hostingLinks.get(0)));
  }

  public void testIsLinkInUseWhenItIsnt() {
    assertFalse(mapping.isLinkInUse(hostingLinks.get(0)));
  }

  public void testClearMappings() {
    mapping.addNodeMapping(virtualNode, hostingNode);
    mapping.addLinkMapping(virtualLink, hostingLinks);
    mapping.clearMappings();
    assertFalse(mapping.isNodeInUse(hostingNode));
    assertFalse(mapping.isNodeMapped(virtualNode));
    assertFalse(mapping.isLinkMapped(virtualLink));
    for(PhysicalLink hostingLink : hostingLinks)
      assertFalse(mapping.isLinkInUse(hostingLink));
  }

  public void testAddNodeMappingAddsLoadToPhysicalNode() {
    mapping.addNodeMapping(virtualNode, hostingNode);
    assertEquals(hostingNode.getCapacity() - virtualNode.getCapacity(),
      hostingNode.getRemainingCapacity());
  }

  public void testAddLinkMappingAddsLoadToPhysicalLinks() {
    mapping.addLinkMapping(virtualLink, hostingLinks);
    for(PhysicalLink physicalLink : hostingLinks) {
      assertEquals(physicalLink.getBandwidthCapacity()
        - virtualLink.getBandwidthCapacity(), physicalLink.getRemainingBandwidth());
    }
  }

  public void testClearMappingsRemovesLoadsFromPhysicalNodesAndLinks() {
    mapping.addNodeMapping(virtualNode, hostingNode);
    mapping.addLinkMapping(virtualLink, hostingLinks);
    mapping.clearMappings();
    assertEquals(hostingNode.getCapacity(), hostingNode.getRemainingCapacity());
    for(PhysicalLink hostingLink : hostingLinks)
      assertEquals(hostingLink.getBandwidthCapacity(),
        hostingLink.getRemainingBandwidth());
  }

  public void testSamePhysicalNodeCanHostMultipleVirtualNodesFromDifferentRequests() {
    VirtualNode nodeFromRequest1 = new VirtualNode(1, 100);
    VirtualNode nodeFromRequest2 = new VirtualNode(1, 100);
    mapping.addNodeMapping(nodeFromRequest1, hostingNode);
    mapping.addNodeMapping(nodeFromRequest2, hostingNode);
    assertEquals(hostingNode, mapping.getHostingNodeFor(nodeFromRequest1));
    assertEquals(hostingNode, mapping.getHostingNodeFor(nodeFromRequest2));
  }

  public void testDeactiveResourcesHandlingAllowsBreakingNodesConstraints() {
    mapping.deactiveResourcesHandling();
    virtualNode.setCapacity(hostingNode.getCapacity() + 1);
    mapping.addNodeMapping(virtualNode, hostingNode);
  }

  public void testDeactiveResourcesHandlingAllowsBreakingLinksConstraints() {
    mapping.deactiveResourcesHandling();
    virtualLink.setBandwidthCapacity(hostingLinks.get(0).getBandwidthCapacity() + 1);
    mapping.addLinkMapping(virtualLink, hostingLinks);
  }

  public void testGetAvailabilityReturnsNumberGreaterThanZero() {
    assertTrue(mapping.getAvailability().doubleValue() > 0D);
  }

  public void testGetAvailabilityTakesIntoAccountUniqPhysicalNodesOnly() {
    mapping.addNodeMapping(new VirtualNode(1, 100), hostingNode);
    mapping.addNodeMapping(new VirtualNode(2, 100), hostingNode);
    assertEquals(
      hostingNode.getNodeAvailability().doubleValue(),
      mapping.getAvailability().doubleValue());
  }

  public void testGetAvailabilityTakesIntoAccountUniqPhysicalLinksOnly() {
    VirtualNode virtualNode1 = new VirtualNode(1, 50);
    VirtualNode virtualNode2 = new VirtualNode(2, 50);
    VirtualLink virtualLink1 = new VirtualLink("1:2", virtualNode1, virtualNode2, 5, 5);
    VirtualLink virtualLink2 = new VirtualLink("2:1", virtualNode2, virtualNode1, 5, 5);
    PhysicalNode hostingNode1 = new PhysicalNode(1, 100);
    PhysicalNode hostingNode2 = new PhysicalNode(2, 100);
    hostingLinks = new ArrayList<PhysicalLink>();
    hostingLinks.add(new PhysicalLink("1:2", hostingNode1, hostingNode2, 200, 5, 5));
    mapping.addNodeMapping(virtualNode1, hostingNode1);
    mapping.addNodeMapping(virtualNode2, hostingNode2);
    mapping.addLinkMapping(virtualLink1, hostingLinks);
    mapping.addLinkMapping(virtualLink2, hostingLinks);
    BigDecimal expected = hostingNode1.getNodeAvailability().multiply(
      hostingNode2.getNodeAvailability());
    for(PhysicalLink link : hostingLinks) {
      expected = expected.multiply(link.getAvailability());
    }

    int scale = 10;
    RoundingMode mode = RoundingMode.HALF_UP;
    expected = expected.setScale(scale, mode);
    BigDecimal actual = mapping.getAvailability().setScale(scale, mode);
    assertEquals(expected.doubleValue(), actual.doubleValue());
  }

  public void testGetAvailabilityTakesIntoAccountUniqIntermediaryNodesOnly() {
    VirtualNode virtualNode1 = new VirtualNode(1, 50);
    VirtualNode virtualNode2 = new VirtualNode(2, 50);
    VirtualLink virtualLink1 = new VirtualLink("1:2", virtualNode1, virtualNode2, 5, 5);
    PhysicalNode interNode = new PhysicalNode(1, 100);
    hostingLinks = new ArrayList<PhysicalLink>();
    hostingLinks.add(new PhysicalLink("1:2", interNode, interNode, 200, 5, 5));
    mapping.addLinkMapping(virtualLink1, hostingLinks);
    BigDecimal expected = interNode.getIntermediaryNodeAvailability();
    for(PhysicalLink link : hostingLinks) {
      expected = expected.multiply(link.getAvailability());
    }

    assertEquals(expected.doubleValue(), mapping.getAvailability().doubleValue());
  }
}
