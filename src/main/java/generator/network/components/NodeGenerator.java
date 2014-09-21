package generator.network.components;

import generator.util.Randomizer;

import simulator.network.components.physical.PhysicalNode;
import simulator.network.components.virtual.VirtualNode;

public class NodeGenerator {
  private Randomizer randomizer;

  public NodeGenerator() {
    randomizer = Randomizer.getInstance();
  }

  public PhysicalNode generatePhysicalNode(int id) {
    double capacityDecimalPortion = randomizer.nextDouble();
    int capacityIntegerPortion = randomizer.nextInt(50) + 50;
    double capacity = (double) capacityIntegerPortion + capacityDecimalPortion;

    return new PhysicalNode(id, capacity);
  }

  public VirtualNode generateVirtualNode(int id) {
    double capacityDecimalPortion = randomizer.nextDouble();
    int capacityIntegerPortion = randomizer.nextInt(21);
    double capacity = (double) capacityIntegerPortion + capacityDecimalPortion;

    return new VirtualNode(id, capacity);
  }
}
