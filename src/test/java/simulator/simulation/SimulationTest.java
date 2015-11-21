package simulator.simulation;

import junit.framework.TestCase;

import java.io.File;
import java.util.HashMap;

import simulator.io.OptFIVNMPReader;
import simulator.mapping.IMapper;
import simulator.mapping.HaterMapper;
import simulator.mapping.Mapping;

public class SimulationTest extends TestCase {

  private String simulationName;
  private Simulation simulation;
  private IMapper mapper;

  public SimulationTest(String testName) {
    super(testName);
    simulationName = "OptFIVNMP_20_0";
    simulation = new Simulation(simulationName,
      new OptFIVNMPReader("src/test/resources/OptFI/VNMPs/eu_20_0_prob"));
    mapper = new HaterMapper();
  }

  protected void tearDown() {
    File outputFile = new File("OptFIVNMP_20_0_simulation.txt");
    if(outputFile.exists()) {
      outputFile.delete();
    }
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
