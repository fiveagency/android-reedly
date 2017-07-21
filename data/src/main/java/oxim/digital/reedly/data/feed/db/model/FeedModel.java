package oxim.digital.reedly.data.feed.db.model;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.ConflictAction;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.annotation.Unique;
import com.raizlabs.android.dbflow.structure.BaseModel;

import oxim.digital.reedly.data.feed.db.definition.FeedDatabase;

@Table(database = FeedDatabase.class)
public final class FeedModel extends BaseModel {

    @Column
    @PrimaryKey(autoincrement = true)
    int id;

    @Column
    String title;

    @Column
    String imageUrl;

    @Column
    String pageLink;

    @Column
    String description;

    @Column
    @Unique(onUniqueConflict = ConflictAction.FAIL)
    String url;

    public FeedModel() { }

    public FeedModel(final String title, final String imageUrl, final String pageLink, final String description, final String url) {
        this(0, title, imageUrl, pageLink, description, url);
    }

    public FeedModel(final int id, final String title, final String imageUrl, final String pageLink, final String description, final String url) {
        this.id = id;
        this.title = title;
        this.imageUrl = imageUrl;
        this.pageLink = pageLink;
        this.description = description;
        this.url = url;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getPageLink() {
        return pageLink;
    }

    public String getDescription() {
        return description;
    }

    public String getUrl() {
        return url;
    }
}
