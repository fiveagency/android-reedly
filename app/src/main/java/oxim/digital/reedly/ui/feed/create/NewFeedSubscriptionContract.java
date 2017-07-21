package oxim.digital.reedly.ui.feed.create;

import oxim.digital.reedly.base.BaseView;
import oxim.digital.reedly.base.ScopedPresenter;

public interface NewFeedSubscriptionContract {

    interface View extends BaseView {

        void showMessage(String message);

        void clearMessage();

        void showIsLoading(boolean isLoading);

    }

    interface Presenter extends ScopedPresenter {

        void addNewFeed(String feedUrl);
    }
}
