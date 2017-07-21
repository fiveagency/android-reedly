package oxim.digital.reedly.data.feed.db.model;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.ConflictAction;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.annotation.Unique;
import com.raizlabs.android.dbflow.annotation.UniqueGroup;
import com.raizlabs.android.dbflow.structure.BaseModel;

import oxim.digital.reedly.data.feed.db.definition.FeedDatabase;

@Table(database = FeedDatabase.class,
        uniqueColumnGroups = @UniqueGroup(groupNumber = ArticleModel.UNIQUE_GROUP_ID, uniqueConflict = ConflictAction.IGNORE))
public final class ArticleModel extends BaseModel {

    static final int UNIQUE_GROUP_ID = 100;

    @Column
    @PrimaryKey(autoincrement = true)
    int id;

    @Column
    @Unique(unique = false, uniqueGroups = UNIQUE_GROUP_ID)
    int feedId;

    @Column
    String title;

    @Column
    @Unique(unique = false, uniqueGroups = UNIQUE_GROUP_ID)
    String link;

    @Column
    long publicationDate;

    @Column
    boolean isNew;

    @Column
    boolean isFavourite;

    public ArticleModel() { }

    public ArticleModel(final int feedId, final String title, final String link, final long publicationDate) {
        this(feedId, title, link, publicationDate, true, false);
    }

    public ArticleModel(final int feedId, final String title, final String link, final long publicationDate, final boolean isNew, final boolean isFavourite) {
        this.feedId = feedId;
        this.title = title;
        this.link = link;
        this.publicationDate = publicationDate;
        this.isNew = isNew;
        this.isFavourite = isFavourite;
    }

    public int getId() {
        return id;
    }

    public int getFeedId() {
        return feedId;
    }

    public String getTitle() {
        return title;
    }

    public String getLink() {
        return link;
    }

    public long getPublicationDate() {
        return publicationDate;
    }

    public boolean isNew() {
        return isNew;
    }

    public boolean isFavourite() {
        return isFavourite;
    }
}
