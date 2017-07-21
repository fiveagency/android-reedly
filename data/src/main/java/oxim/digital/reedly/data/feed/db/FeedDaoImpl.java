package oxim.digital.reedly.data.feed.db;

import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;
import com.raizlabs.android.dbflow.sql.language.Method;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.structure.BaseModel;

import java.util.List;

import oxim.digital.reedly.data.feed.converter.FeedModelConverter;
import oxim.digital.reedly.data.feed.db.model.ArticleModel;
import oxim.digital.reedly.data.feed.db.model.ArticleModel_Table;
import oxim.digital.reedly.data.feed.db.model.FeedModel;
import oxim.digital.reedly.data.feed.db.model.FeedModel_Table;
import oxim.digital.reedly.data.feed.service.model.ApiArticle;
import oxim.digital.reedly.data.feed.service.model.ApiFeed;
import oxim.digital.reedly.domain.model.Article;
import oxim.digital.reedly.domain.model.Feed;
import rx.Completable;
import rx.Single;

import static com.raizlabs.android.dbflow.sql.language.SQLite.select;

public class FeedDaoImpl implements FeedDao {

    private final FeedModelConverter feedModelConverter;

    public FeedDaoImpl(final FeedModelConverter feedModelConverter) {
        this.feedModelConverter = feedModelConverter;
    }

    @Override
    public Completable insertFeed(final ApiFeed apiFeed) {
        return Completable.fromAction(() -> innerInsertFeed(apiFeed));
    }

    private void innerInsertFeed(final ApiFeed apiFeed) {
        final FeedModel feedModel = feedModelConverter.apiToModel(apiFeed);
        feedModel.save();

        Stream.of(apiFeed.articles)
              .map(apiArticle -> feedModelConverter.apiToModel(apiArticle, feedModel.getId()))
              .forEach(BaseModel::save);
    }

    @Override
    public Single<List<Feed>> getAllFeeds() {
        return Single.just(Stream.of(SQLite.select()
                                           .from(FeedModel.class)
                                           .queryList())
                                 .map(feedModelConverter::modelToDomain)
                                 .collect(Collectors.toList()));
    }

    @Override
    public Completable updateFeed(final int feedId, List<ApiArticle> apiArticles) {
        return Completable.fromAction(() -> innerUpdateFeed(feedId, apiArticles));
    }

    private void innerUpdateFeed(final int feedId, List<ApiArticle> apiArticles) {
        Stream.of(apiArticles)
              .map(apiArticle -> feedModelConverter.apiToModel(apiArticle, feedId))
              .forEach(BaseModel::save);
    }

    @Override
    public Single<List<Article>> getArticlesForFeed(final int feedId) {
        return Single.defer(() -> Single.just(innerGetArticlesForFeed(feedId)));
    }

    private List<Article> innerGetArticlesForFeed(final int feedId) {
        return Stream.of(select().from(ArticleModel.class)
                                 .where(ArticleModel_Table.feedId.eq(feedId))
                                 .orderBy(ArticleModel_Table.publicationDate, false)
                                 .queryList())
                     .map(feedModelConverter::modelToDomain)
                     .collect(Collectors.toList());
    }

    @Override
    public Single<Boolean> doesFeedExist(final String feedUrl) {
        return Single.defer(() -> Single.just(innerDoesFeedExist(feedUrl)));
    }

    private boolean innerDoesFeedExist(final String feedUrl) {
        return !SQLite.select()
                      .from(FeedModel.class)
                      .where(FeedModel_Table.url.eq(feedUrl))
                      .queryList()
                      .isEmpty();
    }

    @Override
    public Completable deleteFeed(final int feedId) {
        return Completable.fromAction(() -> innerDeleteFeed(feedId));
    }

    @Override
    public Completable markArticlesAsRead(final int articleId) {
        return Completable.fromAction(() -> SQLite.update(ArticleModel.class)
                                                  .set(ArticleModel_Table.isNew.eq(false))
                                                  .where(ArticleModel_Table.id.eq(articleId))
                                                  .execute());
    }

    @Override
    public Completable favouriteArticle(final int articleId) {
        return Completable.fromAction(() -> setFavouriteToArticle(true, articleId));
    }

    @Override
    public Completable unFavouriteArticle(final int articleId) {
        return Completable.fromAction(() -> setFavouriteToArticle(false, articleId));
    }

    @Override
    public Single<Long> getUnreadArticlesCount() {
        return Single.fromCallable(() -> SQLite.select(Method.count())
                                               .from(ArticleModel.class)
                                               .where(ArticleModel_Table.isNew.eq(true))
                                               .count());
    }

    @Override
    public Single<List<Article>> getFavouriteArticles() {
        return Single.fromCallable(this::innerGetFavouriteArticles);
    }

    private List<Article> innerGetFavouriteArticles() {
        return Stream.of(SQLite.select()
                               .from(ArticleModel.class)
                               .where(ArticleModel_Table.isFavourite.eq(true))
                               .queryList())
                     .map(feedModelConverter::modelToDomain)
                     .collect(Collectors.toList());
    }

    private void setFavouriteToArticle(final boolean isFavourite, final int articleId) {
        SQLite.update(ArticleModel.class)
              .set(ArticleModel_Table.isFavourite.eq(isFavourite))
              .where(ArticleModel_Table.id.eq(articleId))
              .execute();
    }

    private void innerDeleteFeed(final int feedId) {
        deleteArticlesForFeed(feedId);
        SQLite.delete(FeedModel.class)
              .where(FeedModel_Table.id.eq(feedId))
              .execute();
    }

    private void deleteArticlesForFeed(final int feedId) {
        SQLite.delete(ArticleModel.class)
              .where(ArticleModel_Table.feedId.eq(feedId))
              .execute();
    }
}
