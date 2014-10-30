package simulator.simulation;

import java.util.HashMap;

import simulator.network.components.virtual.*;

/*
 *  Virtual Network Request
 */
public class Request {
  private int id;
  private int creationTime;
  private int lifeTime;
  private HashMap<Integer,VirtualNode> virtualNodes;
  private HashMap<String, VirtualLink> virtualLinks;
  private int amountVLinks;
  private int amountVNodes;

  public Request(int id, int creationTime, int lifeTime) {
    this.id = id;
    this.creationTime = creationTime;
    this.lifeTime = lifeTime;
    this.virtualNodes = new HashMap<Integer, VirtualNode>();
    this.virtualLinks = new HashMap<String, VirtualLink>();
  }

  public Request(int id, int creationTime, int lifeTime,
                 HashMap<Integer,VirtualNode> virtualNodes,
                 HashMap<String, VirtualLink> virtualLinks) {
    this.id = id;
    this.creationTime = creationTime;
    this.lifeTime = lifeTime;
    this.virtualNodes = virtualNodes;
    this.virtualLinks = virtualLinks;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public int getCreationTime() {
    return creationTime;
  }

  public void setCreationTime(int creationTime) {
    this.creationTime = creationTime;
  }

  public int getLifeTime() {
    return lifeTime;
  }

  public void setLifeTime(int lifeTime) {
    this.lifeTime = lifeTime;
  }

  public int getDepartureTime() {
    return creationTime + lifeTime;
  }

  public HashMap<Integer, VirtualNode> getVirtualNodes() {
    return virtualNodes;
  }

  public void setVirtualNodes(HashMap<Integer, VirtualNode> virtualNodes) {
    this.virtualNodes = virtualNodes;
  }

  public HashMap<String, VirtualLink> getVirtualLinks() {
    return virtualLinks;
  }

  public void setVirtualLinks(HashMap<String, VirtualLink> virtualLinks) {
    this.virtualLinks = virtualLinks;
  }

  public int getAmountNodes() {
    return virtualNodes.size();
  }

  public int getAmountLinks() {
    return virtualLinks.size();
  }
}
