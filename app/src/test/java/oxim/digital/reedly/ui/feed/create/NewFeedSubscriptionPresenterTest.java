package oxim.digital.reedly.ui.feed.create;

import android.content.res.Resources;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import oxim.digital.reedly.MockViewActionHandler;
import oxim.digital.reedly.configuration.ViewActionQueueProvider;
import oxim.digital.reedly.data.util.connectivity.ConnectivityReceiver;
import oxim.digital.reedly.domain.interactor.feed.AddNewFeedUseCase;
import oxim.digital.reedly.ui.article.list.ArticlesContract;
import oxim.digital.reedly.ui.article.list.ArticlesPresenter;
import oxim.digital.reedly.ui.router.Router;
import rx.Scheduler;
import rx.schedulers.Schedulers;

import static org.junit.Assert.*;

public class NewFeedSubscriptionPresenterTest {

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
    MockViewActionHandler mockViewActionHandler;

    @InjectMocks
    NewFeedSubscriptionPresenter newFeedSubscriptionPresenter;

    private NewFeedSubscriptionContract.View view;

    @Before
    public void setUp() throws Exception {
        view = Mockito.mock(NewFeedSubscriptionContract.View.class);
        newFeedSubscriptionPresenter = new NewFeedSubscriptionPresenter(view);
        MockitoAnnotations.initMocks(this);
        newFeedSubscriptionPresenter.start();
        newFeedSubscriptionPresenter.activate();
    }

    @Test
    public void shouldAddNewFeedIfInternetIsAvailable() throws Exception {

    }

    @Test
    public void shouldShowErrorWhenNoInternetWhileAddingNewFeed() throws Exception {

    }

    @Test
    public void shouldGoBackToArticlesList() throws Exception {
        newFeedSubscriptionPresenter.back();
        Mockito.verify(router, Mockito.times(1)).goBack();
        Mockito.verifyNoMoreInteractions(router);
    }
}