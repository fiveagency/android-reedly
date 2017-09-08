package oxim.digital.reedly.domain.interactor.feed.update;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.IOException;

import oxim.digital.reedly.domain.update.FeedsUpdateScheduler;
import rx.Completable;
import rx.observers.TestSubscriber;

public final class DisableBackgroundFeedUpdatesUseCaseTest {

    private DisableBackgroundFeedUpdatesUseCase disableBackgroundFeedUpdatesUseCase;

    private SetShouldUpdateFeedsInBackgroundUseCase setShouldUpdateFeedsInBackgroundUseCaseMock;
    private FeedsUpdateScheduler feedUpdateScheduler;
    private TestSubscriber testSubscriber;

    @Before
    public void setUp() throws Exception {
        setShouldUpdateFeedsInBackgroundUseCaseMock = Mockito.mock(SetShouldUpdateFeedsInBackgroundUseCase.class);
        feedUpdateScheduler = Mockito.mock(FeedsUpdateScheduler.class);
        testSubscriber = new TestSubscriber();

        disableBackgroundFeedUpdatesUseCase = new DisableBackgroundFeedUpdatesUseCase(setShouldUpdateFeedsInBackgroundUseCaseMock, feedUpdateScheduler);
    }

    @Test
    public void shouldDisableBackgroundFeedUpdates() throws Exception {
        Mockito.when(setShouldUpdateFeedsInBackgroundUseCaseMock.execute(false)).thenReturn(Completable.complete());

        disableBackgroundFeedUpdatesUseCase.execute().subscribe(testSubscriber);

        Mockito.verify(setShouldUpdateFeedsInBackgroundUseCaseMock, Mockito.times(1)).execute(false);
        Mockito.verifyNoMoreInteractions(setShouldUpdateFeedsInBackgroundUseCaseMock);

        Mockito.verify(feedUpdateScheduler, Mockito.times(1)).cancelBackgroundFeedUpdates();
        Mockito.verifyNoMoreInteractions(feedUpdateScheduler);

        testSubscriber.assertCompleted();
    }
}