package oxim.digital.reedly.data.feed;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import oxim.digital.reedly.DataTestData;
import oxim.digital.reedly.data.feed.db.FeedDao;
import oxim.digital.reedly.data.feed.service.FeedService;
import oxim.digital.reedly.data.feed.service.model.ApiArticle;
import oxim.digital.reedly.data.feed.service.model.ApiFeed;
import oxim.digital.reedly.data.util.PreferenceUtils;
import oxim.digital.reedly.domain.model.Article;
import oxim.digital.reedly.domain.model.Feed;
import rx.Completable;
import rx.Scheduler;
import rx.Single;
import rx.observers.TestSubscriber;
import rx.schedulers.Schedulers;

public final class FeedRepositoryImplTest {

    private FeedService feedService;
    private FeedDao feedDao;
    private PreferenceUtils preferenceUtils;
    private Scheduler scheduler;

    private FeedRepositoryImpl feedRepositoryImpl;

    @Before
    public void setUp() throws Exception {
        feedService = Mockito.mock(FeedService.class);
        feedDao = Mockito.mock(FeedDao.class);
        preferenceUtils = Mockito.mock(PreferenceUtils.class);
        scheduler = Schedulers.immediate();

        feedRepositoryImpl = new FeedRepositoryImpl(feedService, feedDao, preferenceUtils, scheduler);
    }

    @Test
    public void shouldProvideAllUserFeeds() throws Exception {
        final List<Feed> feeds = new ArrayList<>();
        final Feed feed = new Feed(DataTestData.TEST_INTEGER_ID_1, DataTestData.TEST_STRING_TITLE_1, DataTestData.TEST_IMAGE_URL, DataTestData.TEST_BASIC_URL_STRING,
                                   DataTestData.TEST_DESCRIPTION_STRING, DataTestData.TEST_COMPLEX_URL_STRING_1);
        feeds.add(feed);
        Mockito.when(feedDao.getAllFeeds()).thenReturn(Single.just(feeds));

        final TestSubscriber<List<Feed>> testSubscriber = new TestSubscriber<>();
        feedRepositoryImpl.getUserFeeds().subscribe(testSubscriber);

        Mockito.verify(feedDao, Mockito.times(1)).getAllFeeds();
        Mockito.verifyNoMoreInteractions(feedDao);
        testSubscriber.assertCompleted();

        testSubscriber.assertValue(feeds);
    }

    @Test
    public void shouldProvideAllArticlesForExistingFeed() throws Exception {
        final List<Article> articles = new ArrayList<>();
        final Article article = new Article(DataTestData.TEST_INTEGER_ID_1, DataTestData.TEST_INTEGER_ID_2, DataTestData.TEST_STRING_TITLE_1, DataTestData.TEST_BASIC_URL_STRING,
                                            DataTestData.TEST_DATE_LONG, true, true);
        articles.add(article);
        Mockito.when(feedDao.getArticlesForFeed(DataTestData.TEST_INTEGER_ID_2)).thenReturn(Single.just(articles));

        final TestSubscriber<List<Article>> testSubscriber = new TestSubscriber<>();
        feedRepositoryImpl.getArticles(DataTestData.TEST_INTEGER_ID_2).subscribe(testSubscriber);

        Mockito.verify(feedDao, Mockito.times(1)).getArticlesForFeed(DataTestData.TEST_INTEGER_ID_2);
        Mockito.verifyNoMoreInteractions(feedDao);
        testSubscriber.assertCompleted();

        testSubscriber.assertValue(articles);
    }

    @Test
    public void shouldReturnEmptyArticlesListForNonExistentFeed() throws Exception {
        final List<Article> emptyList = new ArrayList<>();

        Mockito.when(feedDao.getArticlesForFeed(DataTestData.TEST_INTEGER_ID_1)).thenReturn(Single.just(emptyList));

        final TestSubscriber<List<Article>> testSubscriber = new TestSubscriber<>();
        feedRepositoryImpl.getArticles(DataTestData.TEST_INTEGER_ID_1).subscribe(testSubscriber);

        Mockito.verify(feedDao, Mockito.times(1)).getArticlesForFeed(DataTestData.TEST_INTEGER_ID_1);
        Mockito.verifyNoMoreInteractions(feedDao);
        testSubscriber.assertCompleted();

        testSubscriber.assertValue(emptyList);
    }

    @Test
    public void shouldReturnInfoAboutFeedExistingIfFeedExists() throws Exception {
        testIfFeedExists(true);
    }

    @Test
    public void shouldReturnInfoAboutFeedNotExistingIfFeedDoesNotExists() throws Exception {
        testIfFeedExists(false);
    }

    private void testIfFeedExists(final boolean exists) {
        Mockito.when(feedDao.doesFeedExist(DataTestData.TEST_COMPLEX_URL_STRING_1)).thenReturn(Single.just(exists));

        final TestSubscriber<Boolean> testSubscriber = new TestSubscriber<>();
        feedRepositoryImpl.feedExists(DataTestData.TEST_COMPLEX_URL_STRING_1).subscribe(testSubscriber);

        Mockito.verify(feedDao, Mockito.times(1)).doesFeedExist(DataTestData.TEST_COMPLEX_URL_STRING_1);
        Mockito.verifyNoMoreInteractions(feedDao);
        testSubscriber.assertCompleted();

        testSubscriber.assertValue(exists);
    }

    @Test
    public void shouldCreateNewValidFeed() throws Exception {
        final String feedUrl = DataTestData.TEST_COMPLEX_URL_STRING_1;
        final ApiFeed apiFeed = new ApiFeed(DataTestData.TEST_STRING_TITLE_1, DataTestData.TEST_IMAGE_URL, DataTestData.TEST_COMPLEX_URL_STRING_1,
                                            DataTestData.TEST_DESCRIPTION_STRING, feedUrl, new ArrayList<>());

        Mockito.when(feedService.fetchFeed(feedUrl)).thenReturn(Single.just(apiFeed));
        Mockito.when(feedDao.insertFeed(apiFeed)).thenReturn(Completable.complete());

        final TestSubscriber<Object> testSubscriber = new TestSubscriber<>();
        feedRepositoryImpl.createNewFeed(feedUrl).subscribe(testSubscriber);

        Mockito.verify(feedDao, Mockito.times(1)).insertFeed(apiFeed);
        Mockito.verify(feedService, Mockito.times(1)).fetchFeed(feedUrl);
        Mockito.verifyNoMoreInteractions(feedDao, feedService);

        testSubscriber.assertCompleted();
    }

    @Test
    public void shouldNotCreateInvalidFeed() throws Exception {
        final String feedUrl = DataTestData.TEST_COMPLEX_URL_STRING_1;

        Mockito.when(feedService.fetchFeed(feedUrl)).thenReturn(Single.error(new IOException()));

        final TestSubscriber<Object> testSubscriber = new TestSubscriber<>();
        feedRepositoryImpl.createNewFeed(feedUrl).subscribe(testSubscriber);

        Mockito.verify(feedService, Mockito.times(1)).fetchFeed(feedUrl);
        Mockito.verifyNoMoreInteractions(feedDao, feedService);

        testSubscriber.assertNotCompleted();
        testSubscriber.assertError(IOException.class);
    }

    @Test
    public void shouldDeleteExistingFeed() throws Exception {
        final int feedId = DataTestData.TEST_INTEGER_ID_1;

        Mockito.when(feedDao.deleteFeed(feedId)).thenReturn(Completable.complete());

        final TestSubscriber<Object> testSubscriber = new TestSubscriber<>();
        feedRepositoryImpl.deleteFeed(feedId).subscribe(testSubscriber);

        Mockito.verify(feedDao, Mockito.times(1)).deleteFeed(feedId);
        Mockito.verifyNoMoreInteractions(feedDao);

        testSubscriber.assertCompleted();
    }

    @Test
    public void shouldIgnoreDeletingOfNonExistentFeed() throws Exception {
        final int feedId = DataTestData.TEST_INTEGER_ID_1;

        Mockito.when(feedDao.deleteFeed(feedId)).thenReturn(Completable.complete());

        final TestSubscriber<Object> testSubscriber = new TestSubscriber<>();
        feedRepositoryImpl.deleteFeed(feedId).subscribe(testSubscriber);

        Mockito.verify(feedDao, Mockito.times(1)).deleteFeed(feedId);
        Mockito.verifyNoMoreInteractions(feedDao);

        testSubscriber.assertCompleted();
    }

    @Test
    public void shouldUpdateFeedArticlesFromWeb() throws Exception {
        final int feedId = DataTestData.TEST_INTEGER_ID_1;

        final List<ApiArticle> apiArticles = new ArrayList<>();
        final ApiArticle article = new ApiArticle(DataTestData.TEST_STRING_TITLE_1, DataTestData.TEST_BASIC_URL_STRING, DataTestData.TEST_DATE_LONG);
        apiArticles.add(article);

        final Feed feed = new Feed(DataTestData.TEST_INTEGER_ID_1, DataTestData.TEST_STRING_TITLE_1, DataTestData.TEST_IMAGE_URL, DataTestData.TEST_BASIC_URL_STRING,
                                   DataTestData.TEST_DESCRIPTION_STRING, DataTestData.TEST_COMPLEX_URL_STRING_1);

        final ApiFeed apiFeed = new ApiFeed(DataTestData.TEST_STRING_TITLE_1, DataTestData.TEST_IMAGE_URL, DataTestData.TEST_BASIC_URL_STRING, DataTestData.TEST_DESCRIPTION_STRING,
                                            DataTestData.TEST_COMPLEX_URL_STRING_1, apiArticles);

        Mockito.when(feedDao.updateFeed(feedId, apiArticles)).thenReturn(Completable.complete());
        Mockito.when(feedService.fetchFeed(feed.url)).thenReturn(Single.just(apiFeed));

        final TestSubscriber<Object> testSubscriber = new TestSubscriber<>();
        feedRepositoryImpl.pullArticlesForFeedFromOrigin(feed).subscribe(testSubscriber);

        Mockito.verify(feedDao, Mockito.times(1)).updateFeed(feedId, apiArticles);
        Mockito.verify(feedService, Mockito.times(1)).fetchFeed(feed.url);
        Mockito.verifyNoMoreInteractions(feedDao, feedService);

        testSubscriber.assertCompleted();
    }

    @Test
    public void shouldMarkArticleAsRead() throws Exception {
        final int articleId = DataTestData.TEST_INTEGER_ID_1;

        Mockito.when(feedDao.markArticlesAsRead(articleId)).thenReturn(Completable.complete());

        final TestSubscriber<Object> testSubscriber = new TestSubscriber<>();
        feedRepositoryImpl.markArticleAsRead(articleId).subscribe(testSubscriber);

        Mockito.verify(feedDao, Mockito.times(1)).markArticlesAsRead(articleId);
        Mockito.verifyNoMoreInteractions(feedDao);

        testSubscriber.assertCompleted();
    }

    @Test
    public void shouldMakeArticleFavourite() throws Exception {
        final int articleId = DataTestData.TEST_INTEGER_ID_1;

        Mockito.when(feedDao.favouriteArticle(articleId)).thenReturn(Completable.complete());

        final TestSubscriber<Object> testSubscriber = new TestSubscriber<>();
        feedRepositoryImpl.favouriteArticle(articleId).subscribe(testSubscriber);

        Mockito.verify(feedDao, Mockito.times(1)).favouriteArticle(articleId);
        Mockito.verifyNoMoreInteractions(feedDao);

        testSubscriber.assertCompleted();
    }

    @Test
    public void shouldRemoveArticleFromFavourites() throws Exception {
        final int articleId = DataTestData.TEST_INTEGER_ID_1;

        Mockito.when(feedDao.unFavouriteArticle(articleId)).thenReturn(Completable.complete());

        final TestSubscriber<Object> testSubscriber = new TestSubscriber<>();
        feedRepositoryImpl.unFavouriteArticle(articleId).subscribe(testSubscriber);

        Mockito.verify(feedDao, Mockito.times(1)).unFavouriteArticle(articleId);
        Mockito.verifyNoMoreInteractions(feedDao);

        testSubscriber.assertCompleted();
    }

    @Test
    public void shouldReturnNumberOfUnreadArticles() throws Exception {
        Mockito.when(feedDao.getUnreadArticlesCount()).thenReturn(Single.just(DataTestData.TEST_LONG));

        final TestSubscriber<Long> testSubscriber = new TestSubscriber<>();
        feedRepositoryImpl.getUnreadArticlesCount().subscribe(testSubscriber);

        Mockito.verify(feedDao, Mockito.times(1)).getUnreadArticlesCount();
        Mockito.verifyNoMoreInteractions(feedDao);

        testSubscriber.assertCompleted();
        testSubscriber.assertValue(DataTestData.TEST_LONG);
    }

    @Test
    public void shouldReturnFavouriteArticles() throws Exception {
        final List<Article> articles = new ArrayList<>();
        final Article article = new Article(DataTestData.TEST_INTEGER_ID_1, DataTestData.TEST_INTEGER_ID_2, DataTestData.TEST_STRING_TITLE_1, DataTestData.TEST_BASIC_URL_STRING,
                                            DataTestData.TEST_DATE_LONG, true, true);
        articles.add(article);
        Mockito.when(feedDao.getFavouriteArticles()).thenReturn(Single.just(articles));

        final TestSubscriber<List<Article>> testSubscriber = new TestSubscriber<>();
        feedRepositoryImpl.getFavouriteArticles().subscribe(testSubscriber);

        Mockito.verify(feedDao, Mockito.times(1)).getFavouriteArticles();
        Mockito.verifyNoMoreInteractions(feedDao);

        testSubscriber.assertCompleted();
        testSubscriber.assertValue(articles);
    }

    @Test
    public void shouldReturnIfUpdateShouldOccurInBackground() throws Exception {
        testIfUpdateShouldOccurInBackground(true);
    }

    @Test
    public void shouldReturnIfUpdateShouldNotOccurInBackground() throws Exception {
        testIfUpdateShouldOccurInBackground(false);
    }

    private void testIfUpdateShouldOccurInBackground(final boolean shouldOccurInBackground) {
        Mockito.when(preferenceUtils.shouldUpdateFeedsInBackground()).thenReturn(shouldOccurInBackground);

        final TestSubscriber<Boolean> testSubscriber = new TestSubscriber<>();
        feedRepositoryImpl.shouldUpdateFeedsInBackground().subscribe(testSubscriber);

        Mockito.verify(preferenceUtils, Mockito.times(1)).shouldUpdateFeedsInBackground();
        Mockito.verifyNoMoreInteractions(preferenceUtils);

        testSubscriber.assertCompleted();
        testSubscriber.assertValue(shouldOccurInBackground);
    }

    @Test
    public void shouldSetToUpdateArticlesInTheBackground() throws Exception {
        final TestSubscriber<Object> testSubscriber = new TestSubscriber<>();
        feedRepositoryImpl.setShouldUpdateFeedsInBackground(true).subscribe(testSubscriber);

        Mockito.verify(preferenceUtils, Mockito.times(1)).setShouldUpdateFeedsInBackground(true);
        Mockito.verifyNoMoreInteractions(preferenceUtils);

        testSubscriber.assertCompleted();
    }

    @Test
    public void shouldSetNotToUpdateArticlesInTheBackground() throws Exception {
        final TestSubscriber<Object> testSubscriber = new TestSubscriber<>();
        feedRepositoryImpl.setShouldUpdateFeedsInBackground(false).subscribe(testSubscriber);

        Mockito.verify(preferenceUtils, Mockito.times(1)).setShouldUpdateFeedsInBackground(false);
        Mockito.verifyNoMoreInteractions(preferenceUtils);

        testSubscriber.assertCompleted();
    }
}