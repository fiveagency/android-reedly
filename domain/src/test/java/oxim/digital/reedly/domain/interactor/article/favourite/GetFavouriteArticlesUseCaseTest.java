package oxim.digital.reedly.domain.interactor.article.favourite;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import oxim.digital.reedly.domain.interactor.DomainTestData;
import oxim.digital.reedly.domain.model.Article;
import oxim.digital.reedly.domain.repository.FeedRepository;
import rx.Single;
import rx.observers.TestSubscriber;

public final class GetFavouriteArticlesUseCaseTest {

    private GetFavouriteArticlesUseCase getFavouriteArticlesUseCase;
    private FeedRepository feedRepository;

    @Before
    public void setUp() throws Exception {
        feedRepository = Mockito.mock(FeedRepository.class);
        getFavouriteArticlesUseCase = new GetFavouriteArticlesUseCase(feedRepository);
    }

    @Test
    public void execute() throws Exception {
        final List<Article> articleList = new ArrayList<>(3);
        articleList.add(DomainTestData.TEST_NEW_FAVOURITE_ARTICLE);
        articleList.add(DomainTestData.TEST_NEW_FAVOURITE_ARTICLE);
        articleList.add(DomainTestData.TEST_NEW_FAVOURITE_ARTICLE);

        Mockito.when(feedRepository.getFavouriteArticles()).thenReturn(Single.just(articleList));

        final TestSubscriber<List<Article>> testSubscriber = new TestSubscriber<>();
        getFavouriteArticlesUseCase.execute().subscribe(testSubscriber);

        Mockito.verify(feedRepository, Mockito.times(1)).getFavouriteArticles();
        Mockito.verifyNoMoreInteractions(feedRepository);

        testSubscriber.assertValue(articleList);
        testSubscriber.assertValueCount(1);
        testSubscriber.assertCompleted();
    }
}