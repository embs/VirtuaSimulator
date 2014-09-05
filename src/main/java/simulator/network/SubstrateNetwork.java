package simulator.network;

import java.util.ArrayList;
import java.util.HashMap;

import simulator.network.components.physical.PhysicalLink;
import simulator.network.components.physical.PhysicalNode;

public class SubstrateNetwork {
  private HashMap<Integer, PhysicalNode> physicalNodes;
  private HashMap<String, PhysicalLink> physicalLinks;
  private int amountNodes;
  private int amountLinks;

  public SubstrateNetwork() {
    this.physicalNodes = new HashMap<Integer, PhysicalNode>();
    this.physicalLinks = new HashMap<String, PhysicalLink>();
  }

  public SubstrateNetwork(HashMap<Integer, PhysicalNode> nodes,
                          HashMap<String, PhysicalLink> links) {
    setHashNodes(nodes);
    setHashLinks(links);
  }

  public HashMap<Integer, PhysicalNode> getHashNodes() {
    return physicalNodes;
  }

  public void setHashNodes(HashMap<Integer, PhysicalNode> physicalNodes) {
    this.physicalNodes = physicalNodes;
    this.amountNodes = physicalNodes.size();
  }

  public HashMap<String, PhysicalLink> getHashLinks() {
    return physicalLinks;
  }

  public void setHashLinks(HashMap<String, PhysicalLink> physicalLinks) {
    this.physicalLinks = physicalLinks;
    this.amountLinks = physicalLinks.size();
  }

  public int getAmountNodes() {
    return amountNodes;
  }

  public void setAmountNodes(int amountNodes) {
    this.amountNodes = amountNodes;
  }

  public int getAmountLinks() {
    return amountLinks;
  }

  public void setAmountLinks(int amountLinks) {
    this.amountLinks = amountLinks;
  }

  /*
   * Retorna ArrayList com nós virtuais que têm capacidade maior que a
   * especificada como parâmetro.
   */
  public ArrayList<PhysicalNode> getPhysicalNodes(double capacity) {
    ArrayList<PhysicalNode> capablePhysicalNodes = new ArrayList<PhysicalNode>();
    for(PhysicalNode node : physicalNodes.values()) {
      if(node.getCapacity() - node.getLoad() > capacity) {
        capablePhysicalNodes.add(node);
      }
    }

    return capablePhysicalNodes;
  }

  public String toString() {
    String representation = "";
    representation = representation.concat("************************************\n");
    representation = representation.concat(String.format("Rede Substrato: %d nós e %d enlaces\n",
        physicalNodes.size(), physicalLinks.size()));
    representation = representation.concat("************************************\n");
    for(PhysicalNode physicalNode : physicalNodes.values().toArray(new PhysicalNode[1])) {
      representation = representation.concat(String.format("Nó %d: \n\tCPU: %f\n\tCusto: %d\n",
          physicalNode.getId(), physicalNode.getCapacity()));
    }
    for(PhysicalLink physicalLink : physicalLinks.values().toArray(new PhysicalLink[1])) {
      representation = representation.concat(String.format("Enlace %s: origem %d, destino %d\n\tBanda: %f\n\tCusto: %d\n\tDelay: %d\n",
          physicalLink.getId(), physicalLink.getSourceNode().getId(), physicalLink.getDestinyNode().getId(),
          physicalLink.getBandwidthCapacity(), physicalLink.getCost(), physicalLink.getDelay()));
    }

    return representation;
  }

  /**
   *
   * CÓDIGO LEGADO
   *
   */
  // import simulator.network.components.physical.CoordinatedNode;
  // import simulator.network.components.virtual.VirtualLink;
  // import simulator.network.components.virtual.VirtualNode;

  // private HashMap<String, VirtualLink> hashVirtualLinks;

  // construtor
    // this.hashVirtualLinks = new HashMap<String, VirtualLink>();

  // public SubstrateNetwork(HashMap<Integer, PhysicalNode> physicalNodes, HashMap<String, PhysicalLink> physicalLinks, HashMap<String, VirtualLink> hashVirtualLinks) {
  //   this.physicalNodes = physicalNodes;
  //   this.physicalLinks = physicalLinks;
  //   // this.hashVirtualLinks = hashVirtualLinks;
  //   this.amountNodes = physicalNodes.size();
  //   this.amountLinks = physicalLinks.size();
  // }

  // public HashMap<String, VirtualLink> getHashVirtualLinks() {
  //   return hashVirtualLinks;
  // }

  // public void setHashVirtualLinks(HashMap<String, VirtualLink> hashVirtualLinks) {
  //   this.hashVirtualLinks = hashVirtualLinks;
  // }

  // toString
      // if(physicalNode instanceof CoordinatedNode) {
      //   CoordinatedNode coordinatedNode = (CoordinatedNode) physicalNode;
      //   representation = representation.concat(String.format("\tX: %d, Y: %d\n",
      //       coordinatedNode.getX(), coordinatedNode.getY()));
      // }
}
