package oxim.digital.reedly.ui.feed.subscription;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import java.util.ArrayList;
import java.util.List;

import oxim.digital.reedly.AppTestData;
import oxim.digital.reedly.MockViewActionQueue;
import oxim.digital.reedly.configuration.ViewActionQueueProvider;
import oxim.digital.reedly.data.util.connectivity.ConnectivityReceiver;
import oxim.digital.reedly.domain.interactor.feed.DeleteFeedUseCase;
import oxim.digital.reedly.domain.interactor.feed.GetUserFeedsUseCase;
import oxim.digital.reedly.domain.interactor.feed.update.DisableBackgroundFeedUpdatesUseCase;
import oxim.digital.reedly.domain.interactor.feed.update.EnableBackgroundFeedUpdatesUseCase;
import oxim.digital.reedly.domain.interactor.feed.update.ShouldUpdateFeedsInBackgroundUseCase;
import oxim.digital.reedly.domain.interactor.feed.update.UpdateFeedUseCase;
import oxim.digital.reedly.domain.model.Feed;
import oxim.digital.reedly.ui.feed.create.NewFeedSubscriptionContract;
import oxim.digital.reedly.ui.mapper.FeedViewModeMapper;
import oxim.digital.reedly.ui.model.FeedViewModel;
import oxim.digital.reedly.ui.router.Router;
import rx.Completable;
import rx.Observable;
import rx.Scheduler;
import rx.Single;
import rx.schedulers.Schedulers;

public final class UserSubscriptionsPresenterTest {

    @Mock
    GetUserFeedsUseCase getUserFeedsUseCase;

    @Mock
    DeleteFeedUseCase deleteFeedUseCase;

    @Mock
    UpdateFeedUseCase updateFeedUseCase;

    @Mock
    ShouldUpdateFeedsInBackgroundUseCase shouldUpdateFeedsInBackgroundUseCase;

    @Mock
    EnableBackgroundFeedUpdatesUseCase enableBackgroundFeedUpdatesUseCase;

    @Mock
    DisableBackgroundFeedUpdatesUseCase disableBackgroundFeedUpdatesUseCase;

    @Mock
    FeedViewModeMapper feedViewModeMapper;

    @Mock
    ConnectivityReceiver connectivityReceiver;

    @Mock
    Router router;

    @Mock
    ViewActionQueueProvider viewActionQueueProvider;

    @Spy
    Scheduler mainThreadScheduler = Schedulers.immediate();

    @Spy
    Scheduler backgroundThread = Schedulers.immediate();

    @Spy
    MockViewActionQueue mockViewActionHandler;

    @InjectMocks
    UserSubscriptionsPresenter userSubscriptionsPresenter;

    private UserSubscriptionsContract.View view;

    @Before
    public void setUp() throws Exception {
        view = Mockito.mock(UserSubscriptionsContract.View.class);
        userSubscriptionsPresenter = new UserSubscriptionsPresenter(view);
        MockitoAnnotations.initMocks(this);

        Mockito.when(connectivityReceiver.getConnectivityStatus()).thenReturn(Observable.just(true));
        Mockito.when(viewActionQueueProvider.queueFor(Mockito.any())).thenReturn(new MockViewActionQueue<NewFeedSubscriptionContract.View>());

        Mockito.when(shouldUpdateFeedsInBackgroundUseCase.execute()).thenReturn(Single.just(false));

        final Feed feed = new Feed(AppTestData.TEST_FEED_ID, AppTestData.TEST_STRING, AppTestData.TEST_LINK, AppTestData.TEST_LINK, AppTestData.TEST_STRING,
                                   AppTestData.TEST_LINK);
        final List<Feed> feedList = new ArrayList<>(3);
        feedList.add(feed);
        feedList.add(feed);
        feedList.add(feed);

        Mockito.when(getUserFeedsUseCase.execute()).thenReturn(Single.just(feedList));
        Mockito.when(feedViewModeMapper.mapFeedsToViewModels(Mockito.any())).thenReturn(new ArrayList<>());

        userSubscriptionsPresenter.start();
        userSubscriptionsPresenter.activate();

        //invocations from activate and start
        Mockito.clearInvocations(getUserFeedsUseCase, updateFeedUseCase, view);
    }

    @Test
    public void shouldShowArticlesInFeed() throws Exception {
        final FeedViewModel feedViewModel = new FeedViewModel(AppTestData.TEST_FEED_ID, AppTestData.TEST_STRING, AppTestData.TEST_LINK, AppTestData.TEST_LINK,
                                                              AppTestData.TEST_STRING, false);

        userSubscriptionsPresenter.showArticles(feedViewModel);
        Mockito.verify(router, Mockito.times(1)).showArticlesScreen(feedViewModel.id, feedViewModel.title);
        Mockito.verifyNoMoreInteractions(router);
    }

    @Test
    public void shouldShowAddNewFeedScreen() throws Exception {
        userSubscriptionsPresenter.showAddNewFeed();
        Mockito.verify(router, Mockito.times(1)).showAddNewFeedScreen();
        Mockito.verifyNoMoreInteractions(router);
    }

    @Test
    public void shouldUpdateUserSubscriptionsFromOrigin() throws Exception {
        Mockito.when(updateFeedUseCase.execute(Mockito.any())).thenReturn(Completable.complete());

        userSubscriptionsPresenter.updateUserSubscriptions();
        Mockito.verify(getUserFeedsUseCase, Mockito.times(1)).execute();
        Mockito.verify(updateFeedUseCase, Mockito.times(3)).execute(Mockito.any());

        //should refresh screen
        Mockito.verify(view, Mockito.times(1)).showFeedSubscriptions(Mockito.any());
    }

    @Test
    public void shouldUnSubscribeFromFeed() throws Exception {
        final FeedViewModel feedViewModel = new FeedViewModel(AppTestData.TEST_FEED_ID, AppTestData.TEST_STRING, AppTestData.TEST_LINK, AppTestData.TEST_LINK,
                                                              AppTestData.TEST_STRING, false);
        Mockito.when(deleteFeedUseCase.execute(feedViewModel.id)).thenReturn(Completable.complete());

        userSubscriptionsPresenter.unSubscribeFromFeed(feedViewModel);
        Mockito.verify(deleteFeedUseCase, Mockito.times(1)).execute(feedViewModel.id);

        //should refresh screen
        Mockito.verify(view, Mockito.times(1)).showFeedSubscriptions(Mockito.any());
    }

    @Test
    public void shouldShowFavouriteArticlesScreen() throws Exception {
        userSubscriptionsPresenter.showFavouriteArticles();
        Mockito.verify(router, Mockito.times(1)).showFavouriteArticlesScreen();
        Mockito.verifyNoMoreInteractions(router);
    }

    @Test
    public void shouldEnableBackgroundFeedUpdates() throws Exception {
        Mockito.when(enableBackgroundFeedUpdatesUseCase.execute()).thenReturn(Completable.complete());
        userSubscriptionsPresenter.enableBackgroundFeedUpdates();

        Mockito.verify(enableBackgroundFeedUpdatesUseCase, Mockito.times(1)).execute();
        Mockito.verify(view, Mockito.times(1)).setIsBackgroundFeedUpdateEnabled(true);
    }

    @Test
    public void shouldDisableBackgroundFeedUpdates() throws Exception {
        Mockito.when(disableBackgroundFeedUpdatesUseCase.execute()).thenReturn(Completable.complete());
        userSubscriptionsPresenter.disableBackgroundFeedUpdates();

        Mockito.verify(disableBackgroundFeedUpdatesUseCase, Mockito.times(1)).execute();
        Mockito.verify(view, Mockito.times(1)).setIsBackgroundFeedUpdateEnabled(false);
    }
}