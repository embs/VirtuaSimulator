package simulator.network.components;

public abstract class Link {

  private String id;
  private Node sourceNode;
  private Node destinyNode;
  private double bandwidthCapacity;
  private int delay;

  public Link(String id, Node sourceNode, Node destinyNode, double bandwidth, int delay) {
    this.id = id;
    this.sourceNode = sourceNode;
    this.destinyNode = destinyNode;
    this.bandwidthCapacity = bandwidth;
    this.delay = delay;
    sourceNode.attachLink(this);
    destinyNode.attachLink(this);
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public Node getSourceNode() {
    return sourceNode;
  }

  public void setSourceNode(Node sourceNode) {
    this.sourceNode = sourceNode;
  }

  public Node getDestinyNode() {
    return destinyNode;
  }

  public void setDestinyNode(Node destinyNode) {
    this.destinyNode = destinyNode;
  }

  public double getBandwidthCapacity() {
    return bandwidthCapacity;
  }

  public void setBandwidthCapacity(double bandwidthCapacity) {
    this.bandwidthCapacity = bandwidthCapacity;
  }

  public int getDelay() {
    return delay;
  }

  public void setDelay(int delay) {
    this.delay = delay;
  }

  public boolean isMirror(Link link) {
    String[] thisIdTokens = id.split(":");
    String[] otherIdTokens = link.getId().split(":");
    if(thisIdTokens[0].equals(otherIdTokens[1]) && thisIdTokens[1].equals(otherIdTokens[0]))
      return true;

    return false;
  }

  public Node getNodeAttachedTo(Node node) {
    if(node.equals(sourceNode))
      return destinyNode;

    return sourceNode;
  }

  public boolean equals(Object obj) {
    if(obj instanceof Link) {
      if(((Link) obj).getId().equals(id))
        return true;
    }

    return false;
  }
}
