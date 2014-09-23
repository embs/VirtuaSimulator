package simulator.simulation;

import simulator.io.IVNMPReader;
import simulator.mapping.Mapping;
import simulator.network.components.physical.PhysicalNode;
import simulator.network.components.virtual.VirtualNode;

public class AgedSimulation extends Simulation {
  public AgedSimulation(String simulationName, IVNMPReader reader) {
    super(simulationName, reader);
  }

  protected void updatePhysicalNodesAge(RequestEvent requestEvent) {
    Request request = requestEvent.getRequest();
    if(requestEvent.getType() == RequestEvent.ARRIVAL_EVENT
       && mappings.containsKey(request)) {
      Mapping mapping = mappings.get(request);
      for(VirtualNode virtualNode : request.getVirtualNodes().values()) {
        PhysicalNode hostingNode = mapping.getHostingNodeFor(virtualNode);
        if(hostingNode.getStartTime() == 0) {
          hostingNode.setStartTime(request.getCreationTime());
        }
        if(hostingNode.getReleaseTime() < request.getDepartureTime()) {
          hostingNode.setReleaseTime(request.getDepartureTime());
        }
      }
    }
  }
}
