package oxim.digital.reedly.domain.interactor.article.favourite;

import org.mockito.Mockito;

import oxim.digital.reedly.domain.repository.FeedRepository;
import rx.Completable;

public final class UnFavouriteArticleUseCaseTest {

    private UnFavouriteArticleUseCase unFavouriteArticleUseCase;
    private FeedRepository feedRepository;

    @org.junit.Before
    public void setUp() throws Exception {
        feedRepository = Mockito.mock(FeedRepository.class);
        unFavouriteArticleUseCase = new UnFavouriteArticleUseCase(feedRepository);
    }

    @org.junit.Test
    public void execute() throws Exception {
        Mockito.when(feedRepository.unFavouriteArticle(Mockito.anyInt())).thenReturn(Completable.complete());

        unFavouriteArticleUseCase.execute(32).subscribe();

        Mockito.verify(feedRepository, Mockito.times(1)).unFavouriteArticle(32);
    }
}