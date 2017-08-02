package oxim.digital.reedly.domain.interactor.feed;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import oxim.digital.reedly.domain.model.Feed;
import oxim.digital.reedly.domain.repository.FeedRepository;
import rx.Single;
import rx.observers.TestSubscriber;

public final class GetUserFeedsUseCaseTest {

    private GetUserFeedsUseCase getUserFeedsUseCase;
    private FeedRepository feedRepository;

    @Before
    public void setUp() throws Exception {
        feedRepository = Mockito.mock(FeedRepository.class);
        getUserFeedsUseCase = new GetUserFeedsUseCase(feedRepository);
    }

    @Test
    public void execute() throws Exception {
        Mockito.when(feedRepository.getUserFeeds()).thenReturn(Single.just(new ArrayList<>()));

        final TestSubscriber<List<Feed>> testSubscriber = new TestSubscriber<>();
        getUserFeedsUseCase.execute().subscribe(testSubscriber);

        Mockito.verify(feedRepository, Mockito.times(1)).getUserFeeds();
        Mockito.verifyNoMoreInteractions(feedRepository);

        testSubscriber.assertCompleted();
        testSubscriber.assertValueCount(1);
    }
}