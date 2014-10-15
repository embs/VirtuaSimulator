package graphicator.tasks;

import graphicator.graphs.DynamicGraph;
import graphicator.io.ViNEYardMappingsReader;

import java.io.File;
import java.util.HashMap;
import java.util.Scanner;

import org.uncommons.maths.statistics.DataSet;

import simulator.mapping.Mapping;
import simulator.simulation.Request;
import simulator.util.Util;

public class GraphicateViNEMetricsOverTime {
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
      "/media/embs/Data/vine-yard-reproduction/2000-requests/r-2000-50-50-20-10-5-25/prob_0/MySimINFOCOM2009.dvine.out");
    ViNEYardMappingsReader mappingsReader = new ViNEYardMappingsReader(
      "/media/embs/Data/vine-yard-reproduction/2000-requests/r-2000-50-50-20-10-5-25/prob_0/sub.txt",
      "/media/embs/Data/vine-yard-reproduction/2000-requests/r-2000-50-50-20-10-5-25/prob_0/requests");
    HashMap<Request, Mapping> mappings = mappingsReader.readMappings(
      "/media/embs/Data/vine-yard-reproduction/2000-requests/r-2000-50-50-20-10-5-25/prob_0/mappings.dvine.out");
    Scanner scanner = new Scanner(file);
    scanner.nextLine(); // ignora primeira linha, que é o cabeçalho
    while(scanner.hasNextLine()) {
      String[] tokens = scanner.nextLine().trim().split(" +");
      totalEvents++;
      if(Integer.valueOf(tokens[3]) == 0) { // requisição chegando
        totalRequests++;
        if(Integer.valueOf(tokens[4]) == 1) { // requisição aceita
          acceptedRequests++;
          double currentAvailability = 0;
          for(Request request : mappings.keySet()) {
            if(request.getId() == Integer.valueOf(tokens[0])) {
              currentAvailability = mappings.get(request).getAvailability().doubleValue();
            }
          }
          averageAvailability += currentAvailability;
        }
      }
      nodesAverageLoad += Double.valueOf(tokens[12]);
      linksAverageLoad += Double.valueOf(tokens[14]);
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
    for(DynamicGraph graph : graphs) {
      System.out.println(graph);
    }
  }
}
