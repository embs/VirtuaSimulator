package graphicator.io;

import java.util.HashMap;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class VirtuaSimulatorTraceReader {
  private HashMap<String, Object> metrics;

  public VirtuaSimulatorTraceReader() {
    metrics = new HashMap<String, Object>();
  }

  public HashMap<String, Object> readTrace(String filename) {
    Scanner scanner = null;
    try {
      scanner = new Scanner(new File(filename));
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
    int requestsTotal = 0;
    int acceptedRequestsTotal = 0;
    double avgNodesLoad = -1;
    double avgLinksLoad = -1;
    double nodesLoadStdDev = -1;
    double linksLoadStdDev = -1;
    double availabilityTotal = 0;
    while(scanner.hasNextLine()) {
      String[] tokens = scanner.nextLine().trim().split(" +");
      if(tokens.length == 1) { // linha do tempo de execução
        metrics.put("executionTime", Double.valueOf(tokens[0]) / 1000);
      } else { // linha de informações sobre aceitação / rejeição de requisição
        if(tokens[3].equals("0")) { // requisição chegando
          requestsTotal++;
          if(tokens[4].equals("1")) { // requisição aceita
            acceptedRequestsTotal++;
          }
        }
        double requestAvgNodeLoad = Double.valueOf(tokens[6]);
        double requestAvgLinkLoad = Double.valueOf(tokens[8]);
        double requestNodeLoadStdDev = Double.valueOf(tokens[9]);
        double requestLinksLoadStdDev = Double.valueOf(tokens[10]);
        availabilityTotal += Double.valueOf(tokens[11]);
        if(requestAvgNodeLoad > avgNodesLoad) {
          avgNodesLoad = requestAvgNodeLoad;
          nodesLoadStdDev = requestNodeLoadStdDev;
        }
        if(requestAvgLinkLoad > avgLinksLoad) {
          avgLinksLoad = requestAvgLinkLoad;
          linksLoadStdDev = requestLinksLoadStdDev;
        }
      }
    }
    metrics.put("acceptedRequests", acceptedRequestsTotal);
    metrics.put("acceptanceRate", (double) acceptedRequestsTotal / requestsTotal);
    metrics.put("averageNodesLoad", avgNodesLoad);
    metrics.put("averageLinksLoad", avgLinksLoad);
    metrics.put("nodesLoadStandardDeviation", nodesLoadStdDev);
    metrics.put("linksLoadStandardDeviation", linksLoadStdDev);
    metrics.put("averageAvailability", availabilityTotal / acceptedRequestsTotal);

    return metrics;
  }

  public Object get(String metricName) {
    return metrics.get(metricName);
  }
}
