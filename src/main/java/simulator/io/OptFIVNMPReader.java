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
 *  OptFI (https://www.ads.tuwien.ac.at/projects/optFI/Main_Page) VNMPs Reader
 */
public class OptFIVNMPReader implements IVNMPReader {
  private SubstrateNetwork substrateNetwork;
  private ArrayList<Request> requests;
  private Scanner scanner;

  public OptFIVNMPReader(String filename) {
    try {
      scanner = new Scanner(new File(filename));
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
    readSubstrateNetwork(null);
    readVirtualNetworkRequests(null);
  }

  public SubstrateNetwork readSubstrateNetwork(String filename) {
    HashMap<Integer, PhysicalNode> physicalNodes = new HashMap<Integer, PhysicalNode>();
    HashMap<String, PhysicalLink> physicalLinks = new HashMap<String, PhysicalLink>();
    scanner.nextLine(); // #Substrate Graph
    scanner.nextLine(); // # noVertices
    int amountNodes = Integer.valueOf(scanner.nextLine());
    scanner.nextLine(); // # noArcs
    int amountLinks = Integer.valueOf(scanner.nextLine());
    scanner.nextLine(); // # Vertices: id cost cpu group
    for(int i = 0; i < amountNodes; i++) {
      String[] line = scanner.nextLine().split(" ");
      int nodeId = Integer.valueOf(line[0]);
      physicalNodes.put(nodeId, new PhysicalNode(nodeId, Integer.valueOf(line[2])));
    }
    scanner.nextLine(); // # Arcs: idS idT bandwidth cost delay
    for(int i = 0; i < amountLinks; i++) {
      String[] line = scanner.nextLine().split(" ");
      int sourceNodeId = Math.max(Integer.valueOf(line[0]), Integer.valueOf(line[1]));
      int destinyNodeId = Math.min(Integer.valueOf(line[0]), Integer.valueOf(line[1]));
      String linkId = String.format("%s:%s", sourceNodeId, destinyNodeId);
      if(!physicalLinks.containsKey(linkId)) {
        physicalLinks.put(linkId,
                          new PhysicalLink(linkId, physicalNodes.get(sourceNodeId),
                                           physicalNodes.get(destinyNodeId),
                                           Double.valueOf(line[2]),
                                           Integer.valueOf(line[4]),
                                           Integer.valueOf(line[3])));
      }
    }
    this.substrateNetwork = new SubstrateNetwork(physicalNodes, physicalLinks);

    return this.substrateNetwork;
  }

  public ArrayList<Request> readVirtualNetworkRequests(String filename) {
    requests = new ArrayList<Request>();
    int idRequest = 0;
    HashMap<Integer, VirtualNode> virtualNodes = new HashMap<Integer, VirtualNode>();
    HashMap<String, VirtualLink> virtualLinks = new HashMap<String, VirtualLink>();
    String l = scanner.nextLine(); // # noSlices
    scanner.nextLine(); // 40
    scanner.nextLine(); // #Virtual Graph
    while(scanner.hasNextLine()) {
      l = scanner.nextLine(); // # noVertices
      int amountNodes = Integer.valueOf(scanner.nextLine());
      scanner.nextLine(); // # noArcs
      int amountLinks = Integer.valueOf(scanner.nextLine());
      scanner.nextLine(); // # Vertices: id cpu
      for(int i = 0; i < amountNodes; i++) {
        String[] line = scanner.nextLine().split(" ");
        int nodeId = Integer.valueOf(line[0]);
        virtualNodes.put(nodeId, new VirtualNode(nodeId, Integer.valueOf(line[1])));
      }
      scanner.nextLine(); // # Arcs: idS idT bandwidth delay
      for(int i = 0; i < amountLinks; i++) {
        String[] line = scanner.nextLine().split(" ");
        int sourceNodeId = Math.max(Integer.valueOf(line[0]), Integer.valueOf(line[1]));
        int destinyNodeId = Math.min(Integer.valueOf(line[0]), Integer.valueOf(line[1]));
        String linkId = String.format("%s:%s", sourceNodeId, destinyNodeId);
        if(!virtualLinks.containsKey(linkId)) {
          virtualLinks.put(linkId,
                            new VirtualLink(linkId, virtualNodes.get(sourceNodeId),
                                            virtualNodes.get(destinyNodeId),
                                            Double.valueOf(line[2]),
                                            Integer.valueOf(line[3])));
        }
      }
      requests.add(new Request(idRequest, idRequest, 5000, virtualNodes, virtualLinks));
      idRequest++;
      while(scanner.hasNextLine() && !l.equals("#Virtual Graph")) {
        l = scanner.nextLine();
      }
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
