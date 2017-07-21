package oxim.digital.reedly.background;

import android.app.PendingIntent;
import android.app.job.JobParameters;
import android.app.job.JobService;

import javax.inject.Inject;

import oxim.digital.reedly.dagger.application.ReedlyApplication;
import oxim.digital.reedly.device.notification.Notifications;
import oxim.digital.reedly.domain.interactor.article.GetUnreadArticlesCountUseCase;
import oxim.digital.reedly.domain.interactor.feed.GetUserFeedsUseCase;
import oxim.digital.reedly.domain.interactor.feed.update.UpdateFeedUseCase;
import rx.Observable;
import rx.schedulers.Schedulers;

public final class BackgroundFeedsUpdateService extends JobService {

    private static final int NEW_ARTICLES_NOTIFICATION_ID = 1832;

    @Inject
    GetUserFeedsUseCase getUserFeedsUseCase;

    @Inject
    UpdateFeedUseCase updateFeedUseCase;

    @Inject
    GetUnreadArticlesCountUseCase getUnreadArticlesCountUseCase;

    @Inject
    Notifications notifications;

    @Inject
    NotificationFactory notificationFactory;

    @Inject
    PendingIntent notificationPendingIntent;

    @Override
    public void onCreate() {
        super.onCreate();
        ReedlyApplication.from(getApplication()).getApplicationComponent().inject(this);
    }

    @Override
    public boolean onStartJob(final JobParameters jobParameters) {
        getUnreadArticlesCountUseCase.execute()
                                     .subscribeOn(Schedulers.io())
                                     .subscribe(unreadCount -> onUnreadItemsCount(unreadCount, jobParameters),
                                                throwable -> handleError(throwable, jobParameters));
        return true;
    }

    private void onUnreadItemsCount(final long unreadItemsCount, final JobParameters jobParameters) {
        getUserFeedsUseCase.execute()
                           .flatMapObservable(Observable::from)
                           .flatMap(feed -> updateFeedUseCase.execute(feed).toObservable())
                           .toCompletable()
                           .subscribeOn(Schedulers.io())
                           .subscribe(() -> onUpdatedFeeds(unreadItemsCount, jobParameters),
                                      throwable -> handleError(throwable, jobParameters));
    }

    private void onUpdatedFeeds(final long unreadItemsCount, final JobParameters jobParameters) {
        getUnreadArticlesCountUseCase.execute()
                                     .subscribeOn(Schedulers.io())
                                     .doOnSuccess(c -> jobFinished(jobParameters, false))
                                     .subscribe(newUnreadCount -> onNewUnreadCount(unreadItemsCount, newUnreadCount),
                                                throwable -> handleError(throwable, jobParameters));
    }

    private void onNewUnreadCount(final long oldCount, final long newCount) {
        if (newCount > oldCount) {
            showNewArticlesNotification();
        }
    }

    private void handleError(final Throwable throwable, final JobParameters jobParameters) {
        throwable.printStackTrace();
        jobFinished(jobParameters, false);
    }

    private void showNewArticlesNotification() {
        notifications.showNotification(NEW_ARTICLES_NOTIFICATION_ID,
                                       notificationFactory.createNewArticlesNotification(notificationPendingIntent));
    }

    @Override
    public boolean onStopJob(final JobParameters jobParameters) {
        return false;
    }
}
