package oxim.digital.reedly;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.einmalfel.earl.Enclosure;
import com.einmalfel.earl.Item;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public final class TestItem implements Item {

    private final String link;
    private final Date publicationDate;
    private final String title;
    private final String description;
    private final String imageLink;
    private final String author;
    private final String id;
    private final List<Enclosure> enclosures;

    public TestItem(final String link, final Date publicationDate, final String title, final String description, final String imageLink, final String author, final String id,
                    final List<Enclosure> enclosures) {
        this.link = link;
        this.publicationDate = publicationDate;
        this.title = title;
        this.description = description;
        this.imageLink = imageLink;
        this.author = author;
        this.id = id;
        this.enclosures = enclosures;
    }

    public TestItem(final String link, final Date publicationDate, final String title) {
        this.link = link;
        this.publicationDate = publicationDate;
        this.title = title;

        //not used
        this.description = null;
        this.imageLink = null;
        this.author = null;
        this.id = null;
        this.enclosures = new ArrayList<>();
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

    @Nullable
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
    public String getImageLink() {
        return imageLink;
    }

    @Nullable
    @Override
    public String getAuthor() {
        return author;
    }

    @Nullable
    @Override
    public String getId() {
        return id;
    }

    @NonNull
    @Override
    public List<Enclosure> getEnclosures() {
        return enclosures;
    }
}
