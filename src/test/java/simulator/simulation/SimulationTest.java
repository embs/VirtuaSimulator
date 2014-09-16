package simulator.simulation;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.util.HashMap;

import simulator.io.OptFIVNMPReader;
import simulator.mapping.IMapper;
import simulator.mapping.HaterMapper;
import simulator.mapping.Mapping;

public class SimulationTest extends TestCase {

  private Simulation simulation;
  private IMapper mapper;

  public SimulationTest(String testName) {
    super(testName);
    simulation = new Simulation(
      new OptFIVNMPReader("/media/embs/Data/VNMP_Instances/20/eu_20_0_prob"));
    mapper = new HaterMapper();
  }

  public static Test suite() {
    return new TestSuite(SimulationTest.class);
  }

  public void testGetMappings() {
    simulation.simulate(mapper);
    HashMap<Request, Mapping> mappings = simulation.getMappings();
    assertEquals(40, mappings.size());
  }
}
