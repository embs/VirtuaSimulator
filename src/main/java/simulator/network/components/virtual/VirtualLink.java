package simulator.network.components.virtual;


import java.util.ArrayList;
import java.util.List;

import simulator.network.components.Link;

public class VirtualLink extends Link {

  public VirtualLink(String id, VirtualNode sourceNode, VirtualNode destinyNode,
                     double bandwidth, double delay) {
    super(id, sourceNode, destinyNode, bandwidth, delay);
  }
}
