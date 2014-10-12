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

    return (data.getAggregate() == 0D ? 0D : max);
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

  public static double getError(DataSet data) {
    double err = 0;
    try {
      err = 1.96 * data.getStandardDeviation() / Math.sqrt(data.getSize());
    } catch(EmptyDataSetException e) {
      e.printStackTrace();
    }

    return err;
  }

  public static double getRateError(DataSet data) {
    double err = 0;
    try {
      err = 1.96 * Math.sqrt(data.getArithmeticMean() / data.getSize());
    } catch(EmptyDataSetException e) {
      e.printStackTrace();
    }

    return err;
  }
}
