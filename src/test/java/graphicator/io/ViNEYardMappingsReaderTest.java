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
    	"/media/embs/Data/OptFIVNMP_Instances_ViNE_format/100/eu_100_0_prob/sub.txt",
    	"/media/embs/Data/OptFIVNMP_Instances_ViNE_format/100/eu_100_0_prob/requests");
    mappings = reader.readMappings(
    	"/media/embs/Data/OptFIVNMP_Instances_ViNE_format/100/eu_100_0_prob/mappings.out");
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
