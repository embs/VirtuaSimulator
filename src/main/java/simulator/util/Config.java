package simulator.util;

import java.math.MathContext;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.List;

public class Config {
  // Parâmetros do Simulator
  public static final int MAX_ALLOCATION_TRIES = 8;
  public static final int VIRTUAL_LINKS_SIZE = 20;
  public static final double A = 0.7;
  public static final int MIN_RCL_SIZE = 5;
  public static final int TRY_FIND_NODE_AND_LINK = 20;
  public static final boolean ALEATORY_FIRST_NODE = false;
  public static final boolean COMPARE_NODES_BY_AVAILABILITY = true; // false = capacidade, true = disponibilidade
  public static final MathContext MATH_CONTEXT = new MathContext(10, RoundingMode.HALF_UP);
  public static final boolean CONSIDER_DELAY = true;
  public static boolean DEBUG = false;
  public static final int NUMBER_OF_REQUESTS = 40;
  public static final double SOFTWARE_AGING_CONSTANT = 0.1;
  public static boolean allowNodeSharing = false;
  public static boolean considerIntermediaryNodes = true;
  public static boolean softwareAgingScenario = false;

  // Técnicas de mapeamento de redes virtuais FIXME têm de estar consistentes com os nomes de arquivos abaixo
  public static final int GRASP = 0;
  public static final int GREEDY = 1;
  public static final int DVINESP = 2;
  public static final int DVINELB = 3;
  public static final int RVINE = 4;

  // Diretórios e arquivos
  public static final String DATA_DIR = "/home/embs/Data/simulator/";
  public static final String VNMP_INSTANCES_DIR = DATA_DIR + "VNMP_Instances/";
  public static final String VNMP_INSTANCES_VINE_YARD_DIR = DATA_DIR + "VNMP_Instances_ViNE-Yard/";
  public static final String NO_DELAY_DIR = DATA_DIR + "NO_DELAY/";
  public static final String SIMULATOR_TRACES_DIR = DATA_DIR + "simulator_traces/";
  public static final String GRAPHS_DATA_DIR = DATA_DIR + "graphs_data/";
  public static final List<String> GRASP_FOLDERS = Arrays.asList(new String[]{"20", "30", "50", "100"});
  public static final String SUBSTRATE_NETWORK_FILE_NAME = "sub.txt";
  public static final String[] EXECUTION_TIME_TRACE_FILE_NAME = new String[] {
    "NONE", "time.out", "time.dvine.out", "time.dvinelb.out", "time.rvine.out"
  };
  public static final String[] VINE_YARD_TRACE_FILE_NAME = new String[] {
    "NONE", "MySimINFOCOM2009.out", "MySimINFOCOM2009.dvine.out", "MySimINFOCOM2009.dvinelb.out", "MySimINFOCOM2009.rvine.out"
  };
  public static final String[] VINE_YARD_MAPPINGS_FILE_NAME = new String[] {
    "NONE", "mappings.out", "mappings.dvine.out", "mappings.dvinelb.out", "mappings.rvine.out"
  };

  // Identificadores de métricas
  public static final String SIMULATION_EXECUTION_TIME_IDENTIFIER = "executionTime";
  public static final String AVERAGE_AVAILABILITY_IDENTIFIER = "averageAvailability";
  public static final String SIMULATION_ACCEPTED_REQUESTS_IDENTIFIER = "acceptedRequests";
  public static final String SIMULATION_ACCEPTANCE_RATE_IDENTIFIER = "acceptanceRate";
  public static final String AVERAGE_NODES_LOAD_IDENTIFIER = "averageNodesLoad";
  public static final String AVERAGE_LINKS_LOAD_IDENTIFIER = "averageLinksLoad";
  public static final String AVAILABILITY_STD_DEV_IDENTIFIER = "availabilityStdDev";
  public static final String NODES_LOAD_STD_DEV_IDENTIFIER = "nodesLoadStdDev";
  public static final String LINKS_LOAD_STD_DEV_IDENTIFIER = "linksLoadStdDev";
  public static final String REQUESTS_AVAILABILITIES_IDENTIFIER = "requestsAvailabilities";
  public static final String REJECTIONS_BY_NODE_IDENTIFIER = "rejectionsByNode";
  public static final String REJECTIONS_BY_LINK_IDENTIFIER = "rejectionsByLink";
  public static final String AVERAGE_PHYSICAL_NODES_SHARE_IDENTIFIER = "averagePhysicalNodesShare";
}
