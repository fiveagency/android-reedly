package oxim.digital.reedly.domain.interactor.article;

import org.mockito.Mockito;

import java.util.ArrayList;

import oxim.digital.reedly.domain.repository.FeedRepository;
import rx.Single;

public final class GetArticlesUseCaseTest {

    private GetArticlesUseCase getArticlesUseCase;
    private FeedRepository feedRepository;

    @org.junit.Before
    public void setUp() throws Exception {
        feedRepository = Mockito.mock(FeedRepository.class);
        getArticlesUseCase = new GetArticlesUseCase(feedRepository);
    }

    @org.junit.Test
    public void execute() throws Exception {
        Mockito.when(feedRepository.getArticles(Mockito.anyInt())).thenReturn(Single.just(new ArrayList<>()));

        getArticlesUseCase.execute(101).subscribe();

        Mockito.verify(feedRepository, Mockito.times(1)).getArticles(101);
    }
}