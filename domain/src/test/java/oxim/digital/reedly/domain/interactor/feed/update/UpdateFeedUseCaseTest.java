package oxim.digital.reedly.domain.interactor.feed.update;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.IOException;

import oxim.digital.reedly.domain.interactor.DomainTestData;
import oxim.digital.reedly.domain.model.Feed;
import oxim.digital.reedly.domain.repository.FeedRepository;
import rx.Completable;
import rx.observers.TestSubscriber;

public final class UpdateFeedUseCaseTest {

    private UpdateFeedUseCase updateFeedUseCase;
    private FeedRepository feedRepository;
    private TestSubscriber<Object> testSubscriber;

    @Before
    public void setUp() throws Exception {
        feedRepository = Mockito.mock(FeedRepository.class);
        updateFeedUseCase = new UpdateFeedUseCase(feedRepository);
        testSubscriber = new TestSubscriber<>();
    }

    @Test
    public void executeUpdateSuccessfully() throws Exception {
        Mockito.when(feedRepository.updateArticles(Mockito.any())).thenReturn(Completable.complete());

        updateFeedUseCase.execute(DomainTestData.TEST_FEED).subscribe(testSubscriber);

        Mockito.verify(feedRepository, Mockito.times(1)).updateArticles(DomainTestData.TEST_FEED);
        Mockito.verifyNoMoreInteractions(feedRepository);

        testSubscriber.assertCompleted();
    }

    @Test
    public void executeUpdateWithErrorInRepository() throws Exception {
        Mockito.when(feedRepository.updateArticles(Mockito.any())).thenReturn(Completable.error(new IOException()));

        updateFeedUseCase.execute(DomainTestData.TEST_FEED).subscribe(testSubscriber);

        Mockito.verify(feedRepository, Mockito.times(1)).updateArticles(DomainTestData.TEST_FEED);
        Mockito.verifyNoMoreInteractions(feedRepository);

        testSubscriber.assertNotCompleted();
        testSubscriber.assertError(IOException.class);
    }
}