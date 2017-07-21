package oxim.digital.reedly.data.util.connectivity;

import java.net.InetAddress;
import java.net.UnknownHostException;

import rx.Single;

public final class NetworkUtilsImpl implements NetworkUtils {

    private static final String EMPTY = "";
    private static final String PING_ADDRESS = "www.google.com";
    private final ConnectivityManagerWrapper connectivityManagerWrapper;

    public NetworkUtilsImpl(final ConnectivityManagerWrapper connectivityManagerWrapper) {
        this.connectivityManagerWrapper = connectivityManagerWrapper;
    }

    @Override
    public Single<Boolean> isConnectedToInternet() {
        return Single.fromCallable(() -> (isConnectedToNetwork() && canResolveAddress(PING_ADDRESS)));
    }

    @Override
    public Single<NetworkData> getActiveNetworkData() {
        return Single.fromCallable(connectivityManagerWrapper::getNetworkData);
    }

    private boolean canResolveAddress(final String url) {
        return pingAddress(url);
    }

    private boolean isConnectedToNetwork() {
        return connectivityManagerWrapper.isConnectedToNetwork();
    }

    private boolean pingAddress(final String url) {
        try {
            final InetAddress address = InetAddress.getByName(url);
            return address != null && !EMPTY.equals(address.getHostAddress());
        } catch (final UnknownHostException e) {
            return false;
        }
    }
}
