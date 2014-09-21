package generator.network.components;

import generator.util.Randomizer;

import simulator.network.components.physical.*;
import simulator.network.components.virtual.*;

public class LinkGenerator {
  private Randomizer randomizer;

  public LinkGenerator() {
    randomizer = Randomizer.getInstance();
  }

  public PhysicalLink generatePhysicalLink(String id, PhysicalNode sourceNode,
                                                     PhysicalNode destinyNode) {
    double bandwidthDecimalPortion = randomizer.nextDouble();
    int bandwidthIntegerPortion = randomizer.nextInt(50) + 50;
    double bandwidth = (double) bandwidthIntegerPortion + bandwidthDecimalPortion;

    return new PhysicalLink(id, sourceNode, destinyNode, bandwidth, 0, 0);
  }

  public VirtualLink generateVirtualLink(String id, VirtualNode sourceNode,
                                                      VirtualNode destinyNode) {
    double bandwidthDecimalPortion = randomizer.nextDouble();
    int bandwidthIntegerPortion = randomizer.nextInt(51);
    double bandwidth = (double) bandwidthIntegerPortion + bandwidthDecimalPortion;

    return new VirtualLink(id, sourceNode, destinyNode, bandwidth, 0);
  }
}
