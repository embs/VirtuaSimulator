package simulator.util;

import org.uncommons.maths.statistics.DataSet;
import org.uncommons.maths.statistics.EmptyDataSetException;

public class Util {
  public static double getAverage(DataSet data) {
    double avg = 0;
    try {
      avg = data.getArithmeticMean();
    } catch(EmptyDataSetException e) {
      e.printStackTrace();
    }

    return avg;
  }

  public static double getMaximum(DataSet data) {
    double max = 0;
    try {
      max = data.getMaximum();
    } catch(EmptyDataSetException e) {
      e.printStackTrace();
    }

    return max;
  }

  public static double getStandardDeviation(DataSet data) {
    double sd = 0;
    try {
      sd = data.getStandardDeviation();
    } catch(EmptyDataSetException e) {
      e.printStackTrace();
    }

    return sd;
  }
}
