package simulator.network.components.physical;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;

import generator.dependability.AvailabilityGenerator;

import simulator.network.components.Node;
import simulator.network.components.virtual.VirtualNode;

import static simulator.util.Config.COMPARE_NODES_BY_AVAILABILITY;
import static simulator.util.Config.SOFTWARE_AGING_CONSTANT;
import static simulator.util.Config.softwareAgingScenario;

public class PhysicalNode extends Node implements Comparable<PhysicalNode> {
  private double load;
  private BigDecimal machineAvailability;
  private BigDecimal hypervisorAvailability;

  public PhysicalNode (int id, double capacity) {
    super(id, capacity);
    load = 0;
    this.machineAvailability = AvailabilityGenerator.getInstance().
      generateMachineAvailability();
    this.hypervisorAvailability = AvailabilityGenerator.getInstance().
      generateComponentAvailability(AvailabilityGenerator.HYPERVISOR_FAILURE_RATE,
        AvailabilityGenerator.HYPERVISOR_MTTR);
  }

  public double getLoad() {
    return this.load;
  }

  public void addLoad(double load) {
    this.load = (new BigDecimal(String.valueOf(this.load)).
      add(new BigDecimal(String.valueOf(load)))).doubleValue();
    if(load > getCapacity())
      throw new RuntimeException("Nó físico não possui capacidade suficiente.");
  }

  public void removeLoad(double load) {
    this.load = (new BigDecimal(String.valueOf(this.load)).
      subtract(new BigDecimal(String.valueOf(load)))).doubleValue();
    if(load < 0)
      throw new RuntimeException("Carga de nó físico negativa.");
  }

  public double getRemainingCapacity() {
    return this.getCapacity() - this.load;
  }

  public boolean canHost(VirtualNode virtualNode) {
    return this.getRemainingCapacity() >= virtualNode.getCapacity();
  }

  public BigDecimal getNodeAvailability() {
    return hypervisorAvailability.multiply(machineAvailability);
  }

  public BigDecimal getIntermediaryNodeAvailability() {
    return machineAvailability;
  }

  @Override
  public int compareTo(PhysicalNode node) {
    return node.getNodeAvailability().compareTo(
      this.getNodeAvailability());
  }

  /**
   *
   * CÓDIGO LEGADO
   *
   */
  // private ArrayList<PhysicalLink> listLinks;
  // private HashMap<Integer, ArrayList<VirtualNode>> hashVirtualNodes;
  // private HashMap<Integer, ArrayList<String>> shortestPathsHash;

  // public PhysicalNode (int id, double capacity) {
    // this.cost = cost;
    // this.listLinks = new ArrayList<PhysicalLink>();
    // this.hashVirtualNodes = new HashMap<Integer, ArrayList<VirtualNode>>();
    // this.shortestPathsHash = new HashMap<Integer, ArrayList<String>>();
  // }

  // public HashMap<Integer, ArrayList<String>> getShortestPathsHash() {
  //   return shortestPathsHash;
  // }

  // public void setShortestPathsHash(HashMap<Integer, ArrayList<String>> hashShortedPath) {
  //   this.shortestPathsHash = hashShortedPath;
  // }

  // public int getCost() {
  //   return cost;
  // }

  // public void setCost(int cost) {
  //   this.cost = cost;
  // }

  // public boolean allocVirtualNode(int idRequest, VirtualNode vNode) {
  //   if(load + vNode.getCapacity() <= this.getCapacity()) {
  //     load = load + vNode.getCapacity();
  //     if(hashVirtualNodes.containsKey(idRequest)) {
  //       ArrayList<VirtualNode> listNode = hashVirtualNodes.get(idRequest);
  //       listNode.add(vNode);
  //     } else {
  //       ArrayList<VirtualNode> listNode = new ArrayList<VirtualNode>();
  //       listNode.add(vNode);
  //       hashVirtualNodes.put(idRequest, listNode);
  //     }

  //     return true;
  //   }

  //   return false;
  // }

  // public void desAllocRequest(int idRequest) {
  //   if(hashVirtualNodes.containsKey(idRequest)) {
  //     ArrayList<VirtualNode> listVirNodes = hashVirtualNodes.get(idRequest);
  //     for(VirtualNode vNode:listVirNodes)
  //       load = load - vNode.getCapacity();
  //     hashVirtualNodes.remove(idRequest);
  //   }
  // }

  // public void addPhysicalLink(PhysicalLink link) {
  //   listLinks.add(link);
  // }

  // public ArrayList<PhysicalLink> getList_links() {
  //   return listLinks;
  // }

  // public void setListLinks(ArrayList<PhysicalLink> listLinks) {
  //   this.listLinks = listLinks;
  // }

  // public HashMap<Integer, ArrayList<VirtualNode>> getHashVirtualNodes() {
  //   return hashVirtualNodes;
  // }

  // public void setHashVirtualNodes(
  //     HashMap<Integer, ArrayList<VirtualNode>> hashVirtualNodes) {
  //   this.hashVirtualNodes = hashVirtualNodes;
  // }

  // public BigDecimal getHypervisorAvailability() {
  //   return this.hypervisorAvailability;
  // }

  // public BigDecimal getVirtualMachineAvailability() {
  //   double dLoad = (load / this.getCapacity()) * 100;
  //   if(dLoad > 0 && softwareAgingScenario) {
  //     double expRate = Math.pow(dLoad, 2) / 10000;
  //     double hypervisorDependabilityFactor = (1 - (SOFTWARE_AGING_CONSTANT * expRate));
  //     BigDecimal rateFinal= BigDecimal.valueOf(hypervisorDependabilityFactor);
  //     BigDecimal hypervisorAvailabilityFinal = hypervisorAvailability.multiply(rateFinal);

  //     return virtualMachineAvailability.multiply(hypervisorAvailabilityFinal);
  //   }

  //   return virtualMachineAvailability.multiply(hypervisorAvailability);
  // }

  // public BigDecimal getRouterAvailability() {
  //   return this.routerAvailability;
  // }

  // @Override
  // public int compareTo(PhysicalNode x) {
  //   if(COMPARE_NODES_BY_AVAILABILITY) {
  //     return x.getVirtualMachineAvailability().compareTo(this.getVirtualMachineAvailability());
  //   } else {
  //     if((this.getCapacity() - load) / this.getCapacity()>(x.getCapacity()-x.getLoad())/x.getCapacity())
  //       return -1;
  //     else if((this.getCapacity()-load) / this.getCapacity() < (x.getCapacity()-x.getLoad())/x.getCapacity())
  //       return 1;
  //     else return 0;
  //   }
}
