package simulator.simulation;

import java.util.PriorityQueue;
import java.util.ArrayList;
import java.util.HashMap;

import simulator.io.IVNMPReader;
import simulator.io.OptFIVNMPReader;
import simulator.network.SubstrateNetwork;
import simulator.network.components.physical.*;
import simulator.network.components.virtual.*;

public class Simulation {
  private PriorityQueue<RequestEvent> requestEvents;
  private SubstrateNetwork substrateNetwork;
  private HashMap<Request, Mapping> mappings;

  public Simulation(String filename) {
    requestEvents = new PriorityQueue<RequestEvent>();
    mappings = new HashMap<Request, Mapping>();
    IVNMPReader reader = new OptFIVNMPReader(filename);
    substrateNetwork = reader.getSubstrateNetwork();
    ArrayList<Request> virtualRequests = reader.getVirtualNetworkRequests();
    for(Request request : virtualRequests) {
      requestEvents.add(new RequestEvent(request, request.getCreationTime(),
                                         RequestEvent.ARRIVAL_EVENT));
    }
  }

  public void simulate(IMapper mapper) {
    while(!requestEvents.isEmpty()) {
      RequestEvent currentRequestEvent = requestEvents.poll();
      Request currentRequest = currentRequestEvent.getRequest();
      if(currentRequestEvent.isArrivalEvent()) { // arrival event
        Mapping requestMapping = mapper.map(substrateNetwork, currentRequest);
        mappings.add(currentRequest, requestMapping);
        if(requestMapping != null) {
          // cria evento de saída
          requestEvents.add(new RequestEvent(currentRequest,
                                             currentRequest.getDepartureTime()),
                                             RequestEvent.DEPARTURE_EVENT);
        }
      } else { // departure event
        mappings.get(currentRequest).clearMappings();
      }
    }
  }
}
