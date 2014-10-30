package graphicator.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Scanner;

import simulator.io.ViNEYardVNMPReader;
import simulator.mapping.Mapping;
import simulator.network.components.physical.*;
import simulator.network.components.virtual.*;
import simulator.network.SubstrateNetwork;
import simulator.simulation.Request;

/*
 *  Lê mapeamentos realizados pelo ViNEYard para um dado VNMP. Útil para
 * avaliação da dependabilidade.
 */
public class ViNEYardMappingsReader {
  private SubstrateNetwork substrateNetwork;
  private HashMap<Integer, Request> requests;

  public ViNEYardMappingsReader(String substrateNetworkFilename,
                                                String requestsFolderFilename) {
    ViNEYardVNMPReader vnmpReader = new ViNEYardVNMPReader(
      substrateNetworkFilename, requestsFolderFilename);
    substrateNetwork = vnmpReader.getSubstrateNetwork();
    ArrayList<Request> disorderedRequests = vnmpReader.getVirtualNetworkRequests();
    requests = new HashMap<Integer, Request>();
    for(Request request : disorderedRequests) {
      requests.put(request.getId(), request);
    }
  }

  public HashMap<Request, Mapping> readMappings(String mappingsFilename) {
    HashMap<Request, Mapping> mappings = new HashMap<Request, Mapping>();
    Scanner scanner = null;
    try {
      scanner = new Scanner(new File(mappingsFilename));
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
    while(scanner.hasNextLine()) {
      String[] tokens = scanner.nextLine().trim().split(" +"); // mapping header
      Request request = requests.get(Integer.valueOf(tokens[0]));
      Mapping mapping = new Mapping();
      mapping.deactiveResourcesHandling();
      int linksAmmount = Integer.valueOf(tokens[3]);
      Collection<VirtualLink> virtualLinks = request.getVirtualLinks().values();
      // Checagem
      if(linksAmmount != virtualLinks.size()) {
        throw new RuntimeException("Quantidade de enlaces não bate.");
      }
      for(VirtualLink virtualLink : virtualLinks) {
        tokens = scanner.nextLine().trim().split(" +"); // link mapping
        Integer[] physicalHostNodesIds = getUniqueIds(tokens);
        VirtualNode virtualSourceNode = (VirtualNode) virtualLink.getSourceNode();
        VirtualNode virtualDestinyNode = (VirtualNode) virtualLink.getDestinyNode();
        VirtualNode[] sourceAndDestinyNodes = { virtualSourceNode, virtualDestinyNode };
        for(int i = 0; i < 2; i++) {
          VirtualNode virtualNode = sourceAndDestinyNodes[i];
          if(!mapping.isNodeMapped(sourceAndDestinyNodes[i])) {
            PhysicalNode physicalHostNode = substrateNetwork.getHashNodes().get(
              physicalHostNodesIds[i]);
            mapping.addNodeMapping(virtualNode, physicalHostNode);
          }
        }
        ArrayList<PhysicalLink> hostLinks = new ArrayList<PhysicalLink>();
        for(int i = 0; i < tokens.length; i = i + 2) {
          int sourceId, destinyId;
          sourceId = Math.max(Integer.valueOf(tokens[i]), Integer.valueOf(tokens[i+1]));
          destinyId = Math.min(Integer.valueOf(tokens[i]), Integer.valueOf(tokens[i+1]));
          PhysicalLink hostLink = substrateNetwork.getHashLinks().get(
            String.format("%s:%s", sourceId, destinyId));
          hostLinks.add(hostLink);
        }
        mapping.addLinkMapping(virtualLink, hostLinks);
      }
      mappings.put(request, mapping);
    }

    return mappings;
  }

  /**
   *  Retorna os números que têm número de aparições no array ímpar. Útil para
   * descobrir nós físicos de origem e destino do enlace virtual mapeado.
   */
  private static Integer[] getUniqueIds(String[] ids) {
    ArrayList<Integer> aList = new ArrayList<Integer>();
    for(String n : ids) {
      if(aList.contains(Integer.valueOf(n)))
        aList.remove(Integer.valueOf(n));
      else
        aList.add(Integer.valueOf(n));
    }
    if(aList.size() > 2) {
      System.err.println("ERR! Mais de dois índices de nós físicos sem repetição.");
      System.exit(-1);
    }

    return aList.toArray(new Integer[1]);
  }
}
