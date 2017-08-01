package oxim.digital.reedly.domain.interactor.feed;

import org.mockito.Mockito;

import oxim.digital.reedly.domain.repository.FeedRepository;
import rx.Single;

public final class IsUserSubscribedToFeedUseCaseTest {

    private IsUserSubscribedToFeedUseCase isUserSubscribedToFeedUseCase;
    private FeedRepository feedRepository;

    @org.junit.Before
    public void setUp() throws Exception {
        feedRepository = Mockito.mock(FeedRepository.class);
        isUserSubscribedToFeedUseCase = new IsUserSubscribedToFeedUseCase(feedRepository);
    }

    @org.junit.Test
    public void execute() throws Exception {
        Mockito.when(feedRepository.feedExists(Mockito.anyString())).thenReturn(Single.just(true));

        isUserSubscribedToFeedUseCase.execute("aaa").subscribe();

        Mockito.verify(feedRepository, Mockito.times(1)).feedExists("aaa");
    }
}