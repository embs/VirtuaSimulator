package generator.tasks;

import generator.network.SubstrateNetworkGenerator;
import generator.network.VirtualNetworksGenerator;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;

import simulator.network.SubstrateNetwork;
import simulator.network.components.physical.*;
import simulator.network.components.virtual.*;
import simulator.simulation.Request;

public class CreateVirtuaVNMPs {
  public static void main(String[] args) {
    String baseDir = "/media/embs/Data/VirtuaVNMPs/";
    for(int i = 0; i < 30; i++) {
      createVirtuaVNMP(baseDir + String.format("vnmp_%s.txt", i));
    }
  }

  private static void createVirtuaVNMP(String fileName) {
    SubstrateNetwork substrateNetwork = new SubstrateNetworkGenerator().generate(50);
    ArrayList<Request> requests = new VirtualNetworksGenerator().
      generateVirtualNetworks(40);
    try {
      PrintWriter writer = new PrintWriter(fileName);
      writer.println(substrateNetwork.getAmountNodes() + " "
        + substrateNetwork.getAmountLinks());
      for(PhysicalNode node : substrateNetwork.getHashNodes().values()) {
        writer.println(node.getId() + " " + node.getCapacity());
      }
      for(PhysicalLink link : substrateNetwork.getHashLinks().values()) {
        writer.println(link.getSourceNode().getId() + " "
          + link.getDestinyNode().getId() + " " + link.getBandwidthCapacity()
          + " " + link.getDelay() + " " + link.getCost());
      }
      writer.println(requests.size());
      for(Request request : requests) {
        writer.println(request.getId() + " " + request.getAmountNodes() + " "
          + request.getAmountLinks() + " " + request.getCreationTime() + " "
          + request.getLifeTime());
        for(VirtualNode node : request.getVirtualNodes().values()) {
          writer.println(node.getId() + " " + node.getCapacity());
        }
        for(VirtualLink link : request.getVirtualLinks().values()) {
          writer.println(link.getSourceNode().getId() + " "
            + link.getDestinyNode().getId() + " " + link.getBandwidthCapacity()
            + " " + link.getDelay());
        }
      }
      writer.close();
    } catch(FileNotFoundException e) {
      e.printStackTrace();
    }
  }
}
