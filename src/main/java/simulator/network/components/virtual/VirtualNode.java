package simulator.network.components.virtual;

import simulator.network.components.Node;
import simulator.network.components.physical.PhysicalNode;

public class VirtualNode extends Node {
  private PhysicalNode physicalHostNode;

  public VirtualNode(int id, double capacity) {
    super(id, capacity);
    physicalHostNode = null;
  }

  public PhysicalNode getPhysicalHostNode() {
    return physicalHostNode;
  }

  public void setPhysicalHostNode(PhysicalNode physicalHostNode) {
    this.physicalHostNode = physicalHostNode;
  }

  public boolean isAllocated() {
    return this.physicalHostNode != null;
  }
}
