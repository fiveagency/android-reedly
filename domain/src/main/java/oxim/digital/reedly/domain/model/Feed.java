package oxim.digital.reedly.domain.model;

public final class Feed {

    public final int id;
    public final String title;
    public final String imageUrl;
    public final String pageLink;
    public final String description;
    public final String url;

    public Feed(final int id, final String title, final String imageUrl, final String pageLink, final String description, final String url) {
        this.id = id;
        this.title = title;
        this.imageUrl = imageUrl;
        this.pageLink = pageLink;
        this.description = description;
        this.url = url;
    }

    @Override
    public String toString() {
        return "Feed{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", pageLink='" + pageLink + '\'' +
                ", description='" + description + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
