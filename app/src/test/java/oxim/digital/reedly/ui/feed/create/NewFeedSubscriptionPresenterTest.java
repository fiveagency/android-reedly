package oxim.digital.reedly.ui.feed.create;

import android.content.res.Resources;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import oxim.digital.reedly.AppTestData;
import oxim.digital.reedly.MockViewActionQueue;
import oxim.digital.reedly.configuration.ViewActionQueueProvider;
import oxim.digital.reedly.data.util.connectivity.ConnectivityReceiver;
import oxim.digital.reedly.domain.interactor.feed.AddNewFeedUseCase;
import oxim.digital.reedly.ui.router.Router;
import rx.Completable;
import rx.Observable;
import rx.Scheduler;
import rx.Single;
import rx.schedulers.Schedulers;

public final class NewFeedSubscriptionPresenterTest {

    @Mock
    AddNewFeedUseCase addNewFeedUseCase;

    @Mock
    Resources resources;

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

    @InjectMocks
    NewFeedSubscriptionPresenter newFeedSubscriptionPresenter;

    private NewFeedSubscriptionContract.View view;

    @Before
    public void setUp() throws Exception {
        view = Mockito.mock(NewFeedSubscriptionContract.View.class);
        newFeedSubscriptionPresenter = new NewFeedSubscriptionPresenter(view);
        MockitoAnnotations.initMocks(this);

        Mockito.when(connectivityReceiver.getConnectivityStatus()).thenReturn(Observable.just(true));
        Mockito.when(viewActionQueueProvider.queueFor(Mockito.any())).thenReturn(new MockViewActionQueue<NewFeedSubscriptionContract.View>());

        newFeedSubscriptionPresenter.start();
        newFeedSubscriptionPresenter.activate();
    }

    @Test
    public void shouldAddNewFeedIfInternetIsAvailable() throws Exception {
        final String feedUrl = AppTestData.TEST_LINK;

        Mockito.when(connectivityReceiver.isConnected()).thenReturn(Single.just(true));
        Mockito.when(addNewFeedUseCase.execute(feedUrl)).thenReturn(Completable.complete());

        newFeedSubscriptionPresenter.addNewFeed(feedUrl);
        Mockito.verify(addNewFeedUseCase, Mockito.times(1)).execute(feedUrl);
        Mockito.verifyNoMoreInteractions(addNewFeedUseCase);
    }

    @Test
    public void shouldShowErrorWhenNoInternetWhileAddingNewFeed() throws Exception {
        final String feedUrl = AppTestData.TEST_LINK;

        Mockito.when(connectivityReceiver.isConnected()).thenReturn(Single.just(false));

        newFeedSubscriptionPresenter.addNewFeed(feedUrl);

        Mockito.verify(addNewFeedUseCase, Mockito.never()).execute(feedUrl);
        Mockito.verify(view, Mockito.times(1)).showMessage(Mockito.any());
    }

    @Test
    public void shouldGoBackToArticlesList() throws Exception {
        newFeedSubscriptionPresenter.back();
        Mockito.verify(router, Mockito.times(1)).hideAddNewFeedScreen();
        Mockito.verifyNoMoreInteractions(router);
    }
}