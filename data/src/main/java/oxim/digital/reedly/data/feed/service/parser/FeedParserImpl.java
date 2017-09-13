package oxim.digital.reedly.data.feed.service.parser;

import java.io.InputStream;

import oxim.digital.reedly.data.feed.service.model.ApiFeed;
import rx.Single;

public final class FeedParserImpl implements FeedParser {

    private final ExternalParserWrapper externalParserWrapper;

    public FeedParserImpl(final ExternalParserWrapper externalParserWrapper) {
        this.externalParserWrapper = externalParserWrapper;
    }

    @Override
    public Single<ApiFeed> parseFeed(final InputStream inputStream, final String feedUrl) {
        return Single.defer(() -> Single.just(externalParserWrapper.parseOrThrow(inputStream, feedUrl)));
    }
}
