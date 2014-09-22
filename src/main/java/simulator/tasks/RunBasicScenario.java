package simulator.tasks;

import java.util.HashMap;

import simulator.io.VirtuaVNMPReader;
import simulator.mapping.GraspMapper;
import simulator.simulation.Simulation;

/*
 * Primeiro cenário: apenas simulação de eventos discretos -- sem Software Aging,
 * utilizando a versão padrão do GraspMapper.
 */
public class RunBasicScenario {
  public static void main(String[] args) {
    VirtuaVNMPReader reader = new VirtuaVNMPReader(
      "/media/embs/Data/VirtuaVNMPs/vVNMP1.txt");
    Simulation simulation = new Simulation("vVNMP1", reader);
    GraspMapper mapper = new GraspMapper();
    simulation.simulate(mapper);
  }
}
