package oxim.digital.reedly.data.feed.service.parser;

import com.einmalfel.earl.EarlParser;
import com.einmalfel.earl.Feed;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.zip.DataFormatException;

public final class EarlParserWrapper implements ExternalParserWrapper {

    @Override
    public Feed parseOrThrow(final InputStream inputStream, final int maxItems) throws XmlPullParserException, IOException, DataFormatException {
        return EarlParser.parseOrThrow(inputStream, maxItems);
    }

    @Override
    public Feed parse(final InputStream inputStream, final int maxItems) throws XmlPullParserException, IOException, DataFormatException {
        return EarlParser.parse(inputStream, maxItems);
    }
}
