package oxim.digital.reedly.data.feed;

import java.util.List;

import oxim.digital.reedly.data.feed.db.FeedDao;
import oxim.digital.reedly.data.feed.service.FeedService;
import oxim.digital.reedly.data.util.PreferenceUtils;
import oxim.digital.reedly.domain.model.Article;
import oxim.digital.reedly.domain.model.Feed;
import oxim.digital.reedly.domain.repository.FeedRepository;
import rx.Completable;
import rx.Scheduler;
import rx.Single;
import rx.schedulers.Schedulers;

public final class FeedRepositoryImpl implements FeedRepository {

    private final FeedService feedService;
    private final FeedDao feedDao;

    private final PreferenceUtils preferenceUtils;

    private final Scheduler backgroundScheduler;

    public FeedRepositoryImpl(final FeedService feedService, final FeedDao feedDao, final PreferenceUtils preferenceUtils, final Scheduler backgroundScheduler) {
        this.feedService = feedService;
        this.feedDao = feedDao;
        this.preferenceUtils = preferenceUtils;
        this.backgroundScheduler = backgroundScheduler;
    }

    @Override
    public Single<List<Feed>> getUserFeeds() {
        return Single.defer(feedDao::getAllFeeds)
                     .subscribeOn(Schedulers.io());
    }

    @Override
    public Single<List<Article>> getArticles(final int feedId) {
        return Single.defer(() -> feedDao.getArticlesForFeed(feedId))
                     .subscribeOn(backgroundScheduler);
    }

    @Override
    public Single<Boolean> feedExists(final String feedUrl) {
        return Single.defer(() -> feedDao.doesFeedExist(feedUrl))
                     .subscribeOn(backgroundScheduler);
    }

    @Override
    public Completable createNewFeed(final String feedUrl) {
        return Completable.defer(() -> feedService.fetchFeed(feedUrl)
                                                  .flatMapCompletable(feedDao::insertFeed))
                          .subscribeOn(backgroundScheduler);
    }

    @Override
    public Completable deleteFeed(final int feedId) {
        return Completable.defer(() -> feedDao.deleteFeed(feedId))
                          .subscribeOn(backgroundScheduler);
    }

    @Override
    public Completable updateArticles(final Feed feed) {
        return Completable.defer(() -> feedService.fetchFeed(feed.url)
                                                  .flatMapCompletable(apiFeed -> feedDao.updateFeed(feed.id, apiFeed.articles)))
                          .subscribeOn(backgroundScheduler);
    }

    @Override
    public Completable markArticleAsRead(final int articleId) {
        return Completable.defer(() -> feedDao.markArticlesAsRead(articleId))
                          .subscribeOn(backgroundScheduler);
    }

    @Override
    public Completable favouriteArticle(final int articleId) {
        return Completable.defer(() -> feedDao.favouriteArticle(articleId))
                          .subscribeOn(backgroundScheduler);
    }

    @Override
    public Completable unFavouriteArticle(final int articleId) {
        return Completable.defer(() -> feedDao.unFavouriteArticle(articleId))
                          .subscribeOn(backgroundScheduler);
    }

    @Override
    public Single<Long> getUnreadArticlesCount() {
        return Single.defer(feedDao::getUnreadArticlesCount)
                     .subscribeOn(backgroundScheduler);
    }

    @Override
    public Single<List<Article>> getFavouriteArticles() {
        return Single.defer(feedDao::getFavouriteArticles)
                     .subscribeOn(backgroundScheduler);
    }

    @Override
    public Single<Boolean> shouldUpdateFeedsInBackground() {
        return Single.fromCallable(preferenceUtils::shouldUpdateFeedsInBackground)
                     .subscribeOn(backgroundScheduler);
    }

    @Override
    public Completable setShouldUpdateFeedsInBackground(final boolean shouldUpdate) {
        return Completable.fromAction(() -> preferenceUtils.setShouldUpdateFeedsInBackground(shouldUpdate))
                          .subscribeOn(backgroundScheduler);
    }
}
