package simulator.mapping;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.util.HashMap;

import simulator.network.SubstrateNetwork;
import simulator.simulation.Request;

public class HaterMapperTest extends TestCase {

  private IMapper mapper;
  private SubstrateNetwork substrateNetwork;
  private Request poorRequest;

  public HaterMapperTest(String testName) {
    super(testName);
    mapper = new HaterMapper();
    substrateNetwork = new SubstrateNetwork();
    poorRequest = new Request(0, 0, 0);
  }

  public static Test suite() {
    return new TestSuite(HaterMapperTest.class);
  }

  public void testAddNodeMapping() {
    assertNull(mapper.map(poorRequest, substrateNetwork));
  }
}
