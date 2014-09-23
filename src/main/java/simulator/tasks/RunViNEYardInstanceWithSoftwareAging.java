package simulator.tasks;

import java.util.HashMap;

import simulator.io.ViNEYardVNMPReader;
import simulator.mapping.GraspMapper;
import simulator.simulation.AgedSimulation;

public class RunViNEYardInstanceWithSoftwareAging {
  public static void main(String[] args) {
    ViNEYardVNMPReader reader = new ViNEYardVNMPReader(
      "/home/embs/Code/vine-yard/sub-50.txt",
      "/home/embs/Code/vine-yard/r-2000-50-50-20-10-5-25");
    AgedSimulation simulation = new AgedSimulation("aged_vine_yard_instance", reader);
    GraspMapper mapper = new GraspMapper();
    simulation.simulate(mapper);
  }
}
