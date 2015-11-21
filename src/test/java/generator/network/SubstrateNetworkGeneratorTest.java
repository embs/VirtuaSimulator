package generator.network;

import junit.framework.TestCase;

import simulator.network.SubstrateNetwork;

public class SubstrateNetworkGeneratorTest extends TestCase {

  private SubstrateNetworkGenerator substrateNetworkGenerator;
  private int numberOfNodes;
  private SubstrateNetwork generatedSubstrateNetwork;

  public SubstrateNetworkGeneratorTest(String testName) {
    super(testName);
  }

  public void setUp() {
    substrateNetworkGenerator = new SubstrateNetworkGenerator();
    numberOfNodes = 50;
    generatedSubstrateNetwork = substrateNetworkGenerator.generate(numberOfNodes);
  }

  public void testGeneratedSubstrateNetworkNumberOfNodes() {
    assertEquals(numberOfNodes, generatedSubstrateNetwork.getAmountNodes());
  }

  public void testGeneratedSubstrateNetworkNumberOfLinks() {
    assertFalse(generatedSubstrateNetwork.getAmountLinks() == 0);
  }
}
