package simulator.tasks;

import java.io.File;

import junit.framework.TestCase;

public class RunFirstScenarioTest extends TestCase {

  public void testOutputFilesCreation() {
    File outputFile = new File("VirtuaSimulationSharingNodesOptFIVNMPs");
    outputFile.mkdir();
    String[] args = new String[] {
      "src/test/resources/FirstScenario",
      "./VirtuaSimulationSharingNodesOptFIVNMPs/"
    };
    RunFirstScenario.main(args);

    assertTrue("Execution should create output files directory.",
      outputFile.exists());
  }
}

// REFACTOR PHASE!!!
