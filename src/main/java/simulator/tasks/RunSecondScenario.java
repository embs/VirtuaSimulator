package simulator.tasks;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;

import simulator.io.VirtuaVNMPReader;
import simulator.mapping.GraspMapper;
import simulator.simulation.AgedSimulation;

/*
 *  Segundo cenário: levando em conta desalocações no tempo e software aging.
 * GraspMapper não realiza rejuvenescimento de software.
 */
public class RunSecondScenario {
  public static void main(String[] args) {
    File baseDir = new File("/media/embs/Data/VirtuaVNMPs/");
    String outDir = "/media/embs/Data/VirtuaSimulationVirtuaVNMPsWithSA/";
    for(File problemsDir : baseDir.listFiles()) {
      for(File problemFile : problemsDir.listFiles()) {
        String filename = problemFile.getAbsolutePath();
        VirtuaVNMPReader reader = new VirtuaVNMPReader(filename);
        AgedSimulation simulation = new AgedSimulation(outDir + problemFile.getName().split(".txt")[0],
          reader);
        GraspMapper mapper = new GraspMapper();
        // mapper.allowNodeSharing();
        simulation.simulate(mapper);
      }
    }
  }
}
