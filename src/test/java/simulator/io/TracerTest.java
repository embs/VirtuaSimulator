package simulator.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;

import junit.framework.TestCase;

import generator.network.SubstrateNetworkGenerator;
import generator.network.VirtualNetworksGenerator;

import simulator.mapping.Mapping;
import simulator.network.SubstrateNetwork;
import simulator.simulation.Request;
import simulator.simulation.RequestEvent;

public class TracerTest extends TestCase {

  private Tracer tracer;
  private SubstrateNetwork network;
  private HashMap<Request, Mapping> mappings;
  private RequestEvent requestEvent;
  private final String OUTPUT_FILE_NAME = "simulation_trace.txt";

  protected void setUp() {
    tracer = new Tracer(OUTPUT_FILE_NAME);
    network = new SubstrateNetworkGenerator().generate(10);
    mappings = new HashMap<Request, Mapping>();
    Request request =
      new VirtualNetworksGenerator().generateVirtualNetworks(1).get(0);
    requestEvent = new RequestEvent(request, request.getCreationTime(),
      RequestEvent.ARRIVAL_EVENT);
  }

  protected void tearDown() {
    File outputFile = new File(OUTPUT_FILE_NAME);
    if(outputFile.exists()) {
      outputFile.delete();
    }
  }

  public void testConstructTracerWithOutputFile() {
    assertNotNull(tracer);
    assertEquals("Tracer constructor should set output file name.",
      OUTPUT_FILE_NAME, tracer.getOutputFileName());
  }

  public void testTraceRequestEvent() {
    tracer.trace(network, mappings, requestEvent);
    tracer.close();

    assertTrue("Tracer should create a new file but none was created.",
      new File(OUTPUT_FILE_NAME).exists());
  }

  public void testPrintArbitraryLine() throws Exception {
    String arbitraryLine = "Some random text.";
    tracer.println(arbitraryLine);
    tracer.close();

    BufferedReader reader = getReaderForFile(OUTPUT_FILE_NAME);
    assertEquals(arbitraryLine, reader.readLine());
  }

  public void testOutputFileFormat() throws Exception {
    tracer.trace(network, mappings, requestEvent);
    tracer.close();
    BufferedReader reader = getReaderForFile(OUTPUT_FILE_NAME);
    ArrayList<String> lines = new ArrayList<String>();
    String line;

    while((line = reader.readLine()) != null) {
      lines.add(line);
    }

    assertEquals("There should be one line for each virtual request.",
      1, lines.size());
    for(int i = 0; i < lines.size() - 1; i++) {
      assertEquals("Each virtual request line should be composed by 13 tokens.",
        13, lines.get(i).split(" ").length);
    }
  }

  private BufferedReader getReaderForFile(String fileName) throws Exception {
    return new BufferedReader(new FileReader(new File(fileName)));
  }
}
