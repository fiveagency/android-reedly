package oxim.digital.reedly.domain.interactor.article;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import oxim.digital.reedly.domain.interactor.DomainTestData;
import oxim.digital.reedly.domain.repository.FeedRepository;
import rx.Single;
import rx.observers.TestSubscriber;

public final class GetUnreadArticlesCountUseCaseTest {

    private GetUnreadArticlesCountUseCase getUnreadArticlesCountUseCase;
    private FeedRepository feedRepository;
    private TestSubscriber<Long> testSubscriber;

    @Before
    public void setUp() throws Exception {
        feedRepository = Mockito.mock(FeedRepository.class);
        getUnreadArticlesCountUseCase = new GetUnreadArticlesCountUseCase(feedRepository);
        testSubscriber = new TestSubscriber<>();
    }

    @Test
    public void shouldReturnNumberOfUnreadArticlesForUser() throws Exception {
        Mockito.when(feedRepository.getUnreadArticlesCount()).thenReturn(Single.just(DomainTestData.TEST_LONG));

        getUnreadArticlesCountUseCase.execute().subscribe(testSubscriber);

        Mockito.verify(feedRepository, Mockito.times(1)).getUnreadArticlesCount();
        Mockito.verifyNoMoreInteractions(feedRepository);

        testSubscriber.assertValue(DomainTestData.TEST_LONG);
        testSubscriber.assertCompleted();
    }


}