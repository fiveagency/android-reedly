package oxim.digital.reedly.ui.model;

public final class FeedViewModel {

    public static final FeedViewModel EMPTY = new FeedViewModel(0, "", "", "", "");

    public final int id;
    public final String title;
    public final String imageUrl;
    public final String link;
    public final String description;

    public final boolean isSelected;

    public FeedViewModel(final int id, final String title, final String imageUrl, final String link, final String description, final boolean isSelected) {
        this.id = id;
        this.title = title;
        this.imageUrl = imageUrl;
        this.link = link;
        this.description = description;
        this.isSelected = isSelected;
    }

    public FeedViewModel(final int id, final String title, final String imageUrl, final String link, final String description) {
        this(id, title, imageUrl, link, description, false);
    }

    public FeedViewModel(final FeedViewModel feedViewModel, final boolean isSelected) {
        this(feedViewModel.id, feedViewModel.title, feedViewModel.imageUrl, feedViewModel.link, feedViewModel.description, isSelected);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        final FeedViewModel that = (FeedViewModel) o;

        return id == that.id;
    }

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public String toString() {
        return "FeedViewModel{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", link='" + link + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
