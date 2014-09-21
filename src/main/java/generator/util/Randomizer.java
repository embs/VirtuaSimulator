package generator.util;

import java.util.Random;

import org.uncommons.maths.random.ExponentialGenerator;
import org.uncommons.maths.random.PoissonGenerator;

public class Randomizer {
  private static Randomizer instance;
  private Random randomGenerator;
  private ExponentialGenerator exponentialGenerator;
  private PoissonGenerator poissonGenerator;

  private Randomizer() {
    randomGenerator = new Random(1337);
    exponentialGenerator = new ExponentialGenerator(0.001, randomGenerator);
    poissonGenerator = new PoissonGenerator(4, randomGenerator);
  }

  public static Randomizer getInstance() {
    if(instance == null) {
      instance = new Randomizer();
    }

    return instance;
  }

  public int nextInt(int n) {
    return randomGenerator.nextInt(n);
  }

  public double nextDouble() {
    return randomGenerator.nextDouble();
  }

  public boolean nextBoolean() {
    return randomGenerator.nextBoolean();
  }

  public int nextExponential() {
    return exponentialGenerator.nextValue().intValue();
  }

  public int nextPoisson() {
    return poissonGenerator.nextValue();
  }
}
