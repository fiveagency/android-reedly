package oxim.digital.reedly.domain.interactor.feed;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.IOException;

import oxim.digital.reedly.domain.interactor.DomainTestData;
import oxim.digital.reedly.domain.repository.FeedRepository;
import rx.Completable;
import rx.observers.TestSubscriber;

public final class DeleteFeedUseCaseTest {

    private DeleteFeedUseCase deleteFeedUseCase;
    private FeedRepository feedRepository;
    private TestSubscriber<Object> testSubscriber;

    @Before
    public void setUp() throws Exception {
        feedRepository = Mockito.mock(FeedRepository.class);
        deleteFeedUseCase = new DeleteFeedUseCase(feedRepository);
        testSubscriber = new TestSubscriber<>();
    }

    @Test
    public void shouldDeleteExistingFeed() throws Exception {
        deleteFeedSuccessfully();
    }

    private void deleteFeedSuccessfully() {
        Mockito.when(feedRepository.deleteFeed(DomainTestData.TEST_INTEGER_ID_1)).thenReturn(Completable.complete());

        deleteFeedUseCase.execute(DomainTestData.TEST_INTEGER_ID_1).subscribe(testSubscriber);

        Mockito.verify(feedRepository, Mockito.times(1)).deleteFeed(DomainTestData.TEST_INTEGER_ID_1);
        Mockito.verifyNoMoreInteractions(feedRepository);

        testSubscriber.assertCompleted();
    }

    @Test
    public void shouldIgnoreDeletingNonExistingFeed() throws Exception {
        deleteFeedSuccessfully();
    }
}