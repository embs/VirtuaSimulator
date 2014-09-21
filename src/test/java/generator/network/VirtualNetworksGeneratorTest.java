package generator.network;

import java.util.ArrayList;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import simulator.simulation.Request;

public class VirtualNetworksGeneratorTest extends TestCase {

  private VirtualNetworksGenerator virtualNetworksGenerator;
  private int numberOfNetworks;
  private ArrayList<Request> generatedRequests;

  public VirtualNetworksGeneratorTest(String testName) {
    super(testName);
    virtualNetworksGenerator = new VirtualNetworksGenerator();
    numberOfNetworks = 30;
    generatedRequests = virtualNetworksGenerator.generateVirtualNetworks(
      numberOfNetworks);
  }

  public static Test suite() {
    return new TestSuite(VirtualNetworksGeneratorTest.class);
  }

  public void testGeneratedSubstrateNetworkNumberOfNodes() {
    assertEquals(numberOfNetworks, generatedRequests.size());
  }
}
