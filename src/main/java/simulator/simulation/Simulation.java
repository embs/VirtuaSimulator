package simulator.simulation;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.HashMap;

import simulator.io.IVNMPReader;
import simulator.mapping.IMapper;
import simulator.mapping.Mapping;
import simulator.network.SubstrateNetwork;

public class Simulation {
  private String name;
  private PriorityQueue<RequestEvent> requestEvents;
  private SubstrateNetwork substrateNetwork;
  private HashMap<Request, Mapping> mappings;

  public Simulation(String simulationName, IVNMPReader reader) {
    name = simulationName;
    requestEvents = new PriorityQueue<RequestEvent>();
    mappings = new HashMap<Request, Mapping>();
    substrateNetwork = reader.getSubstrateNetwork();
    ArrayList<Request> virtualRequests = reader.getVirtualNetworkRequests();
    for(Request request : virtualRequests) {
      requestEvents.add(new RequestEvent(request, request.getCreationTime(),
                                         RequestEvent.ARRIVAL_EVENT));
    }
  }

  public void simulate(IMapper mapper) {
    PrintWriter writer = null;
    try {
      writer = new PrintWriter(name + "_simulation.txt");
    } catch(FileNotFoundException e) {
      e.printStackTrace();
    }
    while(!requestEvents.isEmpty()) {
      RequestEvent currentRequestEvent = requestEvents.poll();
      Request currentRequest = currentRequestEvent.getRequest();
      if(currentRequestEvent.isArrivalEvent()) { // arrival event
        Mapping requestMapping = mapper.map(currentRequest, substrateNetwork);
        if(requestMapping != null) {
          mappings.put(currentRequest, requestMapping);
          // cria evento de saída
          requestEvents.add(new RequestEvent(currentRequest,
                                             currentRequest.getDepartureTime(),
                                             RequestEvent.DEPARTURE_EVENT));
        }
      } else { // departure event
        mappings.get(currentRequest).clearMappings();
      }

      writer.println(String.format("%s %s %s %s %s %s %s %s",
        currentRequestEvent,
        (mappings.containsKey(currentRequest) ? "1" : "0"),
        substrateNetwork.getMaximumNodesLoad(),
        substrateNetwork.getAverageNodesLoad(),
        substrateNetwork.getMaximumLinksBandwidthLoad(),
        substrateNetwork.getAverageLinksBandwidthLoad(),
        substrateNetwork.getNodesLoadStandardDeviation(),
        substrateNetwork.getLinksBandwidthLoadStandardDeviation()));
    }
    writer.close();
  }

  public HashMap<Request, Mapping> getMappings() {
    return this.mappings;
  }

  public String getName() {
    return name;
  }
}
