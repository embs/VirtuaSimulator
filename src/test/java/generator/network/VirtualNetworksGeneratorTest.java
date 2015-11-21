package generator.network;

import java.util.ArrayList;

import junit.framework.TestCase;

import simulator.simulation.Request;

public class VirtualNetworksGeneratorTest extends TestCase {

  private VirtualNetworksGenerator virtualNetworksGenerator;
  private int numberOfNetworks;
  private ArrayList<Request> generatedRequests;

  public void setUp() {
    virtualNetworksGenerator = new VirtualNetworksGenerator();
    numberOfNetworks = 30;
    generatedRequests = virtualNetworksGenerator.generateVirtualNetworks(
      numberOfNetworks);
  }

  public void testGeneratedSubstrateNetworkNumberOfNodes() {
    assertEquals(numberOfNetworks, generatedRequests.size());
  }
}
