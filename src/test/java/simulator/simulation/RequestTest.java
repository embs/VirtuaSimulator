package simulator.simulation;

import junit.framework.TestCase;

import java.util.HashMap;

import simulator.network.components.virtual.*;

public class RequestTest extends TestCase {

  private Request request;
  private HashMap<Integer, VirtualNode> virtualNodes;
  private HashMap<String, VirtualLink> virtualLinks;

  public void setUp() {
    request = new Request(1, 1, 100);
    virtualNodes = new HashMap<Integer, VirtualNode>();
    virtualLinks = new HashMap<String, VirtualLink>();
    for(int i = 1; i < 6; i++) {
      virtualNodes.put(i, new VirtualNode(i, i * 100));
    }
    for(int i = 1; i < 5; i++) {
      String linkId = String.format("%s:%s", i, 5);
      VirtualLink virtualLink = new VirtualLink(linkId, virtualNodes.get(i),
                                                 virtualNodes.get(5), 5, 5);
      virtualLinks.put(linkId, virtualLink);
    }
  }

  public void testGetVirtualNodes() {
    assertNotNull(request.getVirtualNodes());
  }

  public void testGetVirtualLinks() {
    assertNotNull(request.getVirtualLinks());
  }

  public void testGetAmountNodes() {
    assertEquals(0, request.getAmountNodes());
  }

  public void testGetAmountLinks() {
    assertEquals(0, request.getAmountLinks());
  }

  public void testSetVirtualNodes() {
    request.setVirtualNodes(virtualNodes);
    assertEquals(virtualNodes.size(), request.getAmountNodes());
  }

  public void testSetVirtualLinks() {
    request.setVirtualLinks(virtualLinks);
    assertEquals(virtualLinks.size(), request.getAmountLinks());
  }
}
