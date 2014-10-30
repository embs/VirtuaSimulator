package simulator.network.components.physical;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;

import generator.dependability.AvailabilityGenerator;

import simulator.network.components.Node;
import simulator.network.components.virtual.VirtualNode;
import simulator.util.Clock;

import static simulator.util.Config.COMPARE_NODES_BY_AVAILABILITY;
import static simulator.util.Config.SOFTWARE_AGING_CONSTANT;
import static simulator.util.Config.softwareAgingScenario;

public class PhysicalNode extends Node implements Comparable<PhysicalNode> {
  private double load;
  private BigDecimal machineAvailability;
  private BigDecimal hypervisorAvailability;
  private int startTime;
  private int releaseTime;
  private BigDecimal operatingSystemAvailability;

  public PhysicalNode (int id, double capacity) {
    super(id, capacity);
    load = 0;
    this.machineAvailability = AvailabilityGenerator.getInstance().
      generateMachineAvailability();
    this.hypervisorAvailability = AvailabilityGenerator.getInstance().
      generateComponentAvailability(AvailabilityGenerator.HYPERVISOR_FAILURE_RATE,
        AvailabilityGenerator.HYPERVISOR_MTTR);
    startTime = releaseTime = -1;
    this.operatingSystemAvailability = AvailabilityGenerator.getInstance().
      generateComponentAvailability(AvailabilityGenerator.OPERATING_SYSTEM_MTTF,
        AvailabilityGenerator.OPERATING_SYSTEM_MTTR);
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
    int upTime = getUpTime();
    double lambda = 1D / 4000;
    double rate;
    if(upTime > 0) {
      rate = Math.pow(Math.E, -1 * lambda * upTime);
    } else {
      rate = 1;
    }

    return hypervisorAvailability.multiply(machineAvailability).multiply(
      operatingSystemAvailability).multiply(new BigDecimal(rate));
  }

  public BigDecimal getIntermediaryNodeAvailability() {
    return machineAvailability;
  }

  public int getStartTime() {
    return startTime;
  }

  public void setStartTime(int time) {
    startTime = time;
  }

  public int getReleaseTime() {
    return releaseTime;
  }

  public void setReleaseTime(int time) {
    releaseTime = time;
  }

  public int getUpTime() {
    int currentTime = Clock.instance().time();
    if(startTime == -1)
      return 0;

    return currentTime - startTime;
  }

  @Override
  public int compareTo(PhysicalNode node) {
    return node.getNodeAvailability().compareTo(
      this.getNodeAvailability());
  }
}
