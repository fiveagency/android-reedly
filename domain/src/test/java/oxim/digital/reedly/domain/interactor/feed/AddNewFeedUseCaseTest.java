package oxim.digital.reedly.domain.interactor.feed;

import org.mockito.Mockito;

import java.io.IOException;

import oxim.digital.reedly.domain.repository.FeedRepository;
import rx.Completable;
import rx.functions.Action1;

public final class AddNewFeedUseCaseTest {

    private AddNewFeedUseCase addNewFeedUseCase;
    private FeedRepository feedRepository;

    @org.junit.Before
    public void setUp() throws Exception {
        feedRepository = Mockito.mock(FeedRepository.class);
        addNewFeedUseCase = new AddNewFeedUseCase(feedRepository);
    }

    @org.junit.Test
    public void executeSuccessfully() throws Exception {
        Mockito.when(feedRepository.createNewFeed(Mockito.any())).thenReturn(Completable.complete());

        addNewFeedUseCase.execute("www.google.com").subscribe();

        Mockito.verify(feedRepository, Mockito.times(1)).createNewFeed("www.google.com");
    }

    @org.junit.Test
    public void executeWithError() throws Exception {
        Mockito.when(feedRepository.createNewFeed(Mockito.any())).thenReturn(Completable.error(new IOException()));

        final Action1<Throwable> mockThrowableAction1 = Mockito.mock(Action1.class);
        addNewFeedUseCase.execute("www.google.com").subscribe(() -> {}, mockThrowableAction1);

        Mockito.verify(feedRepository, Mockito.times(1)).createNewFeed("www.google.com");
        Mockito.verify(mockThrowableAction1, Mockito.times(1)).call(Mockito.any(Throwable.class));
    }
}