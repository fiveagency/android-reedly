package oxim.digital.reedly.ui.article.list;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import java.util.ArrayList;
import java.util.List;

import oxim.digital.reedly.AppTestData;
import oxim.digital.reedly.MockViewActionQueue;
import oxim.digital.reedly.configuration.ViewActionQueueProvider;
import oxim.digital.reedly.data.util.connectivity.ConnectivityReceiver;
import oxim.digital.reedly.domain.interactor.article.GetArticlesUseCase;
import oxim.digital.reedly.domain.interactor.article.MarkArticleAsReadUseCase;
import oxim.digital.reedly.domain.interactor.article.favourite.FavouriteArticleUseCase;
import oxim.digital.reedly.domain.interactor.article.favourite.GetFavouriteArticlesUseCase;
import oxim.digital.reedly.domain.interactor.article.favourite.UnFavouriteArticleUseCase;
import oxim.digital.reedly.domain.model.Article;
import oxim.digital.reedly.ui.feed.create.NewFeedSubscriptionContract;
import oxim.digital.reedly.ui.mapper.FeedViewModeMapper;
import oxim.digital.reedly.ui.model.ArticleViewModel;
import oxim.digital.reedly.ui.router.Router;
import rx.Completable;
import rx.Observable;
import rx.Scheduler;
import rx.Single;
import rx.schedulers.Schedulers;

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

    @Mock
    Router router;

    @Mock
    ConnectivityReceiver connectivityReceiver;

    @Mock
    ViewActionQueueProvider viewActionQueueProvider;

    @Spy
    Scheduler mainThreadScheduler = Schedulers.immediate();

    @Spy
    MockViewActionQueue mockViewActionHandler;

    @InjectMocks
    ArticlesPresenter articlesPresenter;

    private ArticlesContract.View view;

    @Before
    public void setUp() throws Exception {
        view = Mockito.mock(ArticlesContract.View.class);
        articlesPresenter = new ArticlesPresenter(view);
        MockitoAnnotations.initMocks(this);

        Mockito.when(connectivityReceiver.getConnectivityStatus()).thenReturn(Observable.just(true));
        Mockito.when(viewActionQueueProvider.queueFor(Mockito.any())).thenReturn(new MockViewActionQueue<NewFeedSubscriptionContract.View>());

        articlesPresenter.start();
        articlesPresenter.activate();
    }

    @Test
    public void shouldFetchArticlesAndPassThemToView() throws Exception {
        final int feedId = AppTestData.TEST_FEED_ID;

        final List<Article> articles = new ArrayList<>();
        final Article article = new Article(AppTestData.TEST_ARTICLE_ID, feedId, AppTestData.TEST_STRING, AppTestData.TEST_LINK, AppTestData.TEST_LONG_DATE,
                                            false, false);
        articles.add(article);

        final List<ArticleViewModel> articleViewModels = new ArrayList<>();
        final ArticleViewModel articleViewModel = new ArticleViewModel(AppTestData.TEST_ARTICLE_ID, AppTestData.TEST_STRING, AppTestData.TEST_LINK, AppTestData.TEST_STRING,
                                                                       false, false);
        articleViewModels.add(articleViewModel);

        Mockito.when(getArticlesUseCase.execute(feedId)).thenReturn(Single.just(articles));
        Mockito.when(feedViewModeMapper.mapArticlesToViewModels(Mockito.anyList())).thenReturn(articleViewModels);

        articlesPresenter.fetchArticles(feedId);

        Mockito.verify(getArticlesUseCase, Mockito.times(1)).execute(feedId);
        Mockito.verify(view, Mockito.times(1)).showArticles(articleViewModels);
    }

    @Test
    public void shouldFetchFavouriteArticlesAndPassThemToView() throws Exception {
        final int feedId = AppTestData.TEST_FEED_ID;

        final List<Article> articles = new ArrayList<>();
        final Article article = new Article(AppTestData.TEST_ARTICLE_ID, feedId, AppTestData.TEST_STRING, AppTestData.TEST_LINK, AppTestData.TEST_LONG_DATE,
                                            false, false);
        articles.add(article);

        final List<ArticleViewModel> articleViewModels = new ArrayList<>();
        final ArticleViewModel articleViewModel = new ArticleViewModel(AppTestData.TEST_ARTICLE_ID, AppTestData.TEST_STRING, AppTestData.TEST_LINK, AppTestData.TEST_STRING,
                                                                       false, false);
        articleViewModels.add(articleViewModel);

        Mockito.when(getFavouriteArticlesUseCase.execute()).thenReturn(Single.just(articles));
        Mockito.when(feedViewModeMapper.mapArticlesToViewModels(Mockito.anyList())).thenReturn(articleViewModels);

        articlesPresenter.fetchFavouriteArticles();

        Mockito.verify(getFavouriteArticlesUseCase, Mockito.times(1)).execute();
        Mockito.verify(view, Mockito.times(1)).showArticles(articleViewModels);
    }

    @Test
    public void shouldShowArticleDetails() throws Exception {
        final ArticleViewModel articleViewModel = new ArticleViewModel(AppTestData.TEST_ARTICLE_ID, AppTestData.TEST_STRING, AppTestData.TEST_LINK, AppTestData.TEST_STRING,
                                                                       false, false);

        articlesPresenter.showArticleContent(articleViewModel);

        Mockito.verify(router, Mockito.times(1)).showArticleContentScreen(articleViewModel.link);
    }

    @Test
    public void shouldMarkArticleAsRead() throws Exception {
        final int articleId = AppTestData.TEST_ARTICLE_ID;
        Mockito.when(markArticleAsReadUseCase.execute(articleId)).thenReturn(Completable.complete());

        articlesPresenter.markArticleAsRead(articleId);

        Mockito.verify(markArticleAsReadUseCase, Mockito.times(1)).execute(articleId);
        Mockito.verifyNoMoreInteractions(markArticleAsReadUseCase);
    }

    @Test
    public void shouldMakeArticleFavourite() throws Exception {
        final ArticleViewModel articleViewModel = new ArticleViewModel(AppTestData.TEST_ARTICLE_ID, AppTestData.TEST_STRING, AppTestData.TEST_LINK, AppTestData.TEST_STRING,
                                                                       false, false);

        Mockito.when(favouriteArticleUseCase.execute(articleViewModel.id)).thenReturn(Completable.complete());

        articlesPresenter.toggleArticleFavourite(articleViewModel);

        Mockito.verify(favouriteArticleUseCase, Mockito.times(1)).execute(articleViewModel.id);
        Mockito.verifyNoMoreInteractions(favouriteArticleUseCase);
        Mockito.verify(unFavouriteArticleUseCase, Mockito.never()).execute(Mockito.anyInt());
    }

    @Test
    public void shouldMakeArticleNotFavourite() throws Exception {
        final ArticleViewModel articleViewModel = new ArticleViewModel(AppTestData.TEST_ARTICLE_ID, AppTestData.TEST_STRING, AppTestData.TEST_LINK, AppTestData.TEST_STRING,
                                                                       false, true);

        Mockito.when(unFavouriteArticleUseCase.execute(articleViewModel.id)).thenReturn(Completable.complete());

        articlesPresenter.toggleArticleFavourite(articleViewModel);

        Mockito.verify(unFavouriteArticleUseCase, Mockito.times(1)).execute(articleViewModel.id);
        Mockito.verifyNoMoreInteractions(unFavouriteArticleUseCase);
        Mockito.verify(favouriteArticleUseCase, Mockito.never()).execute(Mockito.anyInt());
    }
}