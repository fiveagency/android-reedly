package oxim.digital.reedly.domain.interactor;

import oxim.digital.reedly.domain.model.Article;
import oxim.digital.reedly.domain.model.Feed;

public final class DomainTestData {

    public static final Integer TEST_INTEGER = 32;

    public static final Article TEST_NEW_FAVOURITE_ARTICLE = new Article(300, 200, "title", "link", 0L, true, true);

    public static final Long TEST_LONG = 64L;

    public static final Feed TEST_FEED = new Feed(78, "title", "image_url", "link", "description", "url");
}
