package oxim.digital.reedly.data.feed.converter;

import oxim.digital.reedly.data.feed.db.model.ArticleModel;
import oxim.digital.reedly.data.feed.db.model.FeedModel;
import oxim.digital.reedly.data.feed.service.model.ApiFeed;
import oxim.digital.reedly.data.feed.service.model.ApiArticle;
import oxim.digital.reedly.domain.model.Feed;
import oxim.digital.reedly.domain.model.Article;

public final class FeedModelConverterImpl implements FeedModelConverter {

    private static final String FORWARD_SLASH = "/";

    @Override
    public Feed modelToDomain(final FeedModel model) {
        return new Feed(model.getId(), model.getTitle(), model.getImageUrl(), model.getPageLink(), model.getDescription(), model.getUrl());
    }

    @Override
    public Article modelToDomain(final ArticleModel model) {
        return new Article(model.getId(), model.getFeedId(), model.getTitle(), model.getLink(), model.getPublicationDate(), model.isNew(), model.isFavourite());
    }

    @Override
    public FeedModel apiToModel(final ApiFeed apiFeed) {
        return new FeedModel(apiFeed.title, clearImageUrl(apiFeed.imageUrl), apiFeed.pageLink, apiFeed.description, apiFeed.url);
    }

    @Override
    public ArticleModel apiToModel(final ApiArticle apiArticle, final int feedId) {
        return new ArticleModel(feedId, apiArticle.title, apiArticle.link, apiArticle.publicationDate);
    }

    private String clearImageUrl(final String imageUrl) {
        if (imageUrl != null && imageUrl.endsWith(FORWARD_SLASH)) {
            return imageUrl.substring(0, imageUrl.length() - 1);
        }
        return imageUrl;
    }
}
