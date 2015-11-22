package simulator.io;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.HashMap;

import simulator.mapping.Mapping;
import simulator.network.SubstrateNetwork;
import simulator.simulation.Request;
import simulator.simulation.RequestEvent;

public class Tracer {
  private String outputFileName;
  private PrintWriter writer;

  public Tracer(String outputFileName) {
    this.outputFileName = outputFileName;
    try {
      writer = new PrintWriter(outputFileName);
    } catch(FileNotFoundException e) {
      e.printStackTrace();
    }
  }

  public void trace(SubstrateNetwork network, HashMap<Request, Mapping> mappings,
    RequestEvent event) {
    Request request = event.getRequest();
    writer.println(String.format("%s %s %s %s %s %s %s %s %s %s",
      event,
      (mappings.containsKey(request) ? "1" : "0"),
      network.getMaximumNodesLoad(),
      network.getAverageNodesLoad(),
      network.getMaximumLinksBandwidthLoad(),
      network.getAverageLinksBandwidthLoad(),
      network.getNodesLoadStandardDeviation(),
      network.getLinksBandwidthLoadStandardDeviation(),
      (event.isArrivalEvent() && mappings.containsKey(request) ?
        mappings.get(request).getAvailability() : "0.0"),
      (event.isArrivalEvent() && mappings.containsKey(request) ?
        mappings.get(request).getNodeSharingRate(request.getAmountNodes()) : "0.0")
      ));
  }

  public void println(Object text) {
    writer.println(text);
  }

  public void close() {
    writer.close();
  }

  public String getOutputFileName() {
    return outputFileName;
  }
}
