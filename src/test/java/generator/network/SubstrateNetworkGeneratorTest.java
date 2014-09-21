package generator.network;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import simulator.network.SubstrateNetwork;

public class SubstrateNetworkGeneratorTest extends TestCase {

  private SubstrateNetworkGenerator substrateNetworkGenerator;
  private int numberOfNodes;
  private SubstrateNetwork generatedSubstrateNetwork;

  public SubstrateNetworkGeneratorTest(String testName) {
    super(testName);
    substrateNetworkGenerator = new SubstrateNetworkGenerator();
    numberOfNodes = 50;
    generatedSubstrateNetwork = substrateNetworkGenerator.generate(numberOfNodes);
  }

  public static Test suite() {
    return new TestSuite(SubstrateNetworkGeneratorTest.class);
  }

  public void testGeneratedSubstrateNetworkNumberOfNodes() {
    assertEquals(numberOfNodes, generatedSubstrateNetwork.getAmountNodes());
  }

  public void testGeneratedSubstrateNetworkNumberOfLinks() {
    assertFalse(generatedSubstrateNetwork.getAmountLinks() == 0);
  }
}
