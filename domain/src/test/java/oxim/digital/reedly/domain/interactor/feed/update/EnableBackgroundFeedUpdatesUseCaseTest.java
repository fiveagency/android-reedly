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

    @Before
    public void setUp() throws Exception {
        setShouldUpdateFeedsInBackgroundUseCaseMock = Mockito.mock(SetShouldUpdateFeedsInBackgroundUseCase.class);
        feedUpdateScheduler = Mockito.mock(FeedsUpdateScheduler.class);

        enableBackgroundFeedUpdatesUseCase = new EnableBackgroundFeedUpdatesUseCase(setShouldUpdateFeedsInBackgroundUseCaseMock, feedUpdateScheduler);
    }

    @Test
    public void executeWithErrorInSetShouldUpdateFeedsInBackgroundUseCase() throws Exception {
        Mockito.when(setShouldUpdateFeedsInBackgroundUseCaseMock.execute(true)).thenReturn(Completable.error(new IOException()));

        final TestSubscriber testSubscriber = new TestSubscriber();
        enableBackgroundFeedUpdatesUseCase.execute().subscribe(testSubscriber);

        Mockito.verify(setShouldUpdateFeedsInBackgroundUseCaseMock, Mockito.times(1)).execute(true);
        Mockito.verifyNoMoreInteractions(setShouldUpdateFeedsInBackgroundUseCaseMock);

        Mockito.verify(feedUpdateScheduler, Mockito.times(0)).scheduleBackgroundFeedUpdates();
        Mockito.verifyNoMoreInteractions(feedUpdateScheduler);

        testSubscriber.assertNotCompleted();
        testSubscriber.assertError(IOException.class);
    }

    @Test
    public void executeWithoutErrorsInDependencies() throws Exception {
        Mockito.when(setShouldUpdateFeedsInBackgroundUseCaseMock.execute(true)).thenReturn(Completable.complete());

        final TestSubscriber testSubscriber = new TestSubscriber();
        enableBackgroundFeedUpdatesUseCase.execute().subscribe(testSubscriber);

        Mockito.verify(setShouldUpdateFeedsInBackgroundUseCaseMock, Mockito.times(1)).execute(true);
        Mockito.verifyNoMoreInteractions(setShouldUpdateFeedsInBackgroundUseCaseMock);

        Mockito.verify(feedUpdateScheduler, Mockito.times(1)).scheduleBackgroundFeedUpdates();
        Mockito.verifyNoMoreInteractions(feedUpdateScheduler);

        testSubscriber.assertCompleted();
    }
}