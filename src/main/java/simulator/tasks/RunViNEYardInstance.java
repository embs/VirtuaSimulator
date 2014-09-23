package simulator.tasks;

import java.util.HashMap;

import simulator.io.ViNEYardVNMPReader;
import simulator.mapping.GraspMapper;
import simulator.simulation.Simulation;

public class RunViNEYardInstance {
  public static void main(String[] args) {
    ViNEYardVNMPReader reader = new ViNEYardVNMPReader(
      "/home/embs/Code/vine-yard/sub-50.txt",
      "/home/embs/Code/vine-yard/r-2000-50-50-20-10-5-25");
    Simulation simulation = new Simulation("vine_yard_instance", reader);
    GraspMapper mapper = new GraspMapper();
    simulation.simulate(mapper);
  }
}
