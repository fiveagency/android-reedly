package oxim.digital.reedly.domain.interactor.feed.update;

import org.mockito.Mockito;

import oxim.digital.reedly.domain.update.FeedsUpdateScheduler;
import rx.Completable;

public final class EnableBackgroundFeedUpdatesUseCaseTest {

    private EnableBackgroundFeedUpdatesUseCase enableBackgroundFeedUpdatesUseCase;

    private SetShouldUpdateFeedsInBackgroundUseCase setShouldUpdateFeedsInBackgroundUseCase;
    private FeedsUpdateScheduler feedUpdateScheduler;

    @org.junit.Before
    public void setUp() throws Exception {
        setShouldUpdateFeedsInBackgroundUseCase = Mockito.mock(SetShouldUpdateFeedsInBackgroundUseCase.class);
        feedUpdateScheduler = Mockito.mock(FeedsUpdateScheduler.class);

        enableBackgroundFeedUpdatesUseCase = new EnableBackgroundFeedUpdatesUseCase(setShouldUpdateFeedsInBackgroundUseCase, feedUpdateScheduler);
    }

    @org.junit.Test
    public void execute() throws Exception {
        Mockito.when(setShouldUpdateFeedsInBackgroundUseCase.execute(true)).thenReturn(Completable.complete());

        enableBackgroundFeedUpdatesUseCase.execute().subscribe();

        Mockito.verify(setShouldUpdateFeedsInBackgroundUseCase, Mockito.times(1)).execute(true);
        Mockito.verify(setShouldUpdateFeedsInBackgroundUseCase, Mockito.never()).execute(false);

        Mockito.verify(feedUpdateScheduler, Mockito.times(1)).scheduleBackgroundFeedUpdates();
        Mockito.verify(feedUpdateScheduler, Mockito.never()).cancelBackgroundFeedUpdates();
    }
}