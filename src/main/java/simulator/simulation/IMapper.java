package simulator.simulation;

import simulator.network.SubstrateNetwork;

public interface IMapper {
  public Mapping map(Request request, SubstrateNetwork substrateNetwork);
}
