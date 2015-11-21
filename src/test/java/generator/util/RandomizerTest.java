package generator.util;

import junit.framework.TestCase;

public class RandomizerTest extends TestCase {

  private Randomizer randomizer;

  public RandomizerTest(String testName) {
    super(testName);
  }

  public void setUp() {
    randomizer = Randomizer.getInstance();
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
