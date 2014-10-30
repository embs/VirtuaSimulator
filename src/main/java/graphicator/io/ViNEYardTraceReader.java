package graphicator.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.concurrent.TimeUnit;
import java.util.HashMap;
import java.util.Scanner;

import org.uncommons.maths.statistics.DataSet;

import simulator.mapping.Mapping;
import simulator.simulation.Request;
import simulator.util.Clock;
import simulator.util.Util;

public class ViNEYardTraceReader {
  private HashMap<String, Object> metrics;
  private HashMap<Integer, Integer> eventsTimes;
  private HashMap<Integer, Integer> eventsReleaseTimes;

  public ViNEYardTraceReader() {
    metrics = new HashMap<String, Object>();
    eventsTimes = new HashMap<Integer, Integer>();
    eventsReleaseTimes = new HashMap<Integer, Integer>();
  }

  public HashMap<String, Object> readTrace(String traceFilename,
                                                 String executionTimeFilename) {
    Scanner scanner = null;
    try {
      scanner = new Scanner(new File(traceFilename));
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }

    // Workaround para o problema de segmentation fault dos VNMPs com 20 nós no dvine
    if(!scanner.hasNextLine()) {
      metrics.put("acceptedRequests", 0);
      metrics.put("acceptanceRate", 0.0);
      metrics.put("averageNodesLoad", 0.0);
      metrics.put("averageLinksLoad", 0.0);
      metrics.put("nodesLoadStandardDeviation", 0.0);
      metrics.put("linksLoadStandardDeviation", 0.0);
      readExecutionTimeFile(executionTimeFilename);

      return metrics;
    }

    scanner.nextLine(); // desconsidera linha de cabeçalho
    int requestsTotal = 0;
    int acceptedRequestsTotal = 0;
    double avgNodesLoad = -1;
    double avgLinksLoad = -1;
    double nodesLoadStdDev = -1;
    double linksLoadStdDev = -1;
    while(scanner.hasNextLine()) {
      String[] tokens = scanner.nextLine().trim().split(" +");
      if(tokens[3].equals("0")) { // requisição chegando
        requestsTotal++;
        if(tokens[4].equals("1")) { // requisição aceita
          int eventIndex = Integer.valueOf(tokens[0]);
          int eventTime = Integer.valueOf(tokens[1]);
          int eventDuration = Integer.valueOf(tokens[2]);
          eventsTimes.put(eventIndex, eventTime);
          eventsReleaseTimes.put(eventIndex, eventTime + eventDuration);
          acceptedRequestsTotal++;
        }
      }
      double requestAvgNodeLoad = Double.valueOf(tokens[12]);
      double requestAvgLinkLoad = Double.valueOf(tokens[14]);
      double requestNodeLoadStdDev = Double.valueOf(tokens[15]);
      double requestLinksLoadStdDev = Double.valueOf(tokens[16]);
      if(requestAvgNodeLoad > avgNodesLoad) {
        avgNodesLoad = requestAvgNodeLoad;
        nodesLoadStdDev = requestNodeLoadStdDev;
      }
      if(requestAvgLinkLoad > avgLinksLoad) {
        avgLinksLoad = requestAvgLinkLoad;
        linksLoadStdDev = requestLinksLoadStdDev;
      }
    }
    metrics.put("acceptedRequests", acceptedRequestsTotal);
    metrics.put("acceptanceRate", (double) acceptedRequestsTotal / requestsTotal);
    metrics.put("averageNodesLoad", avgNodesLoad);
    metrics.put("averageLinksLoad", avgLinksLoad);
    metrics.put("nodesLoadStandardDeviation", nodesLoadStdDev);
    metrics.put("linksLoadStandardDeviation", linksLoadStdDev);
    readExecutionTimeFile(executionTimeFilename);

    return metrics;
  }

  public Object get(String metricName) {
    return metrics.get(metricName);
  }

  public void readMappings(String substrateNetworkFilename,
                       String requestsFolderFilename, String mappingsFilename) {
    ViNEYardMappingsReader reader = new ViNEYardMappingsReader(substrateNetworkFilename,
      requestsFolderFilename);
    HashMap<Request, Mapping> mappings = reader.readMappings(mappingsFilename);
    DataSet availabilityData = new DataSet();
    for(Request request : mappings.keySet()) {
      Mapping mapping = mappings.get(request);
      Clock.instance().setTime(eventsTimes.get(request.getId()));
      int currentTime = Clock.instance().time();
      mapping.updateNodesStartAndReleaseTimes(currentTime, eventsReleaseTimes.get(request.getId()));
      availabilityData.addValue(mapping.getAvailability().doubleValue());
    }

    if(availabilityData.getSize() == 0) {
      metrics.put("averageAvailability", 0.0);

      return;
    }

    metrics.put("averageAvailability", Util.getAverage(availabilityData));
  }

  private void readExecutionTimeFile(String filename) {
    Scanner scanner = null;
    try {
      scanner = new Scanner(new File(filename));
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
    String firstLine = scanner.nextLine(); // primeira linha, em branco
    // Workaround para o problema com o glpsol: not found
    String timeLine = firstLine;
    if(firstLine.startsWith("sh")) {
      while(timeLine.startsWith("sh"))
        timeLine = scanner.nextLine();
      scanner.nextLine();
    }

    timeLine = scanner.nextLine().split("\t")[1];
    long min = Long.valueOf(timeLine.split("m")[0]);
    double sec = Double.valueOf(timeLine.split("m")[1].split("s")[0]);
    min = TimeUnit.SECONDS.convert(min, TimeUnit.MINUTES);
    scanner.close();
    metrics.put("executionTime", min + sec);
  }
}
