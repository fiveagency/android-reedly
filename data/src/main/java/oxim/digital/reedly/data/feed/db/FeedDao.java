package oxim.digital.reedly.data.feed.db;

import java.util.List;

import oxim.digital.reedly.data.feed.service.model.ApiFeed;
import oxim.digital.reedly.data.feed.service.model.ApiArticle;
import oxim.digital.reedly.domain.model.Article;
import oxim.digital.reedly.domain.model.Feed;
import rx.Completable;
import rx.Single;

public interface FeedDao {

    Single<List<Feed>> getAllFeeds();

    Completable insertFeed(ApiFeed apiFeed);

    Completable updateFeed(int feedId, List<ApiArticle> apiArticles);

    Single<List<Article>> getArticlesForFeed(int feedId);

    Single<Boolean> doesFeedExist(String feedUrl);

    Completable deleteFeed(int feedId);

    Completable markArticlesAsRead(final int articleId);

    Completable favouriteArticle(final int articleId);

    Completable unFavouriteArticle(final int articleId);

    Single<Long> getUnreadArticlesCount();

    Single<List<Article>> getFavouriteArticles();
}
