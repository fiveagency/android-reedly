package oxim.digital.reedly.domain.interactor;

import java.util.Date;

import oxim.digital.reedly.domain.model.Article;
import oxim.digital.reedly.domain.model.Feed;

public final class DomainTestData {

    public static final int TEST_INTEGER_ID_1 = 32;
    public static final int TEST_INTEGER_ID_2 = 64;

    public static final long TEST_LONG = 64L;
    public static final long TEST_DATE_LONG = 1024L;

    public static final Date TEST_DATE = new Date(TEST_DATE_LONG);

    public static final String TEST_URL_STRING = "http://www.google.com";
    public static final String TEST_STRING_TITLE = "this is first title";
    public static final String TEST_IMAGE_URL = "http://image.url/picture";
    public static final String TEST_DESCRIPTION_STRING = "this is a description string";
    public static final String TEST_BASIC_URL_STRING = "https://xkcd.com/";
    public static final String TEST_COMPLEX_URL_STRING = "https://xkcd.com/rss.xml";

    public static final Article TEST_NEW_FAVOURITE_ARTICLE = new Article(TEST_INTEGER_ID_1, TEST_INTEGER_ID_2, TEST_STRING_TITLE, TEST_BASIC_URL_STRING,
                                                                         TEST_DATE_LONG, false, false);

    public static final Feed TEST_FEED = new Feed(TEST_INTEGER_ID_1, TEST_STRING_TITLE, TEST_IMAGE_URL, TEST_BASIC_URL_STRING, TEST_DESCRIPTION_STRING, TEST_COMPLEX_URL_STRING);
}
