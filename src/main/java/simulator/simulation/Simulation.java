package simulator.simulation;


import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.HashMap;

import simulator.io.IVNMPReader;
import simulator.io.Tracer;
import simulator.mapping.IMapper;
import simulator.mapping.Mapping;
import simulator.network.SubstrateNetwork;

public class Simulation {
  private String name;
  private PriorityQueue<RequestEvent> requestEvents;
  private SubstrateNetwork substrateNetwork;
  private HashMap<Request, Mapping> mappings;
  private final int MAX_ALLOCATION_TRIES = 8;

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
    Tracer tracer = new Tracer(name + "_simulation.txt");
    long startTime = System.currentTimeMillis();

    while(!requestEvents.isEmpty()) {
      RequestEvent currentRequestEvent = requestEvents.poll();
      Request currentRequest = currentRequestEvent.getRequest();
      if(currentRequestEvent.isArrivalEvent()) { // arrival event
        int triesCounter = 0;
        while(!mappings.containsKey(currentRequest)
              && triesCounter < MAX_ALLOCATION_TRIES) {
          Mapping requestMapping = mapper.map(currentRequest, substrateNetwork);
          if(requestMapping != null) {
            mappings.put(currentRequest, requestMapping);
            // cria evento de saída
            requestEvents.add(new RequestEvent(currentRequest,
                                               currentRequest.getDepartureTime(),
                                               RequestEvent.DEPARTURE_EVENT));
          }
          triesCounter++;
        }
      } else { // departure event
        mappings.get(currentRequest).clearMappings();
      }
      tracer.trace(substrateNetwork, mappings, currentRequestEvent);
    }
    tracer.println(System.currentTimeMillis() - startTime);
    tracer.close();
  }

  public HashMap<Request, Mapping> getMappings() {
    return this.mappings;
  }

  public String getName() {
    return name;
  }
}
