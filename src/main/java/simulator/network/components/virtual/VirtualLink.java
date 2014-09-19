package simulator.network.components.virtual;


import java.util.ArrayList;
import java.util.List;

import simulator.network.components.Link;
import simulator.network.components.physical.PhysicalLink;

public class VirtualLink extends Link {
  private List<PhysicalLink> physicalHostLinks;

  public VirtualLink(String id, VirtualNode sourceNode, VirtualNode destinyNode,
                     double bandwidth, int delay) {
    super(id, sourceNode, destinyNode, bandwidth, delay);
    this.physicalHostLinks = new ArrayList<PhysicalLink>();
  }

  public List<PhysicalLink> getMappedPhysicalLinksList() {
    return this.physicalHostLinks;
  }

  public void addPhysicalLinkToPhysicalHostLinks(PhysicalLink physicalHostLink) {
    physicalHostLinks.add(physicalHostLink);
  }

  public boolean isAllocated() {
    return !this.physicalHostLinks.isEmpty();
  }
}
