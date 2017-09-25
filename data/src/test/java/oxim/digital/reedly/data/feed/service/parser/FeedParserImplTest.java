package oxim.digital.reedly.data.feed.service.parser;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import oxim.digital.reedly.DataTestData;
import oxim.digital.reedly.data.feed.service.model.ApiArticle;
import oxim.digital.reedly.data.feed.service.model.ApiFeed;
import rx.observers.TestSubscriber;

public final class FeedParserImplTest {

    private FeedParserImpl feedParserImpl;
    private ExternalParserWrapper externalParserWrapper;
    private InputStream inputStream;
    private TestSubscriber<ApiFeed> testSubscriber;

    @Before
    public void setUp() throws Exception {
        inputStream = Mockito.mock(InputStream.class);
        externalParserWrapper = Mockito.mock(ExternalParserWrapper.class);
        feedParserImpl = new FeedParserImpl(externalParserWrapper);

        testSubscriber = new TestSubscriber<>();
    }

    @Test
    public void shouldParseFeedsWithGivenUrl() throws Exception {
        final List<ApiArticle> items = new ArrayList<>();
        final ApiArticle firstItem = new ApiArticle(DataTestData.TEST_STRING_TITLE_2, DataTestData.TEST_COMPLEX_URL_STRING_2, DataTestData.TEST_DATE_LONG);
        items.add(firstItem);

        final ApiFeed apiFeed = new ApiFeed(DataTestData.TEST_STRING_TITLE_1, DataTestData.TEST_IMAGE_URL, DataTestData.TEST_BASIC_URL_STRING,
                                            DataTestData.TEST_DESCRIPTION_STRING, DataTestData.TEST_BASIC_URL_STRING, items);

        Mockito.when(externalParserWrapper.parseOrThrow(Mockito.eq(inputStream), Mockito.anyString())).thenReturn(apiFeed);

        feedParserImpl.parseFeed(inputStream, DataTestData.TEST_COMPLEX_URL_STRING_1).subscribe(testSubscriber);

        testSubscriber.assertCompleted();
        testSubscriber.assertValueCount(1);

        final List<ApiFeed> apiFeeds = testSubscriber.getOnNextEvents();
        final ApiFeed result = apiFeeds.get(0);

        Assert.assertEquals(DataTestData.TEST_STRING_TITLE_1, result.title);
        Assert.assertEquals(DataTestData.TEST_IMAGE_URL, result.imageUrl);
        Assert.assertEquals(DataTestData.TEST_BASIC_URL_STRING, result.pageLink);
        Assert.assertEquals(DataTestData.TEST_BASIC_URL_STRING, result.url);

        Assert.assertEquals(1, result.articles.size());
        final ApiArticle apiArticleResult = result.articles.get(0);

        Assert.assertEquals(DataTestData.TEST_STRING_TITLE_2, apiArticleResult.title);
        Assert.assertEquals(DataTestData.TEST_COMPLEX_URL_STRING_2, apiArticleResult.link);
        Assert.assertEquals(DataTestData.TEST_DATE_LONG, apiArticleResult.publicationDate);
    }

    @Test
    public void shouldThrowErrorWhenInvalidUrlGiven() throws Exception {
        Mockito.when(externalParserWrapper.parseOrThrow(Mockito.eq(inputStream), Mockito.anyString())).thenThrow(new IOException());

        feedParserImpl.parseFeed(inputStream, DataTestData.TEST_COMPLEX_URL_STRING_1).subscribe(testSubscriber);

        testSubscriber.assertError(IOException.class);
    }
}