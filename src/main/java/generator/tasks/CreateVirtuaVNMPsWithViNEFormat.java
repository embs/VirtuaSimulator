package generator.tasks;

import generator.network.SubstrateNetworkGenerator;
import generator.network.VirtualNetworksGenerator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Random;

import simulator.network.SubstrateNetwork;
import simulator.network.components.physical.*;
import simulator.network.components.virtual.*;
import simulator.simulation.Request;

public class CreateVirtuaVNMPsWithViNEFormat {
  public static void main(String[] args) {
    String baseDir = "/media/embs/Data/vine-yard-virtua-vnmps/problems/";
    for(int i = 0; i < 30; i++) {
      createSubstrateNetwork(baseDir + String.format("vnmp_%s/", i));
      createVirtualNetworkRequests(baseDir + String.format("vnmp_%s/requests/", i));
    }
  }

  private static void createSubstrateNetwork(String baseDir) {
    new File(baseDir).mkdir();
    SubstrateNetwork substrateNetwork = new SubstrateNetworkGenerator().generate(50);
    Random random = new Random();

    String substrateNetworkFileName = baseDir + "sub.txt";

    try {
      PrintWriter writer = new PrintWriter(substrateNetworkFileName);
      writer.println(substrateNetwork.getAmountNodes() + " "
        + substrateNetwork.getAmountLinks());
      for(PhysicalNode node : substrateNetwork.getHashNodes().values()) {
        int x = random.nextInt(100);
        int y = random.nextInt(100);
        writer.println(x + " " + y + " " + node.getCapacity());
      }
      for(PhysicalLink link : substrateNetwork.getHashLinks().values()) {
        writer.println(link.getSourceNode().getId() + " "
          + link.getDestinyNode().getId() + " " + link.getBandwidthCapacity()
          + " " + link.getDelay());
      }
      writer.close();
    } catch(FileNotFoundException e) {
      e.printStackTrace();
    }
  }

  private static void createVirtualNetworkRequests(String baseDir) {
    new File(baseDir).mkdir();
    int numberOfVirtualNetworkRequests = 40;
    ArrayList<Request> requests = new VirtualNetworksGenerator().
      generateVirtualNetworks(numberOfVirtualNetworkRequests);
    Random random = new Random();

    for(int i = 0; i < numberOfVirtualNetworkRequests; i++) {
      String requestFilename = baseDir + String.format("req%s.txt", i);

      try {
        PrintWriter writer = new PrintWriter(requestFilename);
        Request request = requests.get(i);
        writer.println(request.getAmountNodes() + " " + request.getAmountLinks()
          + " 0 " + request.getCreationTime() + " " + request.getLifeTime() +
          " 0 200");
        for(VirtualNode node : request.getVirtualNodes().values()) {
          int x = random.nextInt(100);
          int y = random.nextInt(100);
          writer.println(x + " " + y + " " + node.getCapacity());
        }
        for(VirtualLink link : request.getVirtualLinks().values()) {
          writer.println(link.getSourceNode().getId() + " "
            + link.getDestinyNode().getId() + " " + link.getBandwidthCapacity()
            + " " + link.getDelay());
        }
        writer.close();
      } catch(FileNotFoundException e) {
        e.printStackTrace();
      }
    }
  }
}
