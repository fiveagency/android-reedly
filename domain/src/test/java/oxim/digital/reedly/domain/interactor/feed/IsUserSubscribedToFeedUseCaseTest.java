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

    @Before
    public void setUp() throws Exception {
        feedRepository = Mockito.mock(FeedRepository.class);
        isUserSubscribedToFeedUseCase = new IsUserSubscribedToFeedUseCase(feedRepository);
    }

    @Test
    public void executeWhenUserIsSubscriber() throws Exception {
        Mockito.when(feedRepository.feedExists(DomainTestData.TEST_URL_STRING)).thenReturn(Single.just(true));

        final TestSubscriber<Boolean> testSubscriber = new TestSubscriber<>();
        isUserSubscribedToFeedUseCase.execute(DomainTestData.TEST_URL_STRING).subscribe(testSubscriber);

        Mockito.verify(feedRepository, Mockito.times(1)).feedExists(DomainTestData.TEST_URL_STRING);
        Mockito.verifyNoMoreInteractions(feedRepository);

        testSubscriber.assertCompleted();
        testSubscriber.assertValue(true);
    }

    @Test
    public void executeWhenUserIsNotSubscriber() throws Exception {
        Mockito.when(feedRepository.feedExists(DomainTestData.TEST_URL_STRING)).thenReturn(Single.just(false));

        final TestSubscriber<Boolean> testSubscriber = new TestSubscriber<>();
        isUserSubscribedToFeedUseCase.execute(DomainTestData.TEST_URL_STRING).subscribe(testSubscriber);

        Mockito.verify(feedRepository, Mockito.times(1)).feedExists(DomainTestData.TEST_URL_STRING);
        Mockito.verifyNoMoreInteractions(feedRepository);

        testSubscriber.assertCompleted();
        testSubscriber.assertValue(false);
    }
}