package oxim.digital.reedly.domain.interactor.article.favourite;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.IOException;

import oxim.digital.reedly.domain.interactor.DomainTestData;
import oxim.digital.reedly.domain.repository.FeedRepository;
import rx.Completable;
import rx.observers.TestSubscriber;

public final class FavouriteArticleUseCaseTest {

    private FavouriteArticleUseCase favouriteArticleUseCase;
    private FeedRepository feedRepository;

    @Before
    public void setUp() throws Exception {
        feedRepository = Mockito.mock(FeedRepository.class);
        favouriteArticleUseCase = new FavouriteArticleUseCase(feedRepository);
    }

    @Test
    public void executeWithoutErrorsInDependencies() throws Exception {
        Mockito.when(feedRepository.favouriteArticle(DomainTestData.TEST_INTEGER)).thenReturn(Completable.complete());

        final TestSubscriber<Object> testSubscriber = new TestSubscriber<>();

        favouriteArticleUseCase.execute(DomainTestData.TEST_INTEGER).subscribe(testSubscriber);

        Mockito.verify(feedRepository, Mockito.times(1)).favouriteArticle(DomainTestData.TEST_INTEGER);
        Mockito.verifyNoMoreInteractions(feedRepository);

        testSubscriber.assertCompleted();
    }

    @Test
    public void executeFavouriteWithErrorInRepository() throws Exception {
        Mockito.when(feedRepository.favouriteArticle(DomainTestData.TEST_INTEGER)).thenReturn(Completable.error(new IOException()));

        final TestSubscriber<Object> testSubscriber = new TestSubscriber<>();

        favouriteArticleUseCase.execute(DomainTestData.TEST_INTEGER).subscribe(testSubscriber);

        Mockito.verify(feedRepository, Mockito.times(1)).favouriteArticle(DomainTestData.TEST_INTEGER);
        Mockito.verifyNoMoreInteractions(feedRepository);

        testSubscriber.assertNotCompleted();
        testSubscriber.assertError(IOException.class);
    }
}