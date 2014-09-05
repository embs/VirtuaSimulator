package simulator.io;

import java.util.ArrayList;

import simulator.network.SubstrateNetwork;
import simulator.simulation.Request;

public interface IVNMPReader {
  public SubstrateNetwork getSubstrateNetwork();
  public ArrayList<Request> getVirtualNetworkRequests();
  public SubstrateNetwork readSubstrateNetwork(String filename);
  public ArrayList<Request> readVirtualNetworkRequests(String filename);
}
