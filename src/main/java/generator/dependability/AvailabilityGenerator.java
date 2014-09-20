package generator.dependability;

import java.math.BigDecimal;
import java.util.Random;

import static simulator.util.Config.MATH_CONTEXT;

public class AvailabilityGenerator {

  private static AvailabilityGenerator instance;

  private Random randomGenerator;

  private final long SEED = 165;
  private final int CPU_FAILURE_RATE = 2500000;
  private final int HD_FAILURE_RATE = 200000;
  private final int MEMORY_FAILURE_RATE = 480000;
  private final int CPU_MTTR = 1;
  private final int HD_MTTR = 1;
  private final int MEMORY_MTTR = 1;

  public final static int HYPERVISOR_FAILURE_RATE = 2880;
  public final static int HYPERVISOR_MTTR = 2;
  public final static int ROUTER_FAILURE_RATE = 320000;
  public final static int ROUTER_MTTR = 1;
  public final static int LINK_FAILURE_RATE = 19996;
  public final static int LINK_MTTR = 12;

  private AvailabilityGenerator() {
    randomGenerator = new Random(SEED);
  }

  public static AvailabilityGenerator getInstance() {
    if(instance == null)
      instance = new AvailabilityGenerator();
    return instance;
  }

  public BigDecimal generateMachineAvailability() {
    BigDecimal cpuAvailability, hdAvailability, memoryAvailability, routerAvailability;
    cpuAvailability = generateComponentAvailability(CPU_FAILURE_RATE, CPU_MTTR);
    hdAvailability = generateComponentAvailability(HD_FAILURE_RATE, HD_MTTR);
    memoryAvailability = generateComponentAvailability(MEMORY_FAILURE_RATE, MEMORY_MTTR);
    routerAvailability = generateComponentAvailability(ROUTER_FAILURE_RATE, ROUTER_MTTR);

    return cpuAvailability.multiply(hdAvailability, MATH_CONTEXT).
        multiply(memoryAvailability, MATH_CONTEXT).
        multiply(routerAvailability, MATH_CONTEXT);
  }

  public BigDecimal generateComponentAvailability(int failureRate, int mttr) {
    int uniformRandom = randomGenerator.nextInt(66);
    double mttf = ((double)(uniformRandom+35)/100) * failureRate;

    return new BigDecimal(mttf / (mttf + mttr), MATH_CONTEXT);
  }
}
