package oxim.digital.reedly.domain.interactor.feed.update;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.io.IOException;

import oxim.digital.reedly.domain.repository.FeedRepository;
import rx.Completable;
import rx.observers.TestSubscriber;

public final class SetShouldUpdateFeedsInBackgroundUseCaseImplTest {

    private SetShouldUpdateFeedsInBackgroundUseCase setShouldUpdateFeedsInBackgroundUseCase;
    private FeedRepository feedRepository;

    @Before
    public void setUp() throws Exception {
        feedRepository = Mockito.mock(FeedRepository.class);
        setShouldUpdateFeedsInBackgroundUseCase = new SetShouldUpdateFeedsInBackgroundUseCase(feedRepository);
    }

    @Test
    public void executeEnable() throws Exception {
        Mockito.when(feedRepository.setShouldUpdateFeedsInBackground(true)).thenReturn(Completable.complete());

        final TestSubscriber testSubscriber = new TestSubscriber();
        setShouldUpdateFeedsInBackgroundUseCase.execute(true).subscribe(testSubscriber);

        Mockito.verify(feedRepository, Mockito.times(1)).setShouldUpdateFeedsInBackground(true);
        Mockito.verifyNoMoreInteractions(feedRepository);

        testSubscriber.assertCompleted();
    }

    @Test
    public void executeDisable() throws Exception {
        Mockito.when(feedRepository.setShouldUpdateFeedsInBackground(false)).thenReturn(Completable.complete());

        final TestSubscriber testSubscriber = new TestSubscriber();
        setShouldUpdateFeedsInBackgroundUseCase.execute(false).subscribe(testSubscriber);

        Mockito.verify(feedRepository, Mockito.times(1)).setShouldUpdateFeedsInBackground(false);
        Mockito.verifyNoMoreInteractions(feedRepository);

        testSubscriber.assertCompleted();
    }

    @Test
    public void executeEnableWithErrorInRepository() throws Exception {
        Mockito.when(feedRepository.setShouldUpdateFeedsInBackground(true)).thenReturn(Completable.error(new IOException()));

        final TestSubscriber testSubscriber = new TestSubscriber();
        setShouldUpdateFeedsInBackgroundUseCase.execute(true).subscribe(testSubscriber);

        Mockito.verify(feedRepository, Mockito.times(1)).setShouldUpdateFeedsInBackground(true);
        Mockito.verifyNoMoreInteractions(feedRepository);

        testSubscriber.assertNotCompleted();
        testSubscriber.assertError(IOException.class);
    }
}