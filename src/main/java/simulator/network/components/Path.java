package simulator.network.components;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;

import simulator.network.components.physical.*;
import simulator.network.components.virtual.VirtualLink;

public class Path {
  private PhysicalNode sourceNode;
  private PhysicalNode destinyNode;
  private ArrayList<PhysicalLink> links;

  public Path(PhysicalNode source, PhysicalNode destiny) {
    links = new ArrayList<PhysicalLink>();
    sourceNode = source;
    destinyNode = destiny;
  }

  public void addLink(PhysicalLink link) {
    links.add(link);
  }

  public ArrayList<PhysicalLink> getLinks() {
    return links;
  }

  public PhysicalNode getSourceNode() {
    return sourceNode;
  }

  public PhysicalNode getDestinyNode() {
    return destinyNode;
  }

  public double getTotalDelay() {
    double total = 0;
    for(PhysicalLink link : links) {
      total += link.getDelay();
    }

    return total;
  }

  public boolean isEmpty() {
    return links.isEmpty();
  }

  public boolean canHost(VirtualLink virtualLink) {
    for(PhysicalLink physicalLink : links) {
      if(!physicalLink.canHost(virtualLink))
        return false;
    }

    return this.getTotalDelay() <= virtualLink.getDelay();
  }

  // FIXME considera nós intermediários
  public BigDecimal getAvailability() {
    MathContext mathContext = new MathContext(10, RoundingMode.HALF_UP);
    BigDecimal availability = new BigDecimal(1, mathContext);
    ArrayList<PhysicalNode> intermediaryNodes = new ArrayList<PhysicalNode>();
    for(PhysicalLink link : links) {
      availability = availability.multiply(link.getAvailability(), mathContext);
      PhysicalNode[] linkNodes = { (PhysicalNode) link.getSourceNode(),
        (PhysicalNode) link.getDestinyNode() };
      for(PhysicalNode physicalNode : linkNodes) {
        if(!physicalNode.equals(sourceNode) && !physicalNode.equals(destinyNode)
           && !intermediaryNodes.contains(physicalNode)) {
          intermediaryNodes.add(physicalNode);
        }
      }
    }
    for(PhysicalNode intermediaryNode : intermediaryNodes) {
      availability = availability.multiply(
        intermediaryNode.getIntermediaryNodeAvailability(), mathContext);
    }
    availability = availability.multiply(sourceNode.getNodeAvailability(),
      mathContext).multiply(destinyNode.getNodeAvailability(), mathContext);

    return availability;
  }
}
