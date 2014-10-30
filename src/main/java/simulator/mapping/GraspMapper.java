package simulator.mapping;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import org.jgrapht.alg.DijkstraShortestPath;
import org.jgrapht.graph.SimpleWeightedGraph;
import org.jgrapht.graph.DefaultWeightedEdge;

import simulator.network.SubstrateNetwork;
import simulator.network.components.*;
import simulator.network.components.physical.*;
import simulator.network.components.virtual.*;
import simulator.simulation.Request;

public class GraspMapper implements IMapper {
  private final double A = 0.5;
  private final int MIN_RCL_SIZE = 7;
  private final int MAX_SEARCH_TRIES = 20;
  public static MathContext MATH_CONTEXT = new MathContext(10, RoundingMode.HALF_UP);
  private Random random;
  protected boolean allowNodeSharing;

  public GraspMapper() {
    random = new Random(1337);
    allowNodeSharing = false;
  }

  public void allowNodeSharing() {
    allowNodeSharing = true;
  }

  public Mapping map(Request request, SubstrateNetwork substrateNetwork) {
    Mapping mapping = new Mapping();
    ArrayList<VirtualNode> discoveredVirtualNodes = new ArrayList<VirtualNode>();
    HashMap<Integer, VirtualNode> virtualNodes = request.getVirtualNodes();
    VirtualNode firstVirtualNode = virtualNodes.get(random.nextInt(virtualNodes.size()));
    PhysicalNode firstPhysicalNode = findFirstPhysicalNodeFor(firstVirtualNode,
      substrateNetwork);

    if(firstPhysicalNode == null) {
      mapping.clearMappings();
      return null;
    }

    mapping.addNodeMapping(firstVirtualNode, firstPhysicalNode);
    discoveredVirtualNodes.add(firstVirtualNode);

    if(virtualNodes.size() == 1)
      return mapping;

    while(discoveredVirtualNodes.size() > 0) {
      VirtualNode discoveredVirtualNode = discoveredVirtualNodes.get(
        random.nextInt(discoveredVirtualNodes.size()));

      if(mapping.getHostingNodeFor(discoveredVirtualNode) == null)
        System.exit(-1);

      for(Link attachedVirtualLink : discoveredVirtualNode.getAttachedLinks()) {
        VirtualNode attachedNode = (VirtualNode) attachedVirtualLink.getNodeAttachedTo(
          discoveredVirtualNode);
        if(!mapping.isNodeMapped(attachedNode)) {
          if(!mapBranch(discoveredVirtualNode, attachedNode,
             (VirtualLink) attachedVirtualLink, substrateNetwork, mapping)) {
            mapping.clearMappings();
            return null;
          }

          discoveredVirtualNodes.add(attachedNode);
        }
      }

      discoveredVirtualNodes.remove(discoveredVirtualNode);
    }

    // Mapeia enlaces que não foram descobertos
    for(VirtualLink virtualLink : request.getVirtualLinks().values()) {
      if(!mapping.isLinkMapped(virtualLink)) {
        PhysicalNode sourcePhysicalNode = mapping.getHostingNodeFor(
          (VirtualNode) virtualLink.getSourceNode());
        PhysicalNode destinyPhysicalNode = mapping.getHostingNodeFor(
          (VirtualNode) virtualLink.getDestinyNode());
        // Origem e destino podem ser iguais por conta do compartilhamento
        if(!sourcePhysicalNode.equals(destinyPhysicalNode)) {
          HashMap<PhysicalNode, Path> paths = getPathsFromNode(sourcePhysicalNode,
            substrateNetwork);
          Path path = paths.get(destinyPhysicalNode);
          if(path != null && path.canHost(virtualLink)) {
            mapping.addLinkMapping(virtualLink, path.getLinks());
          } else {
            mapping.clearMappings();
            return null;
          }
        }
      }
    }

    return mapping;
  }

  protected PhysicalNode findFirstPhysicalNodeFor(VirtualNode virtualNode,
                                            SubstrateNetwork substrateNetwork) {
    double accumulatedLoad = 0;
    double accumulatedProbability = 0;
    ArrayList<Double> probabilities = new ArrayList<Double>();
    ArrayList<PhysicalNode> capablePhysicalNodes = substrateNetwork.
      getPhysicalNodesWithRemainingCapacityGreaterThan(virtualNode.getCapacity());

    if(capablePhysicalNodes.size() == 0)
      return null;

    Collections.sort(capablePhysicalNodes);

    // if(!ALEATORY_FIRST_NODE) TODO
    //   capablePhysicalNodes = capListNodes(capablePhysicalNodes);

    for(PhysicalNode node : capablePhysicalNodes) {
      double averageLoad = (node.getRemainingCapacity()) / node.getCapacity();
      probabilities.add(averageLoad);
      accumulatedLoad += averageLoad;
    }

    for (int index = 0; index < probabilities.size(); index++) {
      double prob = probabilities.get(index) / accumulatedLoad;
      accumulatedProbability += prob;
      probabilities.set(index, accumulatedProbability);
    }

    int index = 0;
    while(index < probabilities.size() && random.nextDouble() > probabilities.get(index))
      index++;

    return capablePhysicalNodes.get(index);
  }

  protected boolean mapBranch(VirtualNode sourceVirtualNode,
                          VirtualNode destinyVirtualNode, VirtualLink virtualLink,
                          SubstrateNetwork substrateNetwork, Mapping mapping) {
    // Seleciona nós candidatos aptos
    PhysicalNode sourcePhysicalNode = mapping.getHostingNodeFor(sourceVirtualNode);
    HashMap<PhysicalNode, Path> shortestPaths =
      getShortestPathsFromNodeWithConstraints(sourcePhysicalNode, substrateNetwork,
        virtualLink);
    ArrayList<PhysicalNode> candidateNodes = new ArrayList<PhysicalNode>();
    for(PhysicalNode physicalNode : substrateNetwork.getHashNodes().values()) {
      if(physicalNode.canHost(destinyVirtualNode)
         && shortestPaths.get(physicalNode) != null
         && shortestPaths.get(physicalNode).canHost(virtualLink)) {
        if(allowNodeSharing)
          candidateNodes.add(physicalNode);
        else if(!mapping.isNodeInUse(physicalNode))
          candidateNodes.add(physicalNode);
      }
    }
    Collections.sort(candidateNodes);
    candidateNodes = capListNodes(candidateNodes);

    // Seleciona melhor caminho de acordo com a disponibilidade
    int triesCounter = 0;
    Path bestPath = null;
    while(!candidateNodes.isEmpty() && triesCounter < MAX_SEARCH_TRIES) {
      PhysicalNode candidateDestinyPhysicalNode = candidateNodes.get(
        random.nextInt(candidateNodes.size()));
      Path path = shortestPaths.get(candidateDestinyPhysicalNode);
      if(bestPath == null
         || path.getAvailability().compareTo(
          bestPath.getAvailability()) > 0) {
        bestPath = path;
      }
      candidateNodes.remove(candidateDestinyPhysicalNode);
      triesCounter++;
    }

    if(bestPath == null) {
      if(allowNodeSharing && sourcePhysicalNode.canHost(destinyVirtualNode)) {
        mapping.addNodeMapping(destinyVirtualNode, sourcePhysicalNode);

        return true;
      }
    } else { // bestPath != null
      if(allowNodeSharing && sourcePhysicalNode.canHost(destinyVirtualNode)
         && sourcePhysicalNode.getNodeAvailability().compareTo(
          bestPath.getAvailability()) > 0) {
          mapping.addNodeMapping(destinyVirtualNode, sourcePhysicalNode);

          return true;
      } else {
        mapping.addNodeMapping(destinyVirtualNode, bestPath.getDestinyNode());
        mapping.addLinkMapping(virtualLink, bestPath.getLinks());

        return true;
      }
    }

    return false;
  }

  // HashMap<DestinyNode, Path>
  protected HashMap<PhysicalNode, Path> getShortestPathsFromNodeWithConstraints(
             PhysicalNode physicalSourceNode, SubstrateNetwork substrateNetwork,
                                                      VirtualLink virtualLink) {
    HashMap<PhysicalNode, Path> paths = getPathsFromNode(physicalSourceNode,
      substrateNetwork);
    for(Path path : paths.values()) {
      if(!path.getDestinyNode().canHost((VirtualNode) virtualLink.getDestinyNode())
         || !path.canHost(virtualLink))
        paths.remove(path);
    }

    return paths;
  }

  // HashMap<DestinyNode, Path>
  protected HashMap<PhysicalNode, Path> getPathsFromNode(PhysicalNode physicalSourceNode,
                                            SubstrateNetwork substrateNetwork) {
    SimpleWeightedGraph<Node, DefaultWeightedEdge> graph =
      new SimpleWeightedGraph<Node, DefaultWeightedEdge>(DefaultWeightedEdge.class);
    for(PhysicalNode physicalNode : substrateNetwork.getHashNodes().values())
        graph.addVertex(physicalNode);
    for(PhysicalLink physicalLink : substrateNetwork.getHashLinks().values()) {
      DefaultWeightedEdge edge = graph.addEdge(physicalLink.getSourceNode(),
        physicalLink.getDestinyNode());
      graph.setEdgeWeight(edge, physicalLink.getDelay());
    }

    HashMap<PhysicalNode, Path> paths = new HashMap<PhysicalNode, Path>();
    for(PhysicalNode physicalNode : substrateNetwork.getHashNodes().values()) {
      if(!physicalNode.equals(physicalSourceNode)) {
        Path path = new Path(physicalSourceNode, physicalNode);
        List<DefaultWeightedEdge> edgesPath = DijkstraShortestPath.findPathBetween(
          graph, physicalSourceNode, physicalNode);
        if(edgesPath != null) { // FIXME este if é necessário?
          for(DefaultWeightedEdge edge : edgesPath) {
            PhysicalLink physicalLink = substrateNetwork.getHashLinks().get(
              String.format("%s:%s", graph.getEdgeSource(edge).getId(),
                graph.getEdgeTarget(edge).getId()));
            if(physicalLink != null) {
              path.addLink(physicalLink);
            }
          }
        }

        if(!path.isEmpty())
          paths.put(physicalNode, path);
      }
    }

    return paths;
  }

  protected ArrayList<PhysicalNode> capListNodes(ArrayList<PhysicalNode> listNodes) {
    int size = listNodes.size();
    int rclSize = (int)(size * A);
    // Collections.sort(listNodes); FIXME sort de novo?
    if(rclSize >= MIN_RCL_SIZE) {
      listNodes = new ArrayList<PhysicalNode>(listNodes.subList(0, rclSize));
    }

    return listNodes;
  }
}
