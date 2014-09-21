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
      int numberOfNodes = randomizer.nextInt(9) + 2;
      for(int i = 0; i < numberOfNodes; i++) {
        nodes.put(i, nodeGenerator.generateVirtualNode(i));
      }

      HashMap<String, VirtualLink> links = new HashMap<String, VirtualLink>();
      for(VirtualNode source : nodes.values()) {
        for(VirtualNode destiny : nodes.values()) {
          if(!source.equals(destiny) && source.getId() > destiny.getId()
             && randomizer.nextBoolean()) {
            String linkId = String.format("%s:%s", source.getId(), destiny.getId());
            links.put(linkId, linkGenerator.generateVirtualLink(linkId, source,
              destiny));
          }
        }
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
