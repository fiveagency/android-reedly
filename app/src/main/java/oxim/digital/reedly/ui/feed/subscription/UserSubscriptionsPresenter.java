package oxim.digital.reedly.ui.feed.subscription;

import com.annimon.stream.Stream;

import java.util.List;

import javax.inject.Inject;

import oxim.digital.reedly.base.BasePresenter;
import oxim.digital.reedly.domain.interactor.feed.DeleteFeedUseCase;
import oxim.digital.reedly.domain.interactor.feed.update.DisableBackgroundFeedUpdatesUseCase;
import oxim.digital.reedly.domain.interactor.feed.update.EnableBackgroundFeedUpdatesUseCase;
import oxim.digital.reedly.domain.interactor.feed.GetUserFeedsUseCase;
import oxim.digital.reedly.domain.interactor.feed.update.ShouldUpdateFeedsInBackgroundUseCase;
import oxim.digital.reedly.domain.interactor.feed.update.UpdateFeedUseCase;
import oxim.digital.reedly.domain.model.Feed;
import oxim.digital.reedly.ui.mapper.FeedViewModeMapper;
import oxim.digital.reedly.ui.model.FeedViewModel;
import rx.Completable;
import rx.functions.Action1;

public final class UserSubscriptionsPresenter extends BasePresenter<UserSubscriptionsContract.View> implements UserSubscriptionsContract.Presenter {

    @Inject
    GetUserFeedsUseCase getUserFeedsUseCase;

    @Inject
    DeleteFeedUseCase deleteFeedUseCase;

    @Inject
    UpdateFeedUseCase updateFeedUseCase;

    @Inject
    ShouldUpdateFeedsInBackgroundUseCase shouldUpdateFeedsInBackgroundUseCase;

    @Inject
    EnableBackgroundFeedUpdatesUseCase enableBackgroundFeedUpdatesUseCase;

    @Inject
    DisableBackgroundFeedUpdatesUseCase disableBackgroundFeedUpdatesUseCase;

    @Inject
    FeedViewModeMapper feedViewModeMapper;

    public UserSubscriptionsPresenter(final UserSubscriptionsContract.View view) {
        super(view);
    }

    @Override
    public void start() {
        super.start();
        checkBackgroundFeedUpdates();
    }

    @Override
    public void activate() {
        super.activate();
        updateUserSubscriptions();
    }

    @Override
    public void showArticles(final FeedViewModel feedViewModel) {
        router.showArticlesScreen(feedViewModel.id, feedViewModel.title);
    }

    @Override
    public void showAddNewFeed() {
        router.showAddNewFeedScreen();
    }

    @Override
    public void updateUserSubscriptions() {
        fetchUserFeeds();
    }

    @Override
    public void unSubscribeFromFeed(final FeedViewModel selectedFeedModel) {
        viewActionQueue.subscribeTo(deleteFeedUseCase.execute(selectedFeedModel.id)
                                                     .toSingleDefault(true)
                                                     .flatMap(b -> getUserFeedsUseCase.execute())
                                                     .map(feedViewModeMapper::mapFeedsToViewModels)
                                                     .map(this::toViewAction),
                                    this::logError);
    }

    @Override
    public void showFavouriteArticles() {
        router.showFavouriteArticlesScreen();
    }

    @Override
    public void enableBackgroundFeedUpdates() {
        viewActionQueue.subscribeTo(enableBackgroundFeedUpdatesUseCase.execute(),
                                    view -> view.setIsBackgroundFeedUpdateEnabled(true),
                                    this::logError);
    }

    @Override
    public void disableBackgroundFeedUpdates() {
        viewActionQueue.subscribeTo(disableBackgroundFeedUpdatesUseCase.execute(),
                                    view -> view.setIsBackgroundFeedUpdateEnabled(false),
                                    Throwable::printStackTrace);
    }

    private void fetchUserFeeds() {
        viewActionQueue.subscribeTo(getUserFeedsUseCase.execute()
                                                       .doOnSuccess(this::updateUserFeeds)
                                                       .map(feedViewModeMapper::mapFeedsToViewModels)
                                                       .map(this::toViewAction),
                                    this::logError);
    }

    private Action1<UserSubscriptionsContract.View> toViewAction(final List<FeedViewModel> feeds) {
        return view -> view.showFeedSubscriptions(feeds);
    }

    private void updateUserFeeds(final List<Feed> feeds) {
        Stream.of(feeds)
              .map(feed -> updateFeedUseCase.execute(feed))
              .forEach(this::handleFeedUpdateCompletable);
    }

    private void handleFeedUpdateCompletable(final Completable feedUpdateCompletable) {
        addSubscription(feedUpdateCompletable.subscribe(() -> { },
                                                        this::logError));
    }

    private void checkBackgroundFeedUpdates() {
        viewActionQueue.subscribeTo(shouldUpdateFeedsInBackgroundUseCase.execute()
                                                                        .map(this::toShouldUpdateFeedsViewAction),
                                    Throwable::printStackTrace);
    }

    private Action1<UserSubscriptionsContract.View> toShouldUpdateFeedsViewAction(final boolean shouldUpdate) {
        return view -> view.setIsBackgroundFeedUpdateEnabled(shouldUpdate);
    }
}
