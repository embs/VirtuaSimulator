package simulator.util;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.uncommons.maths.statistics.DataSet;

public class UtilTest extends TestCase {

  private DataSet data;

  public UtilTest(String testName) {
    super(testName);
    double[] values = { 2.2, 5.0, 150.52 };
    data = new DataSet(values);
  }

  public static Test suite() {
    return new TestSuite(UtilTest.class);
  }

  public void testGetAverage() {
    assertEquals(52.57333333333333, Util.getAverage(data));
  }

  public void getMaximum() {
    assertEquals(150.52, Util.getMaximum(data));
  }

  public void testGetStandardDeviation() {
    assertEquals(69.26818477643415, Util.getStandardDeviation(data));
  }
}
