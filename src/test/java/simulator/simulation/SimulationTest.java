package simulator.simulation;

import junit.framework.TestCase;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;

import simulator.io.OptFIVNMPReader;
import simulator.mapping.IMapper;
import simulator.mapping.HaterMapper;
import simulator.mapping.Mapping;

public class SimulationTest extends TestCase {

  private String simulationName;
  private Simulation simulation;
  private IMapper mapper;
  private final String OUTPUT_FILE_NAME = "OptFIVNMP_20_0_simulation.txt";

  public void setUp() {
    simulationName = "OptFIVNMP_20_0";
    simulation = new Simulation(simulationName,
      new OptFIVNMPReader("src/test/resources/OptFI/VNMPs/eu_20_0_prob"));
    mapper = new HaterMapper();
  }

  protected void tearDown() {
    File outputFile = new File(OUTPUT_FILE_NAME);
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

  public void testOutputFileFormat() throws Exception {
    simulation.simulate(mapper);
    File outputFile = new File(OUTPUT_FILE_NAME);
    BufferedReader reader = new BufferedReader(new FileReader(outputFile));
    ArrayList<String> lines = new ArrayList<String>();
    String line;

    while((line = reader.readLine()) != null) {
      lines.add(line);
    }

    assertEquals("There should be one line for each virtual request.",
      40, lines.size() - 1);
    assertTrue("Last line should hold the time information.",
      Integer.valueOf(lines.get(40)) > 0);
    for(int i = 0; i < lines.size() - 1; i++) {
      assertEquals("Each virtual request line should be composed by 13 tokens.",
        13, lines.get(i).split(" ").length);
    }
  }
}
