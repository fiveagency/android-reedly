package oxim.digital.reedly.data.feed.service.parser;

import com.einmalfel.earl.Feed;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.zip.DataFormatException;

public interface ExternalParserWrapper {

    Feed parseOrThrow(InputStream inputStream, int maxItems) throws XmlPullParserException, IOException, DataFormatException;

    Feed parse(InputStream inputStream, int maxItems) throws XmlPullParserException, IOException, DataFormatException;
}
