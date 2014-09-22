package simulator.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.HashMap;
import java.util.ArrayList;

import simulator.simulation.Request;
import simulator.network.SubstrateNetwork;
import simulator.network.components.physical.*;
import simulator.network.components.virtual.*;

/*
 *  ViNEYard VNMPs Reader
 */
public class ViNEYardVNMPReader implements IVNMPReader {
  private SubstrateNetwork substrateNetwork;
  private ArrayList<Request> requests;

  public ViNEYardVNMPReader(String substrateNetworkFilename,
                                                    String requestsFolderName) {
    readSubstrateNetwork(substrateNetworkFilename);
    readVirtualNetworkRequests(requestsFolderName);
  }

  public SubstrateNetwork readSubstrateNetwork(String filename) {
    Scanner scanner = null;
    try {
      scanner = new Scanner(new File(filename));
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
    HashMap<Integer, PhysicalNode> physicalNodes = new HashMap<Integer, PhysicalNode>();
    HashMap<String, PhysicalLink> physicalLinks = new HashMap<String, PhysicalLink>();
    String[] tokens = scanner.nextLine().split(" ");
    int amountNodes = Integer.valueOf(tokens[0]);
    int amountLinks = Integer.valueOf(tokens[1]);
    for(int i = 0; i < amountNodes; i++) {
      tokens = scanner.nextLine().split(" ");
      physicalNodes.put(i, new PhysicalNode(i, Double.valueOf(tokens[2])));
    }
    for(int i = 0; i < amountLinks; i++) {
      tokens = scanner.nextLine().split(" ");
      int sourceNodeId = Math.max(Integer.valueOf(tokens[0]),
        Integer.valueOf(tokens[1]));
      int destinyNodeId = Math.min(Integer.valueOf(tokens[0]),
        Integer.valueOf(tokens[1]));
      String linkId = String.format("%s:%s", sourceNodeId, destinyNodeId);
      if(!physicalLinks.containsKey(linkId)) {
        physicalLinks.put(linkId,
                          new PhysicalLink(linkId, physicalNodes.get(sourceNodeId),
                                           physicalNodes.get(destinyNodeId),
                                           Double.valueOf(tokens[2]),
                                           Double.valueOf(tokens[3]),
                                           0));
      }
    }
    this.substrateNetwork = new SubstrateNetwork(physicalNodes, physicalLinks);

    return this.substrateNetwork;
  }

  public ArrayList<Request> readVirtualNetworkRequests(String folderName) {
    requests = new ArrayList<Request>();
    File requestsFolder = new File(folderName);
    for(File requestFile : requestsFolder.listFiles()) {
      Scanner scanner = null;
      try {
        scanner = new Scanner(requestFile);
      } catch (FileNotFoundException e) {
        e.printStackTrace();
      }
      String[] tokens = scanner.nextLine().split(" ");
      int idRequest = Integer.valueOf(
        requestFile.getName().substring(3).split(".txt")[0]);
      HashMap<Integer, VirtualNode> virtualNodes = new HashMap<Integer, VirtualNode>();
      HashMap<String, VirtualLink> virtualLinks = new HashMap<String, VirtualLink>();
      int amountNodes = Integer.valueOf(tokens[0]);
      int amountLinks = Integer.valueOf(tokens[1]);
      int creationTime = Integer.valueOf(tokens[3]);
      int lifeTime = Integer.valueOf(tokens[4]);
      for(int i = 0; i < amountNodes; i++) {
        tokens = scanner.nextLine().split(" ");
        virtualNodes.put(i, new VirtualNode(i, Double.valueOf(tokens[2])));
      }
      for(int i = 0; i < amountLinks; i++) {
        tokens = scanner.nextLine().split(" ");
        int sourceNodeId = Math.max(Integer.valueOf(tokens[0]),
          Integer.valueOf(tokens[1]));
        int destinyNodeId = Math.min(Integer.valueOf(tokens[0]),
          Integer.valueOf(tokens[1]));
        String linkId = String.format("%s:%s", sourceNodeId, destinyNodeId);
        if(!virtualLinks.containsKey(linkId)) {
          virtualLinks.put(linkId,
                            new VirtualLink(linkId, virtualNodes.get(sourceNodeId),
                                            virtualNodes.get(destinyNodeId),
                                            Double.valueOf(tokens[2]),
                                            Double.valueOf(tokens[3])));
        }
      }
      requests.add(
        new Request(idRequest, creationTime, lifeTime, virtualNodes, virtualLinks));

      scanner.close();
    }

    return this.requests;
  }

  public SubstrateNetwork getSubstrateNetwork() {
    return substrateNetwork;
  }

  public ArrayList<Request> getVirtualNetworkRequests() {
    return requests;
  }
}
