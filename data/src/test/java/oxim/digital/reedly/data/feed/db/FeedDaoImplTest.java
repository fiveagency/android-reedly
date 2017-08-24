package oxim.digital.reedly.data.feed.db;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import oxim.digital.reedly.data.feed.converter.FeedModelConverter;

public final class FeedDaoImplTest {

    private FeedDaoImpl feedDao;
    private FeedModelConverter feedModelConverter;

    @Before
    public void setUp() throws Exception {
        feedModelConverter = Mockito.mock(FeedModelConverter.class);
        feedDao = new FeedDaoImpl(feedModelConverter);
    }

    // TODO TODO TODO

    @Test
    public void insertFeed() throws Exception {

    }

    @Test
    public void getAllFeeds() throws Exception {

    }

    @Test
    public void updateFeed() throws Exception {

    }

    @Test
    public void getArticlesForFeed() throws Exception {

    }

    @Test
    public void doesFeedExist() throws Exception {

    }

    @Test
    public void deleteFeed() throws Exception {

    }

    @Test
    public void markArticlesAsRead() throws Exception {

    }

    @Test
    public void favouriteArticle() throws Exception {

    }

    @Test
    public void unFavouriteArticle() throws Exception {

    }

    @Test
    public void getUnreadArticlesCount() throws Exception {

    }

    @Test
    public void getFavouriteArticles() throws Exception {

    }
}