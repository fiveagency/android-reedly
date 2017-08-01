package oxim.digital.reedly.domain.interactor.feed.update;

import org.mockito.Mockito;

import oxim.digital.reedly.domain.repository.FeedRepository;
import rx.Single;

public final class ShouldUpdateFeedsInBackgroundUseCaseTest {

    private ShouldUpdateFeedsInBackgroundUseCase shouldUpdateFeedsInBackgroundUseCase;
    private FeedRepository feedRepository;

    @org.junit.Before
    public void setUp() throws Exception {
        feedRepository = Mockito.mock(FeedRepository.class);

        shouldUpdateFeedsInBackgroundUseCase = new ShouldUpdateFeedsInBackgroundUseCase(feedRepository);
    }

    @org.junit.Test
    public void execute() throws Exception {
        Mockito.when(feedRepository.shouldUpdateFeedsInBackground()).thenReturn(Single.just(null));

        shouldUpdateFeedsInBackgroundUseCase.execute().subscribe();

        Mockito.verify(feedRepository, Mockito.times(1)).shouldUpdateFeedsInBackground();
    }
}