package simulator.mapping;

import simulator.network.SubstrateNetwork;
import simulator.simulation.Request;

/**
 *  This mapper hates requests and rejects all of them.
 */
public class HaterMapper implements IMapper {
  public Mapping map(Request request, SubstrateNetwork substrateNetwork) {
    return null;
  }
}
