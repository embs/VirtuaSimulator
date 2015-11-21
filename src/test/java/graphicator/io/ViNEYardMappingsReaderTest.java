package graphicator.io;

import java.util.HashMap;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

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

  public static Test suite() {
    return new TestSuite(ViNEYardMappingsReaderTest.class);
  }

  public void testMappings() {
  	for(Mapping mapping : mappings.values()) {
  		assertNotNull(mapping);
  	}
  }
}
