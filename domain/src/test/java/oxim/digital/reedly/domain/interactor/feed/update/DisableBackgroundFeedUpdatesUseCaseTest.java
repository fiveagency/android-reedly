package oxim.digital.reedly.domain.interactor.feed.update;

import org.mockito.Mockito;

import oxim.digital.reedly.domain.update.FeedsUpdateScheduler;
import rx.Completable;

public final class DisableBackgroundFeedUpdatesUseCaseTest {

    private DisableBackgroundFeedUpdatesUseCase disableBackgroundFeedUpdatesUseCase;

    private SetShouldUpdateFeedsInBackgroundUseCase setShouldUpdateFeedsInBackgroundUseCase;
    private FeedsUpdateScheduler feedUpdateScheduler;

    @org.junit.Before
    public void setUp() throws Exception {
        setShouldUpdateFeedsInBackgroundUseCase = Mockito.mock(SetShouldUpdateFeedsInBackgroundUseCase.class);
        feedUpdateScheduler = Mockito.mock(FeedsUpdateScheduler.class);

        disableBackgroundFeedUpdatesUseCase = new DisableBackgroundFeedUpdatesUseCase(setShouldUpdateFeedsInBackgroundUseCase, feedUpdateScheduler);
    }

    @org.junit.Test
    public void execute() throws Exception {
        Mockito.when(setShouldUpdateFeedsInBackgroundUseCase.execute(false)).thenReturn(Completable.complete());

        disableBackgroundFeedUpdatesUseCase.execute().subscribe();

        Mockito.verify(setShouldUpdateFeedsInBackgroundUseCase, Mockito.times(1)).execute(false);
        Mockito.verify(setShouldUpdateFeedsInBackgroundUseCase, Mockito.never()).execute(true);

        Mockito.verify(feedUpdateScheduler, Mockito.times(1)).cancelBackgroundFeedUpdates();
        Mockito.verify(feedUpdateScheduler, Mockito.never()).scheduleBackgroundFeedUpdates();
    }
}