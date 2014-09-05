package simulator.network.components;

import java.util.List;
import java.util.ArrayList;

public abstract class Node {
  private int id;
  private double capacity;
  private List<Link> links;

  public Node(int id, double capacity) {
    this.id = id;
    this.capacity = capacity;
    this.links = new ArrayList<Link>();
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public double getCapacity() {
    return capacity;
  }

  public void setCapacity(double capacity) {
    this.capacity = capacity;
  }

  public void attachLink(Link link) {
    links.add(link);
  }

  public void detachLink(Link link) {
    links.remove(link);
  }

  public Link[] getAttachedLinks() {
    return links.toArray(new Link[1]);
  }

  public boolean isSourceNodeFor(Link link) {
    return link.getSourceNode().getId() == id;
  }

  public boolean isDestinyNodeFor(Link link) {
    return link.getDestinyNode().getId() == id;
  }

  public boolean equals(Object o) {
    if(o instanceof Node) {
      Node node = (Node) o;
      if(this.id == node.getId())
        return true;
    }

    return false;
  }
}
