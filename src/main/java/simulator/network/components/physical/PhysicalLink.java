package simulator.network.components.physical;

import java.math.BigDecimal;

import simulator.network.components.Link;
import simulator.network.components.physical.PhysicalNode;
import simulator.network.components.virtual.VirtualLink;
import simulator.dependability.AvailabilityGenerator;

public class PhysicalLink extends Link {
  private double bandwidthLoad;
  private int cost;
  private BigDecimal availability;

  public PhysicalLink(String id, PhysicalNode sourceNode, PhysicalNode destinyNode,
                      double bandwidth, int delay, int cost) {
    super(id, sourceNode, destinyNode, bandwidth, delay);
    bandwidthLoad = 0;
    this.cost = cost;
    this.availability = AvailabilityGenerator.getInstance().
      generateComponentAvailability(AvailabilityGenerator.LINK_FAILURE_RATE,
                                    AvailabilityGenerator.LINK_MTTR);
  }

  public double getBandwidthLoad() {
    return bandwidthLoad;
  }

  public void addBandwidthLoad(double bandwidth) {
    this.bandwidthLoad += bandwidth;
  }

  public void removeBandwidthLoad(double bandwidth) {
    this.bandwidthLoad -= bandwidth;
  }

  public double getRemainingBandwidth() {
    return this.getBandwidthCapacity() - bandwidthLoad;
  }

  public int getCost() {
    return cost;
  }

  public void setCost(int cost) {
    this.cost = cost;
  }

  public boolean canHost(VirtualLink virtualLink) {
    return this.getRemainingBandwidth() >= virtualLink.getBandwidthCapacity();
  }

  public BigDecimal getAvailability() {
    return this.availability;
  }

  /**
   *
   * CÓDIGO LEGADO
   *
   */
  // import java.util.HashMap;
  // import java.util.ArrayList;

  // HashMap<Integer, ArrayList<VirtualLink>> hashVirtualLinks;

  // construtor
    // this.hashVirtualLinks = new HashMap<Integer, ArrayList<VirtualLink>>();

  // public boolean allocVirtualLink(int idRequest, VirtualLink vLink)
  // {
  //   if(bandWidthLoad + vLink.getBandWidthCapacity() <= this.getBandWidthCapacity()) {
  //     bandWidthLoad = bandWidthLoad+vLink.getBandWidthCapacity();
  //     vLink.addPhysicalLinkToPhysicalHostLinks(this);
  //     if(hashVirtualLinks.containsKey(idRequest)) {
  //       ArrayList<VirtualLink> listNode=hashVirtualLinks.get(idRequest);
  //       listNode.add(vLink);
  //     } else {
  //       ArrayList<VirtualLink> listLinks=new ArrayList<>();
  //       listLinks.add(vLink);
  //       hashVirtualLinks.put(idRequest, listLinks);
  //     }

  //     return true;
  //   }

  //   return false;
  // }
  // public void desAllocVirtualLink(int idRequest)
  // {
  //   if(hashVirtualLinks.containsKey(idRequest))
  //   {
  //     ArrayList<VirtualLink> listVirLinks=hashVirtualLinks.get(idRequest);
  //     for(VirtualLink vLink:listVirLinks)
  //       bandWidthLoad=bandWidthLoad-vLink.getBandWidthCapacity();
  //     hashVirtualLinks.remove(idRequest);
  //   }
  // }

  // public HashMap<Integer, ArrayList<VirtualLink>> getHashVirtualLinks() {
  //   return hashVirtualLinks;
  // }

  // public void setHashVirtualLinks(HashMap<Integer, ArrayList<VirtualLink>> hashVirtualLinks) {
  //   this.hashVirtualLinks = hashVirtualLinks;
  // }
}
