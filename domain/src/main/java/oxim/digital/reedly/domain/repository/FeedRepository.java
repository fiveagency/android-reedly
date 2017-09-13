package oxim.digital.reedly.domain.repository;

import java.util.List;

import oxim.digital.reedly.domain.model.Article;
import oxim.digital.reedly.domain.model.Feed;
import rx.Completable;
import rx.Single;

public interface FeedRepository {

    Single<List<Feed>> getUserFeeds();

    Single<List<Article>> getArticles(int feedId);

    Single<Boolean> feedExists(String feedUrl);

    Completable createNewFeed(String feedUrl);

    Completable deleteFeed(int feedId);

    Completable pullArticlesForFeedFromOrigin(Feed feed);

    Completable markArticleAsRead(int articleId);

    Completable favouriteArticle(int articleId);

    Completable unFavouriteArticle(int articleId);

    Single<Long> getUnreadArticlesCount();

    Single<List<Article>> getFavouriteArticles();

    Single<Boolean> shouldUpdateFeedsInBackground();

    Completable setShouldUpdateFeedsInBackground(boolean shouldUpdate);
}
