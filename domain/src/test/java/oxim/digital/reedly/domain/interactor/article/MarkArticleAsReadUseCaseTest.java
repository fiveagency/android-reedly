package oxim.digital.reedly.domain.interactor.article;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.IOException;

import oxim.digital.reedly.domain.interactor.DomainTestData;
import oxim.digital.reedly.domain.repository.FeedRepository;
import rx.Completable;
import rx.observers.TestSubscriber;

public final class MarkArticleAsReadUseCaseTest {

    private MarkArticleAsReadUseCase markArticleAsReadUseCase;
    private FeedRepository feedRepository;
    private TestSubscriber<Object> testSubscriber;

    @Before
    public void setUp() throws Exception {
        feedRepository = Mockito.mock(FeedRepository.class);
        markArticleAsReadUseCase = new MarkArticleAsReadUseCase(feedRepository);
        testSubscriber = new TestSubscriber<>();
    }

    @Test
    public void executeMarkingAsCompletedSuccessful() throws Exception {
        Mockito.when(feedRepository.markArticleAsRead(DomainTestData.TEST_INTEGER)).thenReturn(Completable.complete());

        markArticleAsReadUseCase.execute(DomainTestData.TEST_INTEGER).subscribe(testSubscriber);

        Mockito.verify(feedRepository, Mockito.times(1)).markArticleAsRead(DomainTestData.TEST_INTEGER);
        Mockito.verifyNoMoreInteractions(feedRepository);

        testSubscriber.assertCompleted();
    }

    @Test
    public void executeMarkingAsCompletedError() throws Exception {
        Mockito.when(feedRepository.markArticleAsRead(DomainTestData.TEST_INTEGER)).thenReturn(Completable.error(new IOException()));

        markArticleAsReadUseCase.execute(DomainTestData.TEST_INTEGER).subscribe(testSubscriber);

        Mockito.verify(feedRepository, Mockito.times(1)).markArticleAsRead(DomainTestData.TEST_INTEGER);
        Mockito.verifyNoMoreInteractions(feedRepository);

        testSubscriber.assertNotCompleted();
        testSubscriber.assertError(IOException.class);
    }
}