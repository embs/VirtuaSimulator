package graphicator.tasks;

import graphicator.graphs.DynamicGraph;

import java.io.File;
import java.util.Scanner;

import org.uncommons.maths.statistics.DataSet;

import simulator.util.Util;

public class GraphicateVirtuaMetricsOverTime {
  public static void main(String[] args) throws Exception {
    int totalEvents = 0;
    int totalRequests = 0;
    int acceptedRequests = 0;
    double nodesAverageLoad = 0;
    double linksAverageLoad = 0;
    double averageAvailability = 0;
    String[] metrics = { "acceptanceRate", "nodesAverageLoad",
      "linksAverageLoad", "averageAvailability" };
    DynamicGraph[] graphs = new DynamicGraph[metrics.length];
    for(int i = 0; i < graphs.length; i++) {
      graphs[i] = new DynamicGraph("Gráfico para " + metrics[i] + " no tempo", 2);
      graphs[i].setColumnHeader(0, "tempo");
      graphs[i].setColumnHeader(1, metrics[i]);
    }
    File file = new File(
      "vine_yard_instance_simulation.txt");
    Scanner scanner = new Scanner(file);
    while(scanner.hasNextLine()) {
      String[] tokens = scanner.nextLine().split(" ");
      if(tokens.length > 1) { // rejeita última linha, que é do tempo de exec.
        totalEvents++;
        if(Integer.valueOf(tokens[3]) == 0) { // requisição chegando
          totalRequests++;
          if(Integer.valueOf(tokens[4]) == 1) { // requisição aceita
            acceptedRequests++;
            averageAvailability += Double.valueOf(tokens[11]);
          }
        }
        nodesAverageLoad += Double.valueOf(tokens[6]);
        linksAverageLoad += Double.valueOf(tokens[8]);
        graphs[0].addLine("", new String[]{
          String.valueOf(tokens[1]),
          String.valueOf((double) acceptedRequests / totalRequests)
        });
        graphs[1].addLine("", new String[]{
          String.valueOf(tokens[1]),
          String.valueOf((double) nodesAverageLoad / totalEvents)
        });
        graphs[2].addLine("", new String[]{
          String.valueOf(tokens[1]),
          String.valueOf((double) linksAverageLoad / totalEvents)
        });
        graphs[3].addLine("", new String[]{
          String.valueOf(tokens[1]),
          String.valueOf((double) averageAvailability / acceptedRequests)
        });
      }
    }
    for(DynamicGraph graph : graphs) {
      System.out.println(graph);
    }
  }
}
