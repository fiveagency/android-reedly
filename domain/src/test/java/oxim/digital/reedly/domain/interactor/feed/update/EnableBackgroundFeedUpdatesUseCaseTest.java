package oxim.digital.reedly.domain.interactor.feed.update;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.IOException;

import oxim.digital.reedly.domain.update.FeedsUpdateScheduler;
import rx.Completable;
import rx.observers.TestSubscriber;

public final class EnableBackgroundFeedUpdatesUseCaseTest {

    private EnableBackgroundFeedUpdatesUseCase enableBackgroundFeedUpdatesUseCase;

    private SetShouldUpdateFeedsInBackgroundUseCase setShouldUpdateFeedsInBackgroundUseCaseMock;
    private FeedsUpdateScheduler feedUpdateScheduler;
    private TestSubscriber testSubscriber;

    @Before
    public void setUp() throws Exception {
        setShouldUpdateFeedsInBackgroundUseCaseMock = Mockito.mock(SetShouldUpdateFeedsInBackgroundUseCase.class);
        feedUpdateScheduler = Mockito.mock(FeedsUpdateScheduler.class);
        testSubscriber = new TestSubscriber();

        enableBackgroundFeedUpdatesUseCase = new EnableBackgroundFeedUpdatesUseCase(setShouldUpdateFeedsInBackgroundUseCaseMock, feedUpdateScheduler);
    }

    @Test
    public void shouldEnableBackgroundFeedUpdates() throws Exception {
        Mockito.when(setShouldUpdateFeedsInBackgroundUseCaseMock.execute(true)).thenReturn(Completable.complete());

        enableBackgroundFeedUpdatesUseCase.execute().subscribe(testSubscriber);

        Mockito.verify(setShouldUpdateFeedsInBackgroundUseCaseMock, Mockito.times(1)).execute(true);
        Mockito.verifyNoMoreInteractions(setShouldUpdateFeedsInBackgroundUseCaseMock);

        Mockito.verify(feedUpdateScheduler, Mockito.times(1)).scheduleBackgroundFeedUpdates();
        Mockito.verifyNoMoreInteractions(feedUpdateScheduler);

        testSubscriber.assertCompleted();
    }
}