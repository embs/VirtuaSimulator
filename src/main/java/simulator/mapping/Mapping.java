package simulator.mapping;

import java.util.HashMap;

import simulator.network.components.physical.PhysicalLink;
import simulator.network.components.physical.PhysicalNode;
import simulator.network.components.virtual.VirtualLink;
import simulator.network.components.virtual.VirtualNode;

/**
 *  Virtual Network Mapping Over Substrate Network
 */
public class Mapping {
  private HashMap<VirtualNode, PhysicalNode> nodesMapping;
  private HashMap<VirtualLink, PhysicalLink[]> linksMapping;

  public Mapping() {
    nodesMapping = new HashMap<VirtualNode, PhysicalNode>();
    linksMapping = new HashMap<VirtualLink, PhysicalLink[]>();
  }

  public void addNodeMapping(VirtualNode virtualNode, PhysicalNode physicalNode) {
    if(nodesMapping.containsKey(virtualNode))
      throw new RuntimeException("Nó virtual já está alocado.");

    nodesMapping.put(virtualNode, physicalNode);
  }

  public void addLinkMapping(VirtualLink virtualLink, PhysicalLink physicalLink[]) {
    if(linksMapping.containsKey(virtualLink))
      throw new RuntimeException("Enlace virtual já está alocado.");

    linksMapping.put(virtualLink, physicalLink);
  }

  public boolean isNodeMapped(VirtualNode virtualNode) {
    return nodesMapping.containsKey(virtualNode);
  }

  public boolean isLinkMapped(VirtualLink virtualLink) {
    return linksMapping.containsKey(virtualLink);
  }

  public PhysicalNode getHostingNodeFor(VirtualNode virtualNode) {
    return nodesMapping.get(virtualNode);
  }

  public PhysicalLink[] getHostingLinksFor(VirtualLink virtualLink) {
    return linksMapping.get(virtualLink);
  }

  public boolean isNodeInUse(PhysicalNode node) {
    return nodesMapping.values().contains(node);
  }

  public boolean isLinkInUse(PhysicalLink link) {
    for(PhysicalLink[] linksPath : linksMapping.values()) {
      for(PhysicalLink l : linksPath) {
        if(l.equals(link)) {

          return true;
        }
      }
    }

    return false;
  }

  public void clearMappings() {
    nodesMapping.clear();
    linksMapping.clear();
  }

  /**
   *
   * CÓDIGO LEGADO
   *
   */
  // add node mapping
    // physicalNode.allocVirtualNode(request.getId(), virtualNode);
    // virtualNode.setPhysicalHostNode(physicalNode);

  // clear mappings
    // for(PhysicalNode node : nodesMapping.values()) {
    //   node.desAllocRequest(request.getId());
    // }
    // for(PhysicalLink[] linksPath : linksMapping.values()) {
    //   for(PhysicalLink link : linksPath) {
    //     link.desAllocVirtualLink(request.getId());
    //   }
    // }
}
