package simulator.io;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import simulator.network.components.physical.PhysicalNode;

public class OptFIVNMPReaderTest extends TestCase {

  private OptFIVNMPReader reader;

  public OptFIVNMPReaderTest(String testName) {
    super(testName);
    reader = new OptFIVNMPReader("/home/embs/Data/simulator/VNMP_Instances/20/eu_20_0_prob");
  }

  public static Test suite() {
    return new TestSuite(OptFIVNMPReaderTest.class);
  }

  public void testGetSubstrateNetwork() {
    assertEquals(20, reader.getSubstrateNetwork().getAmountNodes());
  }

  public void testGetVirtualNetworkRequests() {
    assertEquals(40, reader.getVirtualNetworkRequests().size());
  }
}
