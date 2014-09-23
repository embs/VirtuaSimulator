package simulator.simulation;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.util.HashMap;

import simulator.io.OptFIVNMPReader;
import simulator.mapping.IMapper;
import simulator.mapping.HaterMapper;
import simulator.mapping.Mapping;

public class AgedSimulationTest extends TestCase {

  private String simulationName;
  private Simulation simulation;
  private IMapper mapper;

  public AgedSimulationTest(String testName) {
    super(testName);
    simulationName = "OptFIVNMP_20_0";
    simulation = new AgedSimulation(simulationName,
      new OptFIVNMPReader("/media/embs/Data/VNMP_Instances/20/eu_20_0_prob"));
    mapper = new HaterMapper();
  }

  public static Test suite() {
    return new TestSuite(AgedSimulationTest.class);
  }

  public void testGetMappings() {
    simulation.simulate(mapper);
    HashMap<Request, Mapping> mappings = simulation.getMappings();
    assertEquals(0, mappings.size());
  }

  public void testGetName() {
    assertEquals(simulationName, simulation.getName());
  }
}
