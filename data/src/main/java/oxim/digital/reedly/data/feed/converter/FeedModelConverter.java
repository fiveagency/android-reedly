package oxim.digital.reedly.data.feed.converter;

import oxim.digital.reedly.data.feed.db.model.ArticleModel;
import oxim.digital.reedly.data.feed.db.model.FeedModel;
import oxim.digital.reedly.data.feed.service.model.ApiFeed;
import oxim.digital.reedly.data.feed.service.model.ApiArticle;
import oxim.digital.reedly.domain.model.Article;
import oxim.digital.reedly.domain.model.Feed;

public interface FeedModelConverter {

    Feed modelToDomain(FeedModel feedModel);

    Article modelToDomain(ArticleModel articleModel);

    FeedModel apiToModel(ApiFeed apiFeed);

    ArticleModel apiToModel(ApiArticle apiArticle, int feedId);
}
