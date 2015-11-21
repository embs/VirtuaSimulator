package simulator.mapping;

import junit.framework.TestCase;

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

  public void testAddNodeMapping() {
    assertNull(mapper.map(poorRequest, substrateNetwork));
  }
}
