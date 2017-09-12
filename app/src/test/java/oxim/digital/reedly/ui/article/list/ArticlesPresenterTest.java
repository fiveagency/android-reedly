package oxim.digital.reedly.ui.article.list;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import oxim.digital.reedly.AppTestData;
import oxim.digital.reedly.configuration.ViewActionQueue;
import oxim.digital.reedly.domain.interactor.article.GetArticlesUseCase;
import oxim.digital.reedly.domain.interactor.article.MarkArticleAsReadUseCase;
import oxim.digital.reedly.domain.interactor.article.favourite.FavouriteArticleUseCase;
import oxim.digital.reedly.domain.interactor.article.favourite.GetFavouriteArticlesUseCase;
import oxim.digital.reedly.domain.interactor.article.favourite.UnFavouriteArticleUseCase;
import oxim.digital.reedly.ui.mapper.FeedViewModeMapper;

import static org.junit.Assert.*;

public final class ArticlesPresenterTest {

    @Mock
    GetArticlesUseCase getArticlesUseCase;

    @Mock
    MarkArticleAsReadUseCase markArticleAsReadUseCase;

    @Mock
    FavouriteArticleUseCase favouriteArticleUseCase;

    @Mock
    UnFavouriteArticleUseCase unFavouriteArticleUseCase;

    @Mock
    GetFavouriteArticlesUseCase getFavouriteArticlesUseCase;

    @Mock
    FeedViewModeMapper feedViewModeMapper;

    private ArticlesPresenter articlesPresenter;
    protected ViewActionQueue<ArticlesContract.View> viewActionQueue;


    @Before
    public void setUp() throws Exception {
        final ArticlesContract.View view = Mockito.mock(ArticlesContract.View.class);
        viewActionQueue = Mockito.mock(ViewActionQueue.class);
        articlesPresenter = new ArticlesPresenter(view);
    }

    @Test
    public void shouldFetchArticles() throws Exception {
        final int feedId = AppTestData.TEST_FEED_ID;
        articlesPresenter.fetchArticles(feedId);

        Mockito.verify(getArticlesUseCase, Mockito.times(1)).execute(feedId);
    }

    @Test
    public void shouldFetchFavouriteArticles() throws Exception {

    }

    @Test
    public void shouldShowArticleContent() throws Exception {

    }

    @Test
    public void shouldMarkArticleAsRead() throws Exception {

    }

    @Test
    public void shouldMakeArticleFavourite() throws Exception {

    }

    @Test
    public void shouldRemoveArticleFromFavourites() throws Exception {

    }
}