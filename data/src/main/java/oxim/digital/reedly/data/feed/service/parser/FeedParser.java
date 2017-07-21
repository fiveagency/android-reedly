package oxim.digital.reedly.data.feed.service.parser;

import java.io.InputStream;

import oxim.digital.reedly.data.feed.service.model.ApiFeed;
import rx.Single;

public interface FeedParser {

    Single<ApiFeed> parseFeed(InputStream inputStream, String feedUrl);
}
