package simulator.mapping;

import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.HashMap;

import simulator.io.OptFIVNMPReader;
import simulator.network.SubstrateNetwork;
import simulator.network.components.Path;
import simulator.network.components.virtual.*;
import simulator.network.components.physical.*;
import simulator.simulation.Request;

public class GraspMapperTest extends TestCase {

  private GraspMapper mapper;
  private SubstrateNetwork substrateNetwork;
  private ArrayList<Request> requests;

  public void setUp() {
    mapper = new GraspMapper();
    substrateNetwork = new SubstrateNetwork();
    OptFIVNMPReader reader = new OptFIVNMPReader(
      "src/test/resources/OptFI/VNMPs/eu_20_0_prob");
    substrateNetwork = reader.getSubstrateNetwork();
    requests = reader.getVirtualNetworkRequests();
  }

  public void testFindFirstPhysicalNodeFor() {
    VirtualNode virtualNode = requests.get(0).getVirtualNodes().get(0);
    PhysicalNode physicalNode = mapper.findFirstPhysicalNodeFor(virtualNode,
      substrateNetwork);
    assertNotNull(physicalNode);
    assertTrue(physicalNode.getRemainingCapacity() > virtualNode.getCapacity());
  }

  public void testGetShortestPathsFromNodeWithConstraints() {
    PhysicalNode physicalSourceNode = substrateNetwork.getHashNodes().get(0);
    VirtualLink virtualLink = requests.get(0).getVirtualLinks().get("1:0");
    HashMap<PhysicalNode, Path> shortestPaths =
      mapper.getShortestPathsFromNodeWithConstraints(physicalSourceNode,
        substrateNetwork, virtualLink);
    for(Path path : shortestPaths.values()) {
      assertTrue(path.getSourceNode().canHost(
        (VirtualNode) virtualLink.getSourceNode()));
      assertTrue(path.getDestinyNode().canHost(
        (VirtualNode) virtualLink.getDestinyNode()));
      assertTrue(path.canHost(virtualLink));
    }
  }

  public void testGetPathsFromNode() {
    PhysicalNode physicalSourceNode = substrateNetwork.getHashNodes().get(0);
    HashMap<PhysicalNode, Path> paths = mapper.getPathsFromNode(
      physicalSourceNode, substrateNetwork);
    assertFalse(paths.isEmpty());
  }

  public void testCapListNodes() {
    ArrayList<PhysicalNode> nodes =
      new ArrayList<PhysicalNode>(substrateNetwork.getHashNodes().values());
    assertTrue(nodes.size() > mapper.capListNodes(nodes).size());
  }

  public void testMapBranch() {
    Mapping mapping = new Mapping();
    VirtualNode virtualNode = requests.get(0).getVirtualNodes().get(0);
    VirtualLink virtualLink = (VirtualLink) virtualNode.getAttachedLinks().get(0);
    VirtualNode virtualDestinyNode = (VirtualNode) virtualLink.getNodeAttachedTo(
      virtualNode);
    for(PhysicalNode physicalNode : substrateNetwork.getHashNodes().values()) {
      if(!mapping.isNodeMapped(virtualNode) && physicalNode.canHost(virtualNode)) {
        mapping.addNodeMapping(virtualNode, physicalNode);
      }
    }
    assertTrue(mapper.mapBranch(virtualNode, virtualDestinyNode, virtualLink,
      substrateNetwork, mapping));
  }

  public void testMapBranchWhenItCannotBeMapped() {
    Mapping mapping = new Mapping();
    VirtualNode virtualNode = requests.get(0).getVirtualNodes().get(0);
    VirtualLink virtualLink = (VirtualLink) virtualNode.getAttachedLinks().get(0);
    virtualLink.setBandwidthCapacity(9999);
    VirtualNode virtualDestinyNode = (VirtualNode) virtualLink.getNodeAttachedTo(
      virtualNode);
    for(PhysicalNode physicalNode : substrateNetwork.getHashNodes().values()) {
      if(!mapping.isNodeMapped(virtualNode) && physicalNode.canHost(virtualNode)) {
        mapping.addNodeMapping(virtualNode, physicalNode);
      }
    }
    assertFalse(mapper.mapBranch(virtualNode, virtualDestinyNode, virtualLink,
      substrateNetwork, mapping));
  }

  public void testMap() {
    Request request = requests.get(0);
    assertNotNull(mapper.map(request, substrateNetwork));
  }

  public void testMapWhenRequestCannotBeMapped() {
    Request request = requests.get(0);
    VirtualNode virtualNode = request.getVirtualNodes().get(0);
    VirtualLink virtualLink = (VirtualLink) virtualNode.getAttachedLinks().get(0);
    virtualLink.setBandwidthCapacity(9999);
    assertNull(mapper.map(request, substrateNetwork));
  }

  public void testMapAllNodesAndLinks() {
    for(Request request : requests) {
      Mapping mapping = mapper.map(request, substrateNetwork);
      if(mapping != null) {
        for(VirtualNode virtualNode : request.getVirtualNodes().values()) {
          assertTrue(mapping.isNodeMapped(virtualNode));
        }
        for(VirtualLink virtualLink : request.getVirtualLinks().values()) {
          assertTrue(mapping.isLinkMapped(virtualLink));
        }
      }
    }
  }

  public void testMapFreesUpResourcesOnMappingFailure() {
    Request request = requests.get(0);
    VirtualNode virtualNode = request.getVirtualNodes().get(0);
    VirtualLink virtualLink = (VirtualLink) virtualNode.getAttachedLinks().get(0);
    virtualLink.setBandwidthCapacity(9999);
    mapper.map(request, substrateNetwork);
    for(PhysicalNode physicalNode : substrateNetwork.getHashNodes().values()) {
      assertEquals(0D, physicalNode.getLoad());
    }
    for(PhysicalLink physicalLink : substrateNetwork.getHashLinks().values()) {
      assertEquals(0D, physicalLink.getBandwidthLoad());
    }
  }

  public void testMapDoesNotSharePhysicalNodesByDefault() {
    Request request = requests.get(0);
    Mapping mapping = mapper.map(request, substrateNetwork);
    ArrayList<Integer> hostsIds = new ArrayList<Integer>();
    for(VirtualNode virtualNode : request.getVirtualNodes().values()) {
      PhysicalNode hostingNode = mapping.getHostingNodeFor(virtualNode);
      assertEquals(false, hostsIds.contains(hostingNode.getId()));
      hostsIds.add(hostingNode.getId());
    }
  }
}
