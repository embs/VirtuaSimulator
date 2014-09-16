package simulator.mapping;

import simulator.network.SubstrateNetwork;
import simulator.simulation.Request;

public interface IMapper {
  public Mapping map(Request request, SubstrateNetwork substrateNetwork);
}
