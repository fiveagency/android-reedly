package oxim.digital.reedly.data.util.connectivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import rx.Observable;
import rx.Scheduler;
import rx.Single;
import rx.subjects.PublishSubject;

public final class ConnectivityReceiverImpl extends BroadcastReceiver implements ConnectivityReceiver {

    private static final String TAG = ConnectivityReceiverImpl.class.getSimpleName();

    private static final String ACTION_CONNECTIVITY_CHANGE = "android.net.conn.CONNECTIVITY_CHANGE";

    private final NetworkUtils networkUtils;
    private final Scheduler backgroundScheduler;
    private final PublishSubject<Boolean> subject;

    private boolean isConnected;

    public ConnectivityReceiverImpl(final Context context, final NetworkUtils networkUtils, final Scheduler backgroundScheduler) {
        this.networkUtils = networkUtils;
        this.backgroundScheduler = backgroundScheduler;
        final IntentFilter intentFilter = new IntentFilter(ACTION_CONNECTIVITY_CHANGE);
        context.registerReceiver(this, intentFilter);
        this.subject = PublishSubject.create();
    }

    @Override
    public Observable<Boolean> getConnectivityStatus() {
        return subject.subscribeOn(backgroundScheduler)
                      .observeOn(backgroundScheduler);
    }

    @Override
    public Single<Boolean> isConnected() {
        return networkUtils.isConnectedToInternet();
    }

    @Override
    public void onReceive(final Context context, final Intent intent) {
        if (subject == null) {
            return;
        }

        checkConnectionStatus();
    }

    private void checkConnectionStatus() {
        networkUtils.isConnectedToInternet()
                    .subscribeOn(backgroundScheduler)
                    .observeOn(backgroundScheduler)
                    .subscribe(this::onNetworkStatus, this::onNetworkStatusError);
    }

    private void onNetworkStatus(final Boolean isConnected) {
        if (this.isConnected != isConnected) {
            this.isConnected = isConnected;
            subject.onNext(isConnected);
        }
    }

    private void onNetworkStatusError(final Throwable throwable) {
        // No-op
    }
}