package oxim.digital.reedly.domain.interactor.feed;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import oxim.digital.reedly.domain.interactor.DomainTestData;
import oxim.digital.reedly.domain.repository.FeedRepository;
import rx.Single;
import rx.observers.TestSubscriber;

public final class IsUserSubscribedToFeedUseCaseTest {

    private IsUserSubscribedToFeedUseCase isUserSubscribedToFeedUseCase;
    private FeedRepository feedRepository;
    private TestSubscriber<Boolean> testSubscriber;

    @Before
    public void setUp() throws Exception {
        feedRepository = Mockito.mock(FeedRepository.class);
        isUserSubscribedToFeedUseCase = new IsUserSubscribedToFeedUseCase(feedRepository);
        testSubscriber = new TestSubscriber<>();
    }

    @Test
    public void shouldReturnIfUserIsSubscribedToFeed() throws Exception {
        Mockito.when(feedRepository.feedExists(DomainTestData.TEST_URL_STRING)).thenReturn(Single.just(true));

        isUserSubscribedToFeedUseCase.execute(DomainTestData.TEST_URL_STRING).subscribe(testSubscriber);

        Mockito.verify(feedRepository, Mockito.times(1)).feedExists(DomainTestData.TEST_URL_STRING);
        Mockito.verifyNoMoreInteractions(feedRepository);

        testSubscriber.assertCompleted();
        testSubscriber.assertValue(true);
    }

    @Test
    public void shouldReturnIfUserIsNotSubscribedToFeed() throws Exception {
        Mockito.when(feedRepository.feedExists(DomainTestData.TEST_URL_STRING)).thenReturn(Single.just(false));

        isUserSubscribedToFeedUseCase.execute(DomainTestData.TEST_URL_STRING).subscribe(testSubscriber);

        Mockito.verify(feedRepository, Mockito.times(1)).feedExists(DomainTestData.TEST_URL_STRING);
        Mockito.verifyNoMoreInteractions(feedRepository);

        testSubscriber.assertCompleted();
        testSubscriber.assertValue(false);
    }
}