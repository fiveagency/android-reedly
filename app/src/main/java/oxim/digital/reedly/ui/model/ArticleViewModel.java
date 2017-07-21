package oxim.digital.reedly.ui.model;

public final class ArticleViewModel {

    public final int id;
    public final String title;
    public final String link;
    public final String publicationDate;

    public final boolean isNew;
    public final boolean isFavourite;

    public ArticleViewModel(final int id, final String title, final String link, final String publicationDate, final boolean isNew, final boolean isFavourite) {
        this.id = id;
        this.title = title;
        this.link = link;
        this.publicationDate = publicationDate;
        this.isNew = isNew;
        this.isFavourite = isFavourite;
    }

    public ArticleViewModel(final boolean isNew, final ArticleViewModel model) {
        this(model.id, model.title, model.link, model.publicationDate, isNew, model.isFavourite);
    }

    public ArticleViewModel(final ArticleViewModel model, final boolean isFavourite) {
        this(model.id, model.title, model.link, model.publicationDate, model.isNew, isFavourite);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        final ArticleViewModel that = (ArticleViewModel) o;

        return id == that.id;
    }

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public String toString() {
        return "ArticleViewModel{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", link='" + link + '\'' +
                ", publicationDate='" + publicationDate + '\'' +
                ", isNew=" + isNew +
                ", isFavourite=" + isFavourite +
                '}';
    }
}
