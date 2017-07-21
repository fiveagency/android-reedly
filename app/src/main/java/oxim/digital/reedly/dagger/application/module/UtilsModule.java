package oxim.digital.reedly.dagger.application.module;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import oxim.digital.reedly.dagger.application.ForApplication;
import oxim.digital.reedly.data.util.CurrentTimeProvider;
import oxim.digital.reedly.data.util.CurrentTimeProviderImpl;
import oxim.digital.reedly.data.util.PreferenceUtils;
import oxim.digital.reedly.data.util.PreferenceUtilsImpl;
import oxim.digital.reedly.data.util.connectivity.ConnectivityManagerWrapper;
import oxim.digital.reedly.data.util.connectivity.ConnectivityManagerWrapperImpl;
import oxim.digital.reedly.data.util.connectivity.ConnectivityReceiver;
import oxim.digital.reedly.data.util.connectivity.ConnectivityReceiverImpl;
import oxim.digital.reedly.data.util.connectivity.NetworkUtils;
import oxim.digital.reedly.data.util.connectivity.NetworkUtilsImpl;
import oxim.digital.reedly.domain.util.CollectionUtils;
import oxim.digital.reedly.domain.util.CollectionUtilsImpl;
import oxim.digital.reedly.util.ActivityUtils;
import oxim.digital.reedly.util.ActivityUtilsImpl;
import oxim.digital.reedly.util.DateUtils;
import oxim.digital.reedly.util.DateUtilsImpl;
import oxim.digital.reedly.util.ImageLoader;
import oxim.digital.reedly.util.ImageLoaderImpl;
import oxim.digital.reedly.device.notification.Notifications;
import oxim.digital.reedly.device.notification.NotificationsImpl;
import rx.schedulers.Schedulers;

@Module
public final class UtilsModule {

    @Provides
    @Singleton
    CollectionUtils provideCollectionUtils() {
        return new CollectionUtilsImpl();
    }

    @Provides
    @Singleton
    ActivityUtils provideActivityUtils(final CollectionUtils collectionUtils) {
        return new ActivityUtilsImpl(collectionUtils);
    }

    @Provides
    @Singleton
    CurrentTimeProvider provideCurrentTimeProvider() {
        return new CurrentTimeProviderImpl();
    }

    @Provides
    @Singleton
    DateUtils provideDateUtils() {
        return new DateUtilsImpl();
    }

    @Provides
    @Singleton
    ConnectivityReceiver provideConnectivityReceiver(final @ForApplication Context context, final NetworkUtils networkUtils) {
        return new ConnectivityReceiverImpl(context, networkUtils, Schedulers.io());
    }

    @Provides
    @Singleton
    NetworkUtils provideNetworkUtils(final ConnectivityManagerWrapper connectivityManagerWrapper) {
        return new NetworkUtilsImpl(connectivityManagerWrapper);
    }

    @Provides
    @Singleton
    ConnectivityManagerWrapper provideConnectivityManagerWrapper(final @ForApplication Context context) {
        return new ConnectivityManagerWrapperImpl(context);
    }

    @Provides
    @Singleton
    Notifications provideNotifications(final @ForApplication Context context) {
        return new NotificationsImpl(context);
    }

    @Provides
    @Singleton
    ImageLoader provideImageLoader(final @ForApplication Context context) {
        return new ImageLoaderImpl(context);
    }

    @Provides
    @Singleton
    PreferenceUtils providePreferenceUtils(final @ForApplication Context context) {
        return new PreferenceUtilsImpl(context);
    }

    public interface Exposes {

        CollectionUtils collectionUtils();

        ActivityUtils activityUtils();

        CurrentTimeProvider currentTimeProvider();

        DateUtils dateUtils();

        ConnectivityReceiver connectivityReceiver();

        Notifications notificationUtils();

        ImageLoader imageLoader();
    }
}
