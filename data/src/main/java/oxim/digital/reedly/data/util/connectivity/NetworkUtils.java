package oxim.digital.reedly.data.util.connectivity;

import rx.Single;

public interface NetworkUtils {

    Single<Boolean> isConnectedToInternet();

    Single<NetworkData> getActiveNetworkData();
}