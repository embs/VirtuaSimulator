package simulator.network.components.physical;

import java.math.BigDecimal;

import generator.dependability.AvailabilityGenerator;

import simulator.network.components.Link;
import simulator.network.components.physical.PhysicalNode;
import simulator.network.components.virtual.VirtualLink;

public class PhysicalLink extends Link {
  private BigDecimal bandwidthLoad;
  private int cost;
  private BigDecimal availability;

  public PhysicalLink(String id, PhysicalNode sourceNode, PhysicalNode destinyNode,
                      double bandwidth, double delay, int cost) {
    super(id, sourceNode, destinyNode, bandwidth, delay);
    bandwidthLoad = new BigDecimal(0);
    this.cost = cost;
    this.availability = AvailabilityGenerator.getInstance().
      generateComponentAvailability(AvailabilityGenerator.LINK_FAILURE_RATE,
                                    AvailabilityGenerator.LINK_MTTR);
  }

  public double getBandwidthLoad() {
    return bandwidthLoad.doubleValue();
  }

  public void addBandwidthLoad(double bandwidth) {
    bandwidthLoad = bandwidthLoad.add(BigDecimal.valueOf(bandwidth));
    if(bandwidthLoad.doubleValue() > getBandwidthCapacity())
      throw new RuntimeException("Enlace físico não possui capacidade suficiente.");
  }

  public void removeBandwidthLoad(double bandwidth) {
    bandwidthLoad = bandwidthLoad.subtract(BigDecimal.valueOf(bandwidth));
    if(bandwidthLoad.doubleValue() < 0)
      throw new RuntimeException("Carga de enlace físico negativa.");
  }

  public double getRemainingBandwidth() {
    return BigDecimal.valueOf(this.getBandwidthCapacity()).subtract(bandwidthLoad).doubleValue();
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
}
