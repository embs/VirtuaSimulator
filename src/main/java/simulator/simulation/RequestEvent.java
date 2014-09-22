package simulator.simulation;

public class RequestEvent implements Comparable<RequestEvent> {
  public static final int ARRIVAL_EVENT = 0;
  public static final int DEPARTURE_EVENT = 1;

  private Request request;
  private int type;
  private int time;

  public RequestEvent(Request request, int time, int type) {
    this.request = request;
    this.type = type;
    this.time = time;
  }

  public Request getRequest() {
    return this.request;
  }

  public int getType() {
    return this.type;
  }

  public int getTime() {
    return this.time;
  }

  public boolean isArrivalEvent() {
    return this.type == ARRIVAL_EVENT;
  }

  public boolean isDepartureEvent() {
    return this.type == DEPARTURE_EVENT;
  }

  @Override
  public int compareTo(RequestEvent requestEvent) {
    if(this.time < requestEvent.time)
      return -1;
    else if(this.time > requestEvent.time)
      return 1;
    else
      throw new RuntimeException("Dois eventos ao mesmo tempo.");
  }

  @Override
  public String toString() {
    return String.format("%s %s %s %s",
      request.getId(), time, request.getLifeTime(), type);
  }
}
