package oxim.digital.reedly.domain.model;

public final class Article {

    public final int id;
    public final int feedId;
    public final String title;
    public final String link;
    public final long publicationDate;

    public final boolean isNew;
    public final boolean isFavourite;

    public Article(final int id, final int feedId, final String title, final String link, final long publicationDate, final boolean isNew, final boolean isFavourite) {
        this.id = id;
        this.feedId = feedId;
        this.title = title;
        this.link = link;
        this.publicationDate = publicationDate;
        this.isNew = isNew;
        this.isFavourite = isFavourite;
    }

    @Override
    public String toString() {
        return "Article{" +
                "id=" + id +
                ", feedId=" + feedId +
                ", title='" + title + '\'' +
                ", link='" + link + '\'' +
                ", publicationDate=" + publicationDate +
                '}';
    }
}
