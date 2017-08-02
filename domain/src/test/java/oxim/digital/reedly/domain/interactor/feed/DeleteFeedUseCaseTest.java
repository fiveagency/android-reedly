package oxim.digital.reedly.domain.interactor.feed;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.sql.SQLException;

import oxim.digital.reedly.domain.interactor.DomainTestData;
import oxim.digital.reedly.domain.repository.FeedRepository;
import rx.Completable;
import rx.functions.Action1;
import rx.observers.TestSubscriber;

public final class DeleteFeedUseCaseTest {

    private DeleteFeedUseCase deleteFeedUseCase;
    private FeedRepository feedRepository;

    @Before
    public void setUp() throws Exception {
        feedRepository = Mockito.mock(FeedRepository.class);
        deleteFeedUseCase = new DeleteFeedUseCase(feedRepository);
    }

    @Test
    public void executeDeleteSuccessful() throws Exception {
        Mockito.when(feedRepository.deleteFeed(DomainTestData.TEST_INTEGER)).thenReturn(Completable.complete());

        final TestSubscriber<Object> testSubscriber = new TestSubscriber<>();
        deleteFeedUseCase.execute(DomainTestData.TEST_INTEGER).subscribe(testSubscriber);

        Mockito.verify(feedRepository, Mockito.times(1)).deleteFeed(DomainTestData.TEST_INTEGER);
        Mockito.verifyNoMoreInteractions(feedRepository);

        testSubscriber.assertCompleted();
    }

    @Test
    public void executeWithErrorInRepository() throws Exception {
        Mockito.when(feedRepository.deleteFeed(DomainTestData.TEST_INTEGER)).thenReturn(Completable.error(new IOException()));

        final TestSubscriber testSubscriber = new TestSubscriber();
        deleteFeedUseCase.execute(DomainTestData.TEST_INTEGER).subscribe(testSubscriber);

        Mockito.verify(feedRepository, Mockito.times(1)).deleteFeed(DomainTestData.TEST_INTEGER);
        Mockito.verifyNoMoreInteractions(feedRepository);

        testSubscriber.assertNotCompleted();
        testSubscriber.assertError(IOException.class);
    }
}