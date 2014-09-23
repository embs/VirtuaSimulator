package simulator.simulation;

import simulator.io.IVNMPReader;

public class SimpleSimulation extends Simulation {
  public SimpleSimulation(String simulationName, IVNMPReader reader) {
    super(simulationName, reader);
  }

  protected void updatePhysicalNodesAge(RequestEvent requestEvent) {}
}
