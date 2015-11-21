package simulator.network.components;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;

import junit.framework.TestCase;

import simulator.network.components.physical.*;
import simulator.network.components.virtual.*;

public class PathTest extends TestCase {

  private Path path;
  private PhysicalNode physicalNode1;
  private PhysicalNode physicalNode2;
  private PhysicalNode physicalNode3;
  private PhysicalLink physicalLink1;
  private PhysicalLink physicalLink2;
  private VirtualNode n1;
  private VirtualNode n2;

  public PathTest(String testName) {
    super(testName);
    physicalNode1 = new PhysicalNode(1, 100);
    physicalNode2 = new PhysicalNode(2, 100);
    physicalNode3 = new PhysicalNode(3, 100);
    physicalLink1 = new PhysicalLink("1:2", physicalNode1, physicalNode2, 10, 30, 0);
    physicalLink2 = new PhysicalLink("2:3", physicalNode2, physicalNode3, 10, 30, 0);
    path = new Path(physicalNode1, physicalNode3);
    path.addLink(physicalLink1);
    path.addLink(physicalLink2);
    n1 = new VirtualNode(1, 100);
    n2 = new VirtualNode(2, 200);
  }

  public void testGetTotalDelay() {
    double totalDelay = physicalLink1.getDelay() + physicalLink2.getDelay();
    assertEquals(totalDelay, path.getTotalDelay());
  }

  public void testCanHostWhenItCan() {
    VirtualLink virtualLink = new VirtualLink("1:2", n1, n2, 10, 60);
    assertTrue(path.canHost(virtualLink));
  }

  public void testCanHostWhenItCannotBecauseItsBandwidth() {
    VirtualLink virtualLink = new VirtualLink("1:2", n1, n2, 11, 60);
    assertFalse(path.canHost(virtualLink));
  }

  public void testCanHostWhenItCannotBecauseItsDelay() {
    VirtualLink virtualLink = new VirtualLink("1:2", n1, n2, 10, 50);
    assertFalse(path.canHost(virtualLink));
  }

  public void testCanHostWhenItCannotBecauseBothItsBandwidthAndDelay() {
    VirtualLink virtualLink = new VirtualLink("1:2", n1, n2, 11, 50);
    assertFalse(path.canHost(virtualLink));
  }

  public void testGetAvailability() {
    MathContext mathContext = new MathContext(10, RoundingMode.HALF_UP);
    BigDecimal availability = new BigDecimal(1, mathContext);
    availability = availability.multiply(physicalNode1.getNodeAvailability(),
      mathContext).multiply(physicalNode3.getNodeAvailability(), mathContext).
      multiply(physicalNode2.getIntermediaryNodeAvailability(), mathContext).
      multiply(physicalLink1.getAvailability(), mathContext).multiply(
        physicalLink2.getAvailability(), mathContext);
    assertEquals(availability.round(new MathContext(8)).doubleValue(),
      path.getAvailability().round(new MathContext(8)).doubleValue());
  }

  public void testGetLinks() {
    ArrayList<PhysicalLink> links = path.getLinks();
    assertTrue(links.contains(physicalLink1));
    assertTrue(links.contains(physicalLink2));
  }

  public void testGetSourceNode() {
    assertEquals(physicalNode1, path.getSourceNode());
  }

  public void testGetDestinyNode() {
    assertEquals(physicalNode3, path.getDestinyNode());
  }

  public void testIsEmpty() {
    assertFalse(path.isEmpty());
  }
}
