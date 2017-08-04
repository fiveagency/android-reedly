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
    private TestSubscriber<List<Article>> testSubscriber;

    @Before
    public void setUp() throws Exception {
        feedRepository = Mockito.mock(FeedRepository.class);
        getFavouriteArticlesUseCase = new GetFavouriteArticlesUseCase(feedRepository);
        testSubscriber = new TestSubscriber<>();
    }

    @Test
    public void execute() throws Exception {
        final List<Article> articleList = new ArrayList<>(3);
        articleList.add(DomainTestData.TEST_NEW_FAVOURITE_ARTICLE);
        articleList.add(DomainTestData.TEST_NEW_FAVOURITE_ARTICLE);
        articleList.add(DomainTestData.TEST_NEW_FAVOURITE_ARTICLE);

        Mockito.when(feedRepository.getFavouriteArticles()).thenReturn(Single.just(articleList));

        getFavouriteArticlesUseCase.execute().subscribe(testSubscriber);

        Mockito.verify(feedRepository, Mockito.times(1)).getFavouriteArticles();
        Mockito.verifyNoMoreInteractions(feedRepository);

        testSubscriber.assertCompleted();
        testSubscriber.assertValue(articleList);
    }
}