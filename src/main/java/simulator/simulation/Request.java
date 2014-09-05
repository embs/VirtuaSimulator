package simulator.simulation;

import java.util.HashMap;

import simulator.network.components.virtual.*;

/*
 *  Virtual Network Request
 */
public class Request {
  private int id;
  private int creationTime;
  private int lifeTime;
  private HashMap<Integer,VirtualNode> virtualNodes;
  private HashMap<String, VirtualLink> virtualLinks;
  private int amountVLinks;
  private int amountVNodes;

  public Request(int id, int creationTime, int lifeTime) {
    this.id = id;
    this.creationTime = creationTime;
    this.lifeTime = lifeTime;
    this.virtualNodes = new HashMap<Integer, VirtualNode>();
    this.virtualLinks = new HashMap<String, VirtualLink>();
  }

  public Request(int id, int creationTime, int lifeTime,
                 HashMap<Integer,VirtualNode> virtualNodes,
                 HashMap<String, VirtualLink> virtualLinks) {
    this.id = id;
    this.creationTime = creationTime;
    this.lifeTime = lifeTime;
    this.virtualNodes = virtualNodes;
    this.virtualLinks = virtualLinks;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public int getCreationTime() {
    return creationTime;
  }

  public void setCreationTime(int creationTime) {
    this.creationTime = creationTime;
  }

  public int getLifeTime() {
    return lifeTime;
  }

  public void setLifeTime(int lifeTime) {
    this.lifeTime = lifeTime;
  }

  public int getDepartureTime() {
    return creationTime + lifeTime;
  }

  public HashMap<Integer, VirtualNode> getVirtualNodes() {
    return virtualNodes;
  }

  public void setVirtualNodes(HashMap<Integer, VirtualNode> virtualNodes) {
    this.virtualNodes = virtualNodes;
  }

  public HashMap<String, VirtualLink> getVirtualLinks() {
    return virtualLinks;
  }

  public void setVirtualLinks(HashMap<String, VirtualLink> virtualLinks) {
    this.virtualLinks = virtualLinks;
  }

  public int getAmountNodes() {
    return virtualNodes.size();
  }

  public int getAmountLinks() {
    return virtualLinks.size();
  }

  /**
   *
   * CÓDIGO LEGADO
   *
   */
  // import java.math.BigDecimal;
  // import java.math.RoundingMode;
  // import java.util.ArrayList;
  // import java.util.Iterator;
  // import java.util.List;
  // import simulator.network.components.physical.PhysicalLink;
  // import simulator.network.components.physical.PhysicalNode;
  // import static simulator.util.Config.MATH_CONTEXT;
  // import static simulator.util.Config.considerIntermediaryNodes;

  // private Mapping mapping;
  // private boolean allocated;
  // private int rejectionCause;

  // Conta quantas vezes um nó virtual é alocado num mesmo nó físico
  // private int samePhyNodeCounter;

  // Carga dos nós da rede substrato no instante em que a requisição foi aceita.
  // private double networkNodesLoadOnRequestAcceptance;

  // Carga dos links da rede substrato no instante em que a requisição foi aceita.
  // private double networkLinksLoadOnRequestAcceptance;

  // public static final int NODE_REJECTION_CAUSE = 0;
  // public static final int LINK_REJECTION_CAUSE = 1;

  // public Request(int id, HashMap<Integer,VirtualNode> virtualNodes, ArrayList<VirtualLink> virtualLinks) {
  //   super();
  //   this.id = id;
  //   this.virtualNodes = virtualNodes;
  //   this.virtualLinks = virtualLinks;
  //   this.amountVLinks = virtualLinks.size();
  //   this.amountVNodes = virtualNodes.size();
  //   this.allocated = false;
  //   this.rejectionCause = -1;
  //   this.samePhyNodeCounter = 0;
  // }

  // public Mapping getMapping() {
  //   return mapping;
  // }

  // public void setMapping(Mapping mapping) {
  //   this.mapping = mapping;
  // }

  // public VirtualNode getVirtualNode(int index) {
  //   return virtualNodes.get(index);
  // }

  // public boolean isAllocated() {
  //   return this.allocated;
  // }

  // public void setAllocated(boolean allocated) {
  //   this.allocated = allocated;
  // }

  // public void setRejectionCause(int rejectionCause) {
  //   this.rejectionCause = rejectionCause;
  // }

  // public int getRejectionCause() {
  //   return this.rejectionCause;
  // }

  // public void setNetworkNodesLoadOnAcceptance(double load) {
  //   this.networkNodesLoadOnRequestAcceptance = load;
  // }

  // public double getNetworkNodesLoadOnAcceptance() {
  //   return this.networkNodesLoadOnRequestAcceptance;
  // }

  // public void setNetworkLinksLoadOnAcceptance(double load) {
  //   this.networkLinksLoadOnRequestAcceptance = load;
  // }

  // public double getNetworkLinksLoadOnAcceptance() {
  //   return this.networkLinksLoadOnRequestAcceptance;
  // }

  // public int getSamePhyNodeCounter() {
  //   return this.samePhyNodeCounter;
  // }

  // /**
  //  *
  //  * @return BigDecimal disponibilidade da requisição, que é um produto das
  //  * disponibilidades de:<br>
  //  *    * Nós físicos que abrigam nós virtuais (VM + Router), sem repetições;<br>
  //  *    * Nós físicos que intermedeiam mapeamentos de enlaces virtuais (Router)
  //  * e não abrigam nós virtuais, sem repetições;<br>
  //  *    * Enlaces físicos que abrigam enlaces virtuais, sem repetições;
  //  */
  // public BigDecimal getAvailability() {
  //   if(!allocated)
  //     return new BigDecimal(0, MATH_CONTEXT);

  //   BigDecimal availability = new BigDecimal(1, MATH_CONTEXT);
  //   List<PhysicalNode> physicalHostNodesOnRequest = getPhysicalHostNodesOnRequest();

  //   // Multiplica disponibilidades de nós físicos que alocam alguma máquina virtual
  //   Iterator<PhysicalNode> physicalNodesIterator = physicalHostNodesOnRequest.iterator();
  //   while(physicalNodesIterator.hasNext()) {
  //     PhysicalNode node = physicalNodesIterator.next();
  //     availability = availability.multiply(node.getVirtualMachineAvailability(), MATH_CONTEXT);
  //   }

  //   // Multiplica disponibilidades de nós físicos que intermediam mapemanto de enlaces virtuais
  //   Iterator<PhysicalNode> physicalIntermediaryNodesIterator =
  //       getPhysicalIntermediaryNodesOnRequest(physicalHostNodesOnRequest).iterator();
  //   while(physicalIntermediaryNodesIterator.hasNext()) {
  //     PhysicalNode physicalIntermediaryNode = physicalIntermediaryNodesIterator.next();
  //     availability = availability.multiply(physicalIntermediaryNode.getRouterAvailability(), MATH_CONTEXT);
  //   }

  //   // Multiplica disponibilidades de enlaces físicos que alocam enlaces virtuais
  //   Iterator<PhysicalLink> physicalHostLinks = getPhysicalLinksOnRequest().iterator();
  //   while(physicalHostLinks.hasNext()) {
  //     availability = availability.multiply(physicalHostLinks.next().getAvailability(), MATH_CONTEXT);
  //   }

  //   return availability;
  // }

  // /**
  //  *
  //  * @return Número de nós virtuais que compartilham nó físico / número total de nós virtuais
  //  */
  // public double getSharedPhyNodesRate() {
  //   int vNodesSharingSomePhyNode = 0;
  //   Iterator<PhysicalNode> phyNodesIt = getPhysicalHostNodesOnRequest().iterator();
  //   while(phyNodesIt.hasNext()) {
  //     PhysicalNode node = phyNodesIt.next();
  //     // Verificação necessária devido a possibilidade de se considerar nós físicos intermediários
  //     if(!node.getHashVirtualNodes().isEmpty()) {
  //       ArrayList<VirtualNode> vNodesOnPhyNode = node.getHashVirtualNodes().get(this.id);
  //       if(vNodesOnPhyNode != null && vNodesOnPhyNode.size() > 1) {
  //         vNodesSharingSomePhyNode += vNodesOnPhyNode.size();
  //       }
  //     }
  //   }

  //   return (double) vNodesSharingSomePhyNode / amountVNodes;
  // }

  // /**
  //  *
  //  * @return List<PhysicalNode> com nós físicos que alocam alguma máquina virtual.
  //  */
  // public List<PhysicalNode> getPhysicalHostNodesOnRequest() {
  //   List<PhysicalNode> physicalHostNodesOnRequest = new ArrayList<PhysicalNode>();
  //   Iterator<VirtualNode> vNodesIterator = virtualNodes.values().iterator();
  //   while(vNodesIterator.hasNext()) {
  //     VirtualNode virtualNode = vNodesIterator.next();
  //     if(!physicalHostNodesOnRequest.contains(virtualNode.getPhysicalHostNode())) {
  //       physicalHostNodesOnRequest.add(virtualNode.getPhysicalHostNode());
  //     }
  //   }

  //   return physicalHostNodesOnRequest;
  // }

  // /**
  //  *
  //  * @param physicalHostNodesOnRequest nós físicos que alocam alguma máquina virtual.
  //  * @return List<PhysicalNode> com nós físicos que intermediam mapeamentos de enlaces
  //  * virtuais mas não alocam máquina virtual.
  //  */
  // public List<PhysicalNode> getPhysicalIntermediaryNodesOnRequest(List<PhysicalNode> physicalHostNodesOnRequest) {
  //   if(!considerIntermediaryNodes)
  //     return new ArrayList<PhysicalNode>();

  //   List<PhysicalNode> physicalIntermediaryNodesOnRequest = new ArrayList<PhysicalNode>();
  //   Iterator<VirtualLink> virtualLinksIterator = virtualLinks.iterator();
  //   while(virtualLinksIterator.hasNext()) {
  //     VirtualLink virtualLink = virtualLinksIterator.next();
  //     Iterator<PhysicalLink> physicalLinksIterator = virtualLink.getMappedPhysicalLinksList().iterator();
  //     while(physicalLinksIterator.hasNext()) {
  //       PhysicalLink physicalLink = physicalLinksIterator.next();
  //       PhysicalNode[] physicalNodes = { (PhysicalNode) physicalLink.getSourceNode(),
  //           (PhysicalNode) physicalLink.getDestinyNode() };
  //       for(PhysicalNode physicalNode : physicalNodes) {
  //         if(!physicalIntermediaryNodesOnRequest.contains(physicalNode) &&
  //             !physicalHostNodesOnRequest.contains(physicalNode)) {
  //           physicalIntermediaryNodesOnRequest.add(physicalNode);
  //         }
  //       }
  //     }
  //   }

  //   return physicalIntermediaryNodesOnRequest;
  // }

  // public List<PhysicalLink> getPhysicalLinksOnRequest() {
  //   List<PhysicalLink> physicalLinksOnRequest = new ArrayList<PhysicalLink>();
  //   for(VirtualLink virtualLink : virtualLinks) {
  //     for(PhysicalLink physicalHostLink : virtualLink.getMappedPhysicalLinksList()) {
  //       if(!physicalLinksOnRequest.contains(physicalHostLink)) {
  //         physicalLinksOnRequest.add(physicalHostLink);
  //       }
  //     }
  //   }

  //   return physicalLinksOnRequest;
  // }

  // public String toString() {
  //   if(allocated) {
  //     return id + " " + allocated + " " + networkNodesLoadOnRequestAcceptance
  //         + " " + networkLinksLoadOnRequestAcceptance + " "
  //         + getAvailability().setScale(5, RoundingMode.HALF_UP)
  //         + " " + getSharedPhyNodesRate();
  //   } else {
  //     return String.format("%d %s %d", id, allocated, rejectionCause);
  //   }
  // }
}
