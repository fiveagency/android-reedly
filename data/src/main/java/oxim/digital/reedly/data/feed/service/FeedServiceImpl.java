package oxim.digital.reedly.data.feed.service;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import oxim.digital.reedly.data.feed.service.model.ApiFeed;
import oxim.digital.reedly.data.feed.service.parser.FeedParser;
import oxim.digital.reedly.data.util.CurrentTimeProvider;
import rx.Single;

public final class FeedServiceImpl implements FeedService {

    private final FeedParser feedParser;

    public FeedServiceImpl(final FeedParser feedParser) {
        this.feedParser = feedParser;
    }

    @Override
    public Single<ApiFeed> fetchFeed(final String feedUrl) {
        try {
            final InputStream inputStream = new URL(feedUrl).openConnection().getInputStream();
            return feedParser.parseFeed(inputStream, feedUrl)
                             .doOnSuccess(feed -> closeStream(inputStream))
                             .doOnError(throwable -> closeStream(inputStream));
        } catch (IOException e) {
            e.printStackTrace();
            return Single.error(e);
        }
    }

    private void closeStream(final InputStream inputStream) {
        try {
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
