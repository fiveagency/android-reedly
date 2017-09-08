package oxim.digital.reedly.domain.interactor.article;

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

public final class GetArticlesUseCaseTest {

    private GetArticlesUseCase getArticlesUseCase;
    private FeedRepository feedRepository;
    private TestSubscriber<List<Article>> testSubscriber;

    @Before
    public void setUp() throws Exception {
        feedRepository = Mockito.mock(FeedRepository.class);
        getArticlesUseCase = new GetArticlesUseCase(feedRepository);
        testSubscriber = new TestSubscriber<>();
    }

    @Test
    public void shouldReturnUsersListOfArticles() throws Exception {
        final List<Article> articles = new ArrayList<>(1);
        articles.add(DomainTestData.TEST_NEW_FAVOURITE_ARTICLE);

        Mockito.when(feedRepository.getArticles(DomainTestData.TEST_INTEGER_ID_1)).thenReturn(Single.just(articles));

        getArticlesUseCase.execute(DomainTestData.TEST_INTEGER_ID_1).subscribe(testSubscriber);

        Mockito.verify(feedRepository, Mockito.times(1)).getArticles(DomainTestData.TEST_INTEGER_ID_1);
        Mockito.verifyNoMoreInteractions(feedRepository);

        testSubscriber.assertCompleted();
        testSubscriber.assertValue(articles);
    }

    @Test
    public void shouldReturnEmptyListIfUsersHasNoArticles() throws Exception {
        final List<Article> articles = new ArrayList<>(0);

        Mockito.when(feedRepository.getArticles(DomainTestData.TEST_INTEGER_ID_1)).thenReturn(Single.just(articles));

        getArticlesUseCase.execute(DomainTestData.TEST_INTEGER_ID_1).subscribe(testSubscriber);

        Mockito.verify(feedRepository, Mockito.times(1)).getArticles(DomainTestData.TEST_INTEGER_ID_1);
        Mockito.verifyNoMoreInteractions(feedRepository);

        testSubscriber.assertCompleted();
        testSubscriber.assertValue(articles);
    }
}