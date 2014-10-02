package simulator.simulation;

import simulator.io.IVNMPReader;
import simulator.mapping.Mapping;

public class SimpleSimulation extends Simulation {
  public SimpleSimulation(String simulationName, IVNMPReader reader) {
    super(simulationName, reader);
  }

  protected void updatePhysicalNodesAge(RequestEvent requestEvent) {}

  protected String printMappingAvailability(RequestEvent requestEvent) {
    Request request = requestEvent.getRequest();
    Mapping mapping = mappings.get(request);

    return mapping.getAvailability(false, 0).toString();
  }
}
