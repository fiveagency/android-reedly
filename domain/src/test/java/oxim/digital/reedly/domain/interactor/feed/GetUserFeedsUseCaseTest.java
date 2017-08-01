package oxim.digital.reedly.domain.interactor.feed;

import org.mockito.Mockito;

import java.util.ArrayList;

import oxim.digital.reedly.domain.repository.FeedRepository;
import rx.Single;

public final class GetUserFeedsUseCaseTest {

    private GetUserFeedsUseCase getUserFeedsUseCase;
    private FeedRepository feedRepository;

    @org.junit.Before
    public void setUp() throws Exception {
        feedRepository = Mockito.mock(FeedRepository.class);
        getUserFeedsUseCase = new GetUserFeedsUseCase(feedRepository);
    }

    @org.junit.Test
    public void execute() throws Exception {
        Mockito.when(feedRepository.getUserFeeds()).thenReturn(Single.just(new ArrayList<>()));

        getUserFeedsUseCase.execute().subscribe();

        Mockito.verify(feedRepository, Mockito.times(1)).getUserFeeds();
    }
}