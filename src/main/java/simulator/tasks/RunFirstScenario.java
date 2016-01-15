package simulator.tasks;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;

import simulator.io.OptFIVNMPReader;
import simulator.mapping.GraspMapper;
import simulator.simulation.Simulation;

/*
 * Primeiro cenário: apenas simulação de eventos discretos -- sem Software Aging,
 * utilizando a versão padrão do GraspMapper e os VNMPs do OptFI (com tempo de
 * vida de requisições setados de maneira que não haja desalocações).
 */
public class RunFirstScenario {
  public static void main(String[] args) {
    String baseDirPath = "/media/embs/Data/VNMP_Instances/";
    String outDir = "/media/embs/Data/VirtuaSimulationSharingNodesOptFIVNMPs/";
    if(args.length == 1) {
      baseDirPath = args[0];
    } else if(args.length == 2) {
      baseDirPath = args[0];
      outDir = args[1];
    }
    File baseDir = new File(baseDirPath);
    for(File problemsDir : baseDir.listFiles()) {
      for(File problemFile : problemsDir.listFiles()) {
        String filename = problemFile.getAbsolutePath();
        OptFIVNMPReader reader = new OptFIVNMPReader(filename);
        Simulation simulation = new Simulation(outDir + problemFile.getName(),
          reader);
        GraspMapper mapper = new GraspMapper();
        mapper.allowNodeSharing();
        simulation.simulate(mapper);
      }
    }
  }
}

// REFACTOR PHASE!!!
