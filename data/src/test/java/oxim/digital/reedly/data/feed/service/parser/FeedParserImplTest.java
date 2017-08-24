package oxim.digital.reedly.data.feed.service.parser;

import com.einmalfel.earl.Feed;
import com.einmalfel.earl.Item;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import oxim.digital.reedly.DataTestData;
import oxim.digital.reedly.TestFeed;
import oxim.digital.reedly.TestItem;
import oxim.digital.reedly.data.feed.service.model.ApiArticle;
import oxim.digital.reedly.data.feed.service.model.ApiFeed;
import oxim.digital.reedly.data.util.CurrentTimeProvider;
import rx.observers.TestSubscriber;

public final class FeedParserImplTest {

    private FeedParserImpl feedParserImpl;
    private CurrentTimeProvider currentTimeProvider;
    private ExternalParserWrapper externalParserWrapper;
    private InputStream inputStream;
    private TestSubscriber<ApiFeed> testSubscriber;

    @Before
    public void setUp() throws Exception {
        inputStream = Mockito.mock(InputStream.class);
        currentTimeProvider = Mockito.mock(CurrentTimeProvider.class);
        externalParserWrapper = Mockito.mock(ExternalParserWrapper.class);
        feedParserImpl = new FeedParserImpl(currentTimeProvider, externalParserWrapper);

        testSubscriber = new TestSubscriber<>();
    }

    @Test
    public void parseFeedSingleArticleAndArticlePublicationDateGiven() throws Exception {
        final List<Item> items = new ArrayList<>();
        final Item firstItem = new TestItem(DataTestData.TEST_COMPLEX_URL_STRING_2, DataTestData.TEST_DATE, DataTestData.TEST_STRING_TITLE_2);
        items.add(firstItem);

        final Feed feed = new TestFeed(DataTestData.TEST_BASIC_URL_STRING, DataTestData.TEST_DATE, DataTestData.TEST_STRING_TITLE_1, DataTestData.TEST_DESCRIPTION_STRING,
                                       DataTestData.TEST_DESCRIPTION_STRING, DataTestData.TEST_IMAGE_URL, DataTestData.TEST_SMALL_STRING, items);

        Mockito.when(externalParserWrapper.parseOrThrow(Mockito.eq(inputStream), Mockito.anyInt())).thenReturn(feed);
        Mockito.when(currentTimeProvider.getCurrentTime()).thenReturn(DataTestData.TEST_DATE_LONG);

        feedParserImpl.parseFeed(inputStream, DataTestData.TEST_COMPLEX_URL_STRING_1).subscribe(testSubscriber);

        testSubscriber.assertCompleted();
        testSubscriber.assertValueCount(1);
        Mockito.verifyNoMoreInteractions(currentTimeProvider);

        final List<ApiFeed> apiFeeds = testSubscriber.getOnNextEvents();
        final ApiFeed result = apiFeeds.get(0);

        Assert.assertEquals(DataTestData.TEST_STRING_TITLE_1, result.title);
        Assert.assertEquals(DataTestData.TEST_IMAGE_URL, result.imageUrl);
        Assert.assertEquals(DataTestData.TEST_BASIC_URL_STRING, result.pageLink);
        Assert.assertEquals(DataTestData.TEST_COMPLEX_URL_STRING_1, result.url);

        Assert.assertEquals(1, result.articles.size());
        final ApiArticle apiArticleResult = result.articles.get(0);

        Assert.assertEquals(DataTestData.TEST_STRING_TITLE_2, apiArticleResult.title);
        Assert.assertEquals(DataTestData.TEST_COMPLEX_URL_STRING_2, apiArticleResult.link);
        Assert.assertEquals(DataTestData.TEST_DATE_LONG, apiArticleResult.publicationDate);
    }

    @Test
    public void parseFeedArticlePublicationDateIsNullAndMultipleArticles() throws Exception {
        final List<Item> items = new ArrayList<>();
        final Item firstItem = new TestItem(DataTestData.TEST_COMPLEX_URL_STRING_1, null, DataTestData.TEST_STRING_TITLE_1);
        items.add(firstItem);

        final Item secondItem = new TestItem(DataTestData.TEST_COMPLEX_URL_STRING_2, null, DataTestData.TEST_STRING_TITLE_2);
        items.add(secondItem);

        final Feed feed = new TestFeed(DataTestData.TEST_BASIC_URL_STRING, DataTestData.TEST_DATE, DataTestData.TEST_STRING_TITLE_1, DataTestData.TEST_DESCRIPTION_STRING,
                                       DataTestData.TEST_DESCRIPTION_STRING, DataTestData.TEST_IMAGE_URL, DataTestData.TEST_SMALL_STRING, items);

        Mockito.when(externalParserWrapper.parseOrThrow(Mockito.eq(inputStream), Mockito.anyInt())).thenReturn(feed);
        Mockito.when(currentTimeProvider.getCurrentTime()).thenReturn(DataTestData.TEST_DATE_LONG);

        feedParserImpl.parseFeed(inputStream, DataTestData.TEST_COMPLEX_URL_STRING_1).subscribe(testSubscriber);

        testSubscriber.assertCompleted();
        testSubscriber.assertValueCount(1);
        Mockito.verify(currentTimeProvider, Mockito.times(2)).getCurrentTime();

        final List<ApiFeed> apiFeeds = testSubscriber.getOnNextEvents();
        final ApiFeed result = apiFeeds.get(0);

        Assert.assertEquals(DataTestData.TEST_STRING_TITLE_1, result.title);
        Assert.assertEquals(DataTestData.TEST_IMAGE_URL, result.imageUrl);
        Assert.assertEquals(DataTestData.TEST_BASIC_URL_STRING, result.pageLink);
        Assert.assertEquals(DataTestData.TEST_COMPLEX_URL_STRING_1, result.url);

        Assert.assertEquals(2, result.articles.size());

        final ApiArticle firstApiArticleResult = result.articles.get(0);
        Assert.assertEquals(DataTestData.TEST_STRING_TITLE_1, firstApiArticleResult.title);
        Assert.assertEquals(DataTestData.TEST_COMPLEX_URL_STRING_1, firstApiArticleResult.link);
        Assert.assertEquals(DataTestData.TEST_DATE_LONG, firstApiArticleResult.publicationDate);

        final ApiArticle secondApiArticleResult = result.articles.get(1);
        Assert.assertEquals(DataTestData.TEST_STRING_TITLE_2, secondApiArticleResult.title);
        Assert.assertEquals(DataTestData.TEST_COMPLEX_URL_STRING_2, secondApiArticleResult.link);
        Assert.assertEquals(DataTestData.TEST_DATE_LONG, secondApiArticleResult.publicationDate);
    }

    @Test
    public void parseFeedWithIOException() throws Exception {
        Mockito.when(externalParserWrapper.parseOrThrow(Mockito.eq(inputStream), Mockito.anyInt())).thenThrow(new IOException());
        Mockito.when(currentTimeProvider.getCurrentTime()).thenReturn(DataTestData.TEST_DATE_LONG);

        feedParserImpl.parseFeed(inputStream, DataTestData.TEST_COMPLEX_URL_STRING_1).subscribe(testSubscriber);

        testSubscriber.assertError(IOException.class);
    }
}