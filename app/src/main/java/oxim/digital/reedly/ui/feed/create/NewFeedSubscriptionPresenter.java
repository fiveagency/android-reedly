package oxim.digital.reedly.ui.feed.create;

import android.content.res.Resources;
import android.database.sqlite.SQLiteConstraintException;

import javax.inject.Inject;

import oxim.digital.reedly.R;
import oxim.digital.reedly.base.BasePresenter;
import oxim.digital.reedly.domain.interactor.feed.AddNewFeedUseCase;

public final class NewFeedSubscriptionPresenter extends BasePresenter<NewFeedSubscriptionContract.View> implements NewFeedSubscriptionContract.Presenter {

    @Inject
    AddNewFeedUseCase addNewFeedUseCase;

    @Inject
    Resources resources;

    public NewFeedSubscriptionPresenter(final NewFeedSubscriptionContract.View view) {
        super(view);
    }

    @Override
    public void addNewFeed(final String feedUrl) {
        doIfConnectedToInternet(() -> initiateNewFeedAddition(feedUrl), this::showNoInternetConnection);
    }

    private void initiateNewFeedAddition(final String feedUrl) {
        doIfViewNotNull(view -> view.showIsLoading(true));
        viewActionQueue.subscribeTo(addNewFeedUseCase.execute(feedUrl), this::onAddNewFeedCompletion, this::onAddNewFeedError);
    }

    private void onAddNewFeedCompletion(final NewFeedSubscriptionContract.View view) {
        view.showIsLoading(false);
        back();
        router.showUserSubscriptionsScreen();
    }

    private void onAddNewFeedError(final Throwable throwable) {
        logError(throwable);
        doIfViewNotNull(view -> {
            view.showIsLoading(false);
            view.showMessage((throwable instanceof SQLiteConstraintException) ? resources.getString(R.string.already_subscribed_message)
                                                                              : resources.getString(R.string.incorrect_feed_url_message));
        });
    }

    private void showNoInternetConnection() {
        doIfViewNotNull(view -> view.showMessage(resources.getString(R.string.please_check_internet_connection_message)));
    }

    @Override
    public void back() {
        destroy();
        router.hideAddNewFeedScreen();
    }
}

