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
    HashMap<String, PhysicalLink> links = new HashMap<String, PhysicalLink>();

    int degreeSum = 0;
    for(int i = 0; i < numberOfNodes; i++) {
      PhysicalNode newNode = nodeGenerator.generatePhysicalNode(i);
      while(newNode.getAttachedLinks().size() == 0 && i != 0) {
        for(PhysicalNode node : nodes.values()) {
          if(degreeSum == 0 || randomizer.nextInt(degreeSum) < node.getAttachedLinks().size()) {
            degreeSum += 2;
            String newLinkId = String.format("%s:%s",
              Math.max(node.getId(), newNode.getId()),
              Math.min(node.getId(), newNode.getId()));
            PhysicalLink newLink = linkGenerator.generatePhysicalLink(newLinkId,
              newNode, node);
            links.put(newLinkId, newLink);
          }
        }
      }
      nodes.put(i, newNode);
    }


    return new SubstrateNetwork(nodes, links);
  }
}
