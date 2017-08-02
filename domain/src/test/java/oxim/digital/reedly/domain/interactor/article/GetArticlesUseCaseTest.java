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

    @Before
    public void setUp() throws Exception {
        feedRepository = Mockito.mock(FeedRepository.class);
        getArticlesUseCase = new GetArticlesUseCase(feedRepository);
    }

    @Test
    public void execute() throws Exception {
        Mockito.when(feedRepository.getArticles(DomainTestData.TEST_INTEGER)).thenReturn(Single.just(new ArrayList<>()));

        final TestSubscriber<List<Article>> testSubscriber = new TestSubscriber<>();
        getArticlesUseCase.execute(DomainTestData.TEST_INTEGER).subscribe(testSubscriber);

        Mockito.verify(feedRepository, Mockito.times(1)).getArticles(DomainTestData.TEST_INTEGER);
        Mockito.verifyNoMoreInteractions(feedRepository);

        testSubscriber.assertCompleted();
    }
}