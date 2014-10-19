package simulator.tasks;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;

import simulator.io.OptFIVNMPReader;
import simulator.mapping.GraspMapper;
import simulator.simulation.Simulation;

/*
 * Executa uma única instância de VNMP do benchmark OptFI.
 */
public class RunOneOptFIVNMP {
  public static void main(String[] args) {
    String filename = "/media/embs/Data/VNMP_Instances/100/eu_100_0_prob";
    OptFIVNMPReader reader = new OptFIVNMPReader(filename);
    Simulation simulation = new Simulation("eu_100_0_prob_after", reader);
    GraspMapper mapper = new GraspMapper();
    mapper.allowNodeSharing();
    simulation.simulate(mapper);
  }
}
