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
    private TestSubscriber<Object> testSubscriber;

    @Before
    public void setUp() throws Exception {
        feedRepository = Mockito.mock(FeedRepository.class);
        favouriteArticleUseCase = new FavouriteArticleUseCase(feedRepository);

        testSubscriber = new TestSubscriber<>();
    }

    @Test
    public void shouldMakeAnArticleFavourite() throws Exception {
        Mockito.when(feedRepository.favouriteArticle(DomainTestData.TEST_INTEGER_ID_1)).thenReturn(Completable.complete());

        favouriteArticleUseCase.execute(DomainTestData.TEST_INTEGER_ID_1).subscribe(testSubscriber);

        Mockito.verify(feedRepository, Mockito.times(1)).favouriteArticle(DomainTestData.TEST_INTEGER_ID_1);
        Mockito.verifyNoMoreInteractions(feedRepository);

        testSubscriber.assertCompleted();
    }


}