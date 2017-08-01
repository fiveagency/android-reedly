package oxim.digital.reedly.domain.interactor.feed;

import org.mockito.Mockito;

import java.io.IOException;
import java.sql.SQLException;

import oxim.digital.reedly.domain.repository.FeedRepository;
import rx.Completable;
import rx.functions.Action1;

public final class DeleteFeedUseCaseTest {

    private DeleteFeedUseCase deleteFeedUseCase;
    private FeedRepository feedRepository;

    @org.junit.Before
    public void setUp() throws Exception {
        feedRepository = Mockito.mock(FeedRepository.class);
        deleteFeedUseCase = new DeleteFeedUseCase(feedRepository);
    }

    @org.junit.Test
    public void executeSuccessfully() throws Exception {
        Mockito.when(feedRepository.deleteFeed(Mockito.anyInt())).thenReturn(Completable.complete());

        deleteFeedUseCase.execute(3).subscribe();

        Mockito.verify(feedRepository, Mockito.times(1)).deleteFeed(3);
    }

    @org.junit.Test
    public void executeWithError() throws Exception {
        Mockito.when(feedRepository.deleteFeed(Mockito.anyInt())).thenReturn(Completable.error(new SQLException()));

        final Action1<Throwable> mockThrowableAction1 = Mockito.mock(Action1.class);
        deleteFeedUseCase.execute(3).subscribe(() -> {}, mockThrowableAction1);

        Mockito.verify(feedRepository, Mockito.times(1)).deleteFeed(3);
        Mockito.verify(mockThrowableAction1, Mockito.times(1)).call(Mockito.any(Throwable.class));
    }
}