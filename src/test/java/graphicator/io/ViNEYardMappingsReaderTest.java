package graphicator.io;

import java.util.HashMap;

import junit.framework.TestCase;

import simulator.mapping.Mapping;
import simulator.simulation.Request;

public class ViNEYardMappingsReaderTest extends TestCase {

  private ViNEYardMappingsReader reader;
  private HashMap<Request, Mapping> mappings;

  public ViNEYardMappingsReaderTest(String testName) {
    super(testName);
    reader = new ViNEYardMappingsReader(
    	"src/test/resources/ViNEYard/substrate/sub-100.txt",
    	"src/test/resources/ViNEYard/requests/40");
    mappings = reader.readMappings(
    	"src/test/resources/ViNEYard/mappings/mappings.out");
  }

  public void testMappings() {
  	for(Mapping mapping : mappings.values()) {
  		assertNotNull(mapping);
  	}
  }
}
