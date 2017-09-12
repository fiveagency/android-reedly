package oxim.digital.reedly.data.feed.converter;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import oxim.digital.reedly.DataTestData;
import oxim.digital.reedly.data.feed.db.model.ArticleModel;
import oxim.digital.reedly.data.feed.db.model.FeedModel;
import oxim.digital.reedly.data.feed.service.model.ApiArticle;
import oxim.digital.reedly.data.feed.service.model.ApiFeed;
import oxim.digital.reedly.domain.model.Article;
import oxim.digital.reedly.domain.model.Feed;

public final class FeedModelConverterImplTest {

    private FeedModelConverterImpl feedModelConverterImpl;

    @Before
    public void setUp() throws Exception {
        feedModelConverterImpl = new FeedModelConverterImpl();
    }

    @Test
    public void shouldMapArticleModelToDomainModel() throws Exception {
        final ArticleModel articleModel = new ArticleModel(DataTestData.TEST_INTEGER_ID_1, DataTestData.TEST_STRING_TITLE_1,
                                                           DataTestData.TEST_COMPLEX_URL_STRING_1, DataTestData.TEST_LONG, false, true);
        final Article article = feedModelConverterImpl.modelToDomain(articleModel);

        Assert.assertEquals(DataTestData.TEST_INTEGER_ID_1, article.feedId);
        Assert.assertEquals(DataTestData.TEST_STRING_TITLE_1, article.title);
        Assert.assertEquals(DataTestData.TEST_COMPLEX_URL_STRING_1, article.link);
        Assert.assertEquals(DataTestData.TEST_LONG, article.publicationDate);
        Assert.assertEquals(false, article.isNew);
        Assert.assertEquals(true, article.isFavourite);
    }

    @Test
    public void shouldMapArticleModelToNewAndNotFavouriteDomainArticleIfNotSetDifferently() throws Exception {
        final ArticleModel articleModel = new ArticleModel(DataTestData.TEST_INTEGER_ID_1, DataTestData.TEST_STRING_TITLE_1,
                                                           DataTestData.TEST_COMPLEX_URL_STRING_1, DataTestData.TEST_LONG);
        final Article article = feedModelConverterImpl.modelToDomain(articleModel);

        Assert.assertEquals(DataTestData.TEST_INTEGER_ID_1, article.feedId);
        Assert.assertEquals(DataTestData.TEST_STRING_TITLE_1, article.title);
        Assert.assertEquals(DataTestData.TEST_COMPLEX_URL_STRING_1, article.link);
        Assert.assertEquals(DataTestData.TEST_LONG, article.publicationDate);
        Assert.assertEquals(true, article.isNew);
        Assert.assertEquals(false, article.isFavourite);
    }

    @Test
    public void shouldMapFeedModelToDomainModel() throws Exception {
        final FeedModel feedModel = new FeedModel(DataTestData.TEST_INTEGER_ID_1, DataTestData.TEST_STRING_TITLE_1, DataTestData.TEST_IMAGE_URL, DataTestData.TEST_BASIC_URL_STRING,
                                                  DataTestData.TEST_DESCRIPTION_STRING, DataTestData.TEST_COMPLEX_URL_STRING_1);
        final Feed feed = feedModelConverterImpl.modelToDomain(feedModel);

        Assert.assertEquals(DataTestData.TEST_DESCRIPTION_STRING, feed.description);
        Assert.assertEquals(DataTestData.TEST_INTEGER_ID_1, feed.id);
        Assert.assertEquals(DataTestData.TEST_IMAGE_URL, feed.imageUrl);
        Assert.assertEquals(DataTestData.TEST_BASIC_URL_STRING, feed.pageLink);
        Assert.assertEquals(DataTestData.TEST_STRING_TITLE_1, feed.title);
        Assert.assertEquals(DataTestData.TEST_COMPLEX_URL_STRING_1, feed.url);
    }

    @Test
    public void shouldMapFeedApiToModelWithSlashAtTheEndOfTheImageUrl() throws Exception {
        final ApiFeed apiFeed = new ApiFeed(DataTestData.TEST_STRING_TITLE_1, DataTestData.TEST_IMAGE_URL_SLASH_AT_THE_END, DataTestData.TEST_BASIC_URL_STRING,
                                            DataTestData.TEST_DESCRIPTION_STRING, DataTestData.TEST_COMPLEX_URL_STRING_1, new ArrayList<>());
        final FeedModel feedModel = feedModelConverterImpl.apiToModel(apiFeed);

        Assert.assertEquals(DataTestData.TEST_STRING_TITLE_1, feedModel.getTitle());
        Assert.assertEquals(DataTestData.TEST_IMAGE_URL, feedModel.getImageUrl());
        Assert.assertEquals(DataTestData.TEST_BASIC_URL_STRING, feedModel.getPageLink());
        Assert.assertEquals(DataTestData.TEST_DESCRIPTION_STRING, feedModel.getDescription());
        Assert.assertEquals(DataTestData.TEST_COMPLEX_URL_STRING_1, feedModel.getUrl());
    }

    @Test
    public void shouldMapFeedApiToModelWithoutSlashAtTheEndOfTheImageUrl() throws Exception {
        final ApiFeed apiFeed = new ApiFeed(DataTestData.TEST_STRING_TITLE_1, DataTestData.TEST_IMAGE_URL, DataTestData.TEST_BASIC_URL_STRING,
                                            DataTestData.TEST_DESCRIPTION_STRING, DataTestData.TEST_COMPLEX_URL_STRING_1, new ArrayList<>());
        final FeedModel feedModel = feedModelConverterImpl.apiToModel(apiFeed);

        Assert.assertEquals(DataTestData.TEST_STRING_TITLE_1, feedModel.getTitle());
        Assert.assertEquals(DataTestData.TEST_IMAGE_URL, feedModel.getImageUrl());
        Assert.assertEquals(DataTestData.TEST_BASIC_URL_STRING, feedModel.getPageLink());
        Assert.assertEquals(DataTestData.TEST_DESCRIPTION_STRING, feedModel.getDescription());
        Assert.assertEquals(DataTestData.TEST_COMPLEX_URL_STRING_1, feedModel.getUrl());
    }

    @Test
    public void shouldMapArticleApiToModel() throws Exception {
        final ApiArticle apiArticle = new ApiArticle(DataTestData.TEST_STRING_TITLE_1, DataTestData.TEST_COMPLEX_URL_STRING_1, DataTestData.TEST_LONG);

        final ArticleModel articleModel = feedModelConverterImpl.apiToModel(apiArticle, DataTestData.TEST_INTEGER_ID_1);

        Assert.assertEquals(DataTestData.TEST_INTEGER_ID_1, articleModel.getFeedId());
        Assert.assertEquals(DataTestData.TEST_STRING_TITLE_1, articleModel.getTitle());
        Assert.assertEquals(DataTestData.TEST_COMPLEX_URL_STRING_1, articleModel.getLink());
        Assert.assertEquals(DataTestData.TEST_LONG, articleModel.getPublicationDate());
    }
}