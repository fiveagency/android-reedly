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

    @Before
    public void setUp() throws Exception {
        feedRepository = Mockito.mock(FeedRepository.class);
        shouldUpdateFeedsInBackgroundUseCase = new ShouldUpdateFeedsInBackgroundUseCase(feedRepository);
    }

    @Test
    public void executeShouldUpdate() throws Exception {
        Mockito.when(feedRepository.shouldUpdateFeedsInBackground()).thenReturn(Single.just(true));

        final TestSubscriber<Boolean> testSubscriber = new TestSubscriber<>();
        shouldUpdateFeedsInBackgroundUseCase.execute().subscribe(testSubscriber);

        Mockito.verify(feedRepository, Mockito.times(1)).shouldUpdateFeedsInBackground();
        Mockito.verifyNoMoreInteractions(feedRepository);

        testSubscriber.assertCompleted();
        testSubscriber.assertValue(true);
    }

    @Test
    public void executeShouldNotUpdate() throws Exception {
        Mockito.when(feedRepository.shouldUpdateFeedsInBackground()).thenReturn(Single.just(false));

        final TestSubscriber<Boolean> testSubscriber = new TestSubscriber<>();
        shouldUpdateFeedsInBackgroundUseCase.execute().subscribe(testSubscriber);

        Mockito.verify(feedRepository, Mockito.times(1)).shouldUpdateFeedsInBackground();
        Mockito.verifyNoMoreInteractions(feedRepository);

        testSubscriber.assertCompleted();
        testSubscriber.assertValue(false);
    }
}