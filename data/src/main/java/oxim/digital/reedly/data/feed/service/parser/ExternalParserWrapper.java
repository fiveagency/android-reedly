package oxim.digital.reedly.data.feed.service.parser;

import java.io.InputStream;

import oxim.digital.reedly.data.feed.service.model.ApiFeed;

public interface ExternalParserWrapper {

    ApiFeed parseOrThrow(final InputStream inputStream, final String feedUrl) throws Exception;

    ApiFeed parse(final InputStream inputStream, final String feedUrl);
}
