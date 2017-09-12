package oxim.digital.reedly.data.feed.service;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.InputStream;
import java.util.ArrayList;

import oxim.digital.reedly.DataTestData;
import oxim.digital.reedly.data.feed.service.model.ApiFeed;
import oxim.digital.reedly.data.feed.service.parser.FeedParser;
import rx.Single;
import rx.observers.TestSubscriber;

public final class FeedServiceImplTest {

    private FeedServiceImpl feedServiceImpl;
    private FeedParser feedParser;
    private TestSubscriber<ApiFeed> testSubscriber;

    @Before
    public void setUp() {
        feedParser = Mockito.mock(FeedParser.class);
        feedServiceImpl = new FeedServiceImpl(feedParser);
        testSubscriber = new TestSubscriber<>();
    }

    @Test
    public void shouldFetchFeedByUrl() throws Exception {
        final ApiFeed apiFeed = new ApiFeed(DataTestData.TEST_STRING_TITLE_1, DataTestData.TEST_IMAGE_URL, DataTestData.TEST_BASIC_URL_STRING, DataTestData.TEST_DESCRIPTION_STRING,
                                            DataTestData.TEST_COMPLEX_URL_STRING_1, new ArrayList<>());
        Mockito.when(feedParser.parseFeed(Mockito.any(), Mockito.eq(DataTestData.TEST_COMPLEX_URL_STRING_1))).thenReturn(Single.just(apiFeed));

        feedServiceImpl.fetchFeed(DataTestData.TEST_COMPLEX_URL_STRING_1).subscribe(testSubscriber);
        Mockito.verify(feedParser, Mockito.times(1)).parseFeed(Mockito.any(), Mockito.eq(DataTestData.TEST_COMPLEX_URL_STRING_1));

        testSubscriber.assertCompleted();
        testSubscriber.assertValue(apiFeed);
    }
}