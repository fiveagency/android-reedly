package oxim.digital.reedly.domain.interactor.feed.update;

import org.mockito.Mockito;

import oxim.digital.reedly.domain.repository.FeedRepository;
import rx.Completable;

public final class SetShouldUpdateFeedsInBackgroundUseCaseImplTest {

    private SetShouldUpdateFeedsInBackgroundUseCase setShouldUpdateFeedsInBackgroundUseCase;
    private FeedRepository feedRepository;

    @org.junit.Before
    public void setUp() throws Exception {
        feedRepository = Mockito.mock(FeedRepository.class);
        setShouldUpdateFeedsInBackgroundUseCase = new SetShouldUpdateFeedsInBackgroundUseCaseImpl(feedRepository);
    }

    @org.junit.Test
    public void executeEnable() throws Exception {
        Mockito.when(feedRepository.setShouldUpdateFeedsInBackground(Mockito.anyBoolean())).thenReturn(Completable.complete());

        setShouldUpdateFeedsInBackgroundUseCase.execute(true).subscribe();

        Mockito.verify(feedRepository, Mockito.times(1)).setShouldUpdateFeedsInBackground(true);
    }

    @org.junit.Test
    public void executeDisable() throws Exception {
        Mockito.when(feedRepository.setShouldUpdateFeedsInBackground(Mockito.anyBoolean())).thenReturn(Completable.complete());

        setShouldUpdateFeedsInBackgroundUseCase.execute(false).subscribe();

        Mockito.verify(feedRepository, Mockito.times(1)).setShouldUpdateFeedsInBackground(false);
    }
}