package oxim.digital.reedly.domain.interactor.feed.update;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import oxim.digital.reedly.domain.repository.FeedRepository;
import rx.Single;
import rx.observers.TestSubscriber;

public final class ShouldUpdateFeedsInBackgroundUseCaseTest {

    private ShouldUpdateFeedsInBackgroundUseCase shouldUpdateFeedsInBackgroundUseCase;
    private FeedRepository feedRepository;
    private TestSubscriber<Boolean> testSubscriber;

    @Before
    public void setUp() throws Exception {
        feedRepository = Mockito.mock(FeedRepository.class);
        shouldUpdateFeedsInBackgroundUseCase = new ShouldUpdateFeedsInBackgroundUseCase(feedRepository);
        testSubscriber = new TestSubscriber<>();
    }

    @Test
    public void shouldReturnInfoIfFeedsShouldUpdateInBackground() throws Exception {
        Mockito.when(feedRepository.shouldUpdateFeedsInBackground()).thenReturn(Single.just(true));

        shouldUpdateFeedsInBackgroundUseCase.execute().subscribe(testSubscriber);

        Mockito.verify(feedRepository, Mockito.times(1)).shouldUpdateFeedsInBackground();
        Mockito.verifyNoMoreInteractions(feedRepository);

        testSubscriber.assertCompleted();
        testSubscriber.assertValue(true);
    }

    @Test
    public void shouldReturnInfoIfFeedsShouldNotUpdateInBackground() throws Exception {
        Mockito.when(feedRepository.shouldUpdateFeedsInBackground()).thenReturn(Single.just(false));

        shouldUpdateFeedsInBackgroundUseCase.execute().subscribe(testSubscriber);

        Mockito.verify(feedRepository, Mockito.times(1)).shouldUpdateFeedsInBackground();
        Mockito.verifyNoMoreInteractions(feedRepository);

        testSubscriber.assertCompleted();
        testSubscriber.assertValue(false);
    }
}