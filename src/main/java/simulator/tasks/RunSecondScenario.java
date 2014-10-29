package simulator.tasks;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;

import simulator.io.VirtuaVNMPReader;
import simulator.mapping.GraspMapper;
import simulator.simulation.Simulation;

/*
 *  Segundo cenário: levando em conta desalocações no tempo e software aging.
 * GraspMapper não realiza rejuvenescimento de software.
 */
public class RunSecondScenario {
  public static void main(String[] args) {
    File baseDir = new File("/media/embs/Data/VirtuaVNMPs/");
    String outDir = "/media/embs/Data/VirtuaSimulationVirtuaVNMPs/";
    for(File problemFile : baseDir.listFiles()) {
      String filename = problemFile.getAbsolutePath();
      VirtuaVNMPReader reader = new VirtuaVNMPReader(filename);
      Simulation simulation = new Simulation(outDir + problemFile.getName(),
        reader);
      GraspMapper mapper = new GraspMapper();
      // mapper.allowNodeSharing();
      simulation.simulate(mapper);
    }
  }
}
