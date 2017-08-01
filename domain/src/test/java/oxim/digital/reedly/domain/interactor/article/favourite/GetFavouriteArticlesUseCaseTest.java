package oxim.digital.reedly.domain.interactor.article.favourite;

import org.mockito.Mockito;

import java.util.ArrayList;

import oxim.digital.reedly.domain.repository.FeedRepository;
import rx.Single;

public final class GetFavouriteArticlesUseCaseTest {

    private GetFavouriteArticlesUseCase getFavouriteArticlesUseCase;
    private FeedRepository feedRepository;

    @org.junit.Before
    public void setUp() throws Exception {
        feedRepository = Mockito.mock(FeedRepository.class);
        getFavouriteArticlesUseCase = new GetFavouriteArticlesUseCase(feedRepository);
    }

    @org.junit.Test
    public void execute() throws Exception {
        Mockito.when(feedRepository.getFavouriteArticles()).thenReturn(Single.just(new ArrayList<>()));

        getFavouriteArticlesUseCase.execute().subscribe();

        Mockito.verify(feedRepository, Mockito.times(1)).getFavouriteArticles();
    }
}