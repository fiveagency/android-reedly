package oxim.digital.reedly.data.feed.service.model;

public final class ApiArticle {

    public final String title;
    public final String link;
    public final long publicationDate;

    public ApiArticle(final String title, final String link, final long publicationDate) {
        this.title = title;
        this.link = link;
        this.publicationDate = publicationDate;
    }
}
