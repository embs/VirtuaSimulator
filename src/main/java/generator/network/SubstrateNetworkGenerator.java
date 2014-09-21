package generator.network;

import generator.network.components.NodeGenerator;
import generator.network.components.LinkGenerator;
import generator.util.Randomizer;

import java.util.HashMap;

import simulator.network.SubstrateNetwork;
import simulator.network.components.physical.PhysicalLink;
import simulator.network.components.physical.PhysicalNode;

public class SubstrateNetworkGenerator {
  private Randomizer randomizer;
  private NodeGenerator nodeGenerator;
  private LinkGenerator linkGenerator;

  public SubstrateNetworkGenerator() {
    randomizer = Randomizer.getInstance();
    nodeGenerator = new NodeGenerator();
    linkGenerator = new LinkGenerator();
  }

  public SubstrateNetwork generate(int numberOfNodes) {
    HashMap<Integer, PhysicalNode> nodes = new HashMap<Integer, PhysicalNode>();
    for(int i = 0; i < numberOfNodes; i++) {
      nodes.put(i, nodeGenerator.generatePhysicalNode(i));
    }

    HashMap<String, PhysicalLink> links = new HashMap<String, PhysicalLink>();
    for(PhysicalNode source : nodes.values()) {
      for(PhysicalNode destiny : nodes.values()) {
        if(!source.equals(destiny) && source.getId() > destiny.getId()
           && randomizer.nextBoolean()) {
          String linkId = String.format("%s:%s", destiny.getId(), source.getId());
          links.put(linkId,
            linkGenerator.generatePhysicalLink(linkId, source, destiny));
        }
      }
    }

    return new SubstrateNetwork(nodes, links);
  }
}
