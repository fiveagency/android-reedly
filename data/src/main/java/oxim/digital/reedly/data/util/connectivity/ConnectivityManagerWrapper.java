package oxim.digital.reedly.data.util.connectivity;

public interface ConnectivityManagerWrapper {

    boolean isConnectedToNetwork();

    NetworkData getNetworkData();
}
