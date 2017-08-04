package oxim.digital.reedly;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.einmalfel.earl.Feed;
import com.einmalfel.earl.Item;

import java.util.Date;
import java.util.List;

public final class TestFeed implements Feed {

    private final String link;
    private final Date publicationDate;
    private final String title;
    private final String description;
    private final String copyright;
    private final String imageLink;
    private final String author;
    private final List<? extends Item> items;

    public TestFeed(final String link, final Date publicationDate, final String title, final String description, final String copyright, final String imageLink,
                    final String author, final List<? extends Item> items) {
        this.link = link;
        this.publicationDate = publicationDate;
        this.title = title;
        this.description = description;
        this.copyright = copyright;
        this.imageLink = imageLink;
        this.author = author;
        this.items = items;
    }

    @Nullable
    @Override
    public String getLink() {
        return link;
    }

    @Nullable
    @Override
    public Date getPublicationDate() {
        return publicationDate;
    }

    @NonNull
    @Override
    public String getTitle() {
        return title;
    }

    @Nullable
    @Override
    public String getDescription() {
        return description;
    }

    @Nullable
    @Override
    public String getCopyright() {
        return copyright;
    }

    @Nullable
    @Override
    public String getImageLink() {
        return imageLink;
    }

    @Nullable
    @Override
    public String getAuthor() {
        return author;
    }

    @NonNull
    @Override
    public List<? extends Item> getItems() {
        return items;
    }
}
