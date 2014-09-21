package generator.util;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class RandomizerTest extends TestCase {

  private Randomizer randomizer;

  public RandomizerTest(String testName) {
    super(testName);
    randomizer = Randomizer.getInstance();
  }

  public static Test suite() {
    return new TestSuite(RandomizerTest.class);
  }

  public void testNextInt() {
    int max = 99;
    assertTrue(randomizer.nextInt(max) < max);
    assertTrue(randomizer.nextInt(max) >= 0);
  }

  public void testNextDouble() {
    double randomDouble = randomizer.nextDouble();
    assertTrue(randomDouble >= 0D);
    assertTrue(randomDouble <= 1D);
  }

  public void testNextBoolean() {
    boolean be = randomizer.nextBoolean();
    assertTrue(be || !be);
  }

  public void testNextExponential() {
    int randomExponential = randomizer.nextExponential();
    assertTrue(randomExponential >= 0);
  }

  public void testNextPoisson() {
    int randomPoisson = randomizer.nextPoisson();
    assertTrue(randomPoisson >= 0);
  }
}
