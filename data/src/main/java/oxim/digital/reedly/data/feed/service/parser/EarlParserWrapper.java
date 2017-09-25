package oxim.digital.reedly.data.feed.service.parser;

import android.support.annotation.Nullable;

import com.annimon.stream.Stream;
import com.einmalfel.earl.EarlParser;
import com.einmalfel.earl.Feed;

import java.io.InputStream;
import java.util.Date;
import java.util.List;

import oxim.digital.reedly.data.feed.service.model.ApiArticle;
import oxim.digital.reedly.data.feed.service.model.ApiFeed;
import oxim.digital.reedly.data.util.CurrentTimeProvider;

public final class EarlParserWrapper implements ExternalParserWrapper {

    private final CurrentTimeProvider currentTimeProvider;

    public EarlParserWrapper(final CurrentTimeProvider currentTimeProvider) {
        this.currentTimeProvider = currentTimeProvider;
    }

    @Override
    public ApiFeed parseOrThrow(final InputStream inputStream, final String feedUrl) throws Exception {
        return mapToApiFeed(EarlParser.parseOrThrow(inputStream, 0), feedUrl);
    }

    @Override
    public ApiFeed parse(final InputStream inputStream, final String feedUrl) {
        return mapToApiFeed(EarlParser.parse(inputStream, 0), feedUrl);
    }

    private ApiFeed mapToApiFeed(final Feed parsedFeed, final String feedUrl) {
        final List<ApiArticle> apiArticles = Stream.of(parsedFeed.getItems())
                                                   .map(article -> new ApiArticle(article.getTitle(), article.getLink(), getTimeForDate(article.getPublicationDate())))
                                                   .toList();
        return new ApiFeed(parsedFeed.getTitle(), parsedFeed.getImageLink(), parsedFeed.getLink(), parsedFeed.getDescription(), feedUrl, apiArticles);
    }

    private long getTimeForDate(@Nullable final Date date) {
        return (date != null) ? date.getTime() : currentTimeProvider.getCurrentTime();
    }
}
