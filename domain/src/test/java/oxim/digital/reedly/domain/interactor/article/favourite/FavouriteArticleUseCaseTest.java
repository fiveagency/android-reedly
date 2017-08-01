package oxim.digital.reedly.domain.interactor.article.favourite;

import org.mockito.Mockito;

import oxim.digital.reedly.domain.repository.FeedRepository;
import rx.Completable;

public final class FavouriteArticleUseCaseTest {

    private FavouriteArticleUseCase favouriteArticleUseCase;
    private FeedRepository feedRepository;

    @org.junit.Before
    public void setUp() throws Exception {
        feedRepository = Mockito.mock(FeedRepository.class);
        favouriteArticleUseCase = new FavouriteArticleUseCase(feedRepository);
    }

    @org.junit.Test
    public void execute() throws Exception {
        Mockito.when(feedRepository.favouriteArticle(Mockito.anyInt())).thenReturn(Completable.complete());

        favouriteArticleUseCase.execute(32).subscribe();

        Mockito.verify(feedRepository, Mockito.times(1)).favouriteArticle(32);
    }
}