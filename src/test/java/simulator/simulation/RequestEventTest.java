package simulator.simulation;

import junit.framework.TestCase;

public class RequestEventTest extends TestCase {

  private RequestEvent event;
  private Request request;

  public RequestEventTest(String testName) {
    super(testName);
  }

  public void setUp() {
    request = new Request(1, 2, 3);
    event = new RequestEvent(request, 2, RequestEvent.ARRIVAL_EVENT);
  }

  public void testGetRequest() {
    assertEquals(request, event.getRequest());
  }

  public void testGetType() {
    assertEquals(RequestEvent.ARRIVAL_EVENT, event.getType());
  }

  public void testGetTime() {
    assertEquals(2, event.getTime());
  }

  public void testCompareToGreaterEvent() {
    assertEquals(-1, event.compareTo(new RequestEvent(request, 3, RequestEvent.ARRIVAL_EVENT)));
  }

  public void testCompareToLesserEvent() {
    assertEquals(1, event.compareTo(new RequestEvent(request, 1, RequestEvent.ARRIVAL_EVENT)));
  }
}
