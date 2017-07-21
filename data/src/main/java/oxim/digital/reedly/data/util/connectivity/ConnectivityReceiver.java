package oxim.digital.reedly.data.util.connectivity;

import rx.Observable;
import rx.Single;

public interface ConnectivityReceiver {

    Observable<Boolean> getConnectivityStatus();

    Single<Boolean> isConnected();
}
