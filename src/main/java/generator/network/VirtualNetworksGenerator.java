package generator.network;

import generator.network.components.*;
import generator.util.Randomizer;

import java.util.ArrayList;
import java.util.HashMap;

import simulator.network.components.virtual.*;
import simulator.simulation.Request;

public class VirtualNetworksGenerator {
  private Randomizer randomizer;
  private NodeGenerator nodeGenerator;
  private LinkGenerator linkGenerator;

  public VirtualNetworksGenerator() {
    randomizer = Randomizer.getInstance();
    nodeGenerator = new NodeGenerator();
    linkGenerator = new LinkGenerator();
  }

  public ArrayList<Request> generateVirtualNetworks(int numberOfNetworks) {
    ArrayList<Request> requests = new ArrayList<Request>();
    int baseTime = 0;
    int requestsPer100UnitsTime = randomizer.nextPoisson();
    for(int idRequest = 0; idRequest < numberOfNetworks; idRequest++) {
      HashMap<Integer,VirtualNode> nodes = new HashMap<Integer, VirtualNode>();
      HashMap<String, VirtualLink> links = new HashMap<String, VirtualLink>();
      int numberOfNodes = randomizer.nextInt(9) + 2;

      int degreeSum = 0;
      for(int i = 0; i < numberOfNodes; i++) {
        VirtualNode newNode = nodeGenerator.generateVirtualNode(i);
        while(newNode.getAttachedLinks().size() == 0 && i != 0) {
          for(VirtualNode node : nodes.values()) {
            if(degreeSum == 0 || randomizer.nextInt(degreeSum) < node.getAttachedLinks().size()) {
              degreeSum += 2;
              String newLinkId = String.format("%s:%s",
                Math.max(node.getId(), newNode.getId()),
                Math.min(node.getId(), newNode.getId()));
              VirtualLink newLink = linkGenerator.generateVirtualLink(newLinkId,
                newNode, node);
              links.put(newLinkId, newLink);
            }
          }
        }
        nodes.put(i, newNode);
      }

      int creationTime = baseTime + randomizer.nextInt(100) + 1;
      int lifeTime = randomizer.nextExponential();
      requestsPer100UnitsTime--;
      if(requestsPer100UnitsTime <= 0) {
        requestsPer100UnitsTime = randomizer.nextPoisson();
        baseTime = baseTime + 100;
      }

      requests.add(idRequest, new Request(idRequest, creationTime, lifeTime,
        nodes, links));
    }

    return requests;
  }
}
