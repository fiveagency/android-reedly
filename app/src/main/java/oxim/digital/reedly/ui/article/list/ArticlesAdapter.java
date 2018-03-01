package oxim.digital.reedly.ui.article.list;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import oxim.digital.reedly.R;
import oxim.digital.reedly.ui.model.ArticleViewModel;
import rx.Observable;
import rx.subjects.BehaviorSubject;
import rx.subjects.Subject;

public final class ArticlesAdapter extends RecyclerView.Adapter<ArticlesAdapter.ArticleViewHolder> {

    private static final long CLICK_THROTTLE_WINDOW_MILLIS = 300L;

    private List<ArticleViewModel> articleViewModels = new LinkedList<>();

    private final Subject<ArticleViewModel, ArticleViewModel> onItemClickSubject = BehaviorSubject.create();
    private final Subject<ArticleViewModel, ArticleViewModel> onItemFavouriteClickSubject = BehaviorSubject.create();

    @Override
    public ArticleViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        final View itemView = LayoutInflater.from(parent.getContext())
                                            .inflate(R.layout.article_list_item, parent, false);
        return new ArticleViewHolder(itemView, onItemClickSubject, onItemFavouriteClickSubject);
    }

    @Override
    public void onBindViewHolder(final ArticleViewHolder holder, final int position) {
        holder.setItem(articleViewModels.get(position));
    }

    @Override
    public int getItemCount() {
        return articleViewModels.size();
    }

    public void onFeedsUpdate(final List<ArticleViewModel> articleViewModels) {
        this.articleViewModels = articleViewModels;
        notifyDataSetChanged();
    }

    public Observable<ArticleViewModel> onItemClick() {
        return onItemClickSubject.throttleFirst(CLICK_THROTTLE_WINDOW_MILLIS, TimeUnit.MILLISECONDS)
                                 .doOnNext(this::markArticleAsRead);
    }

    private void markArticleAsRead(final ArticleViewModel readItem) {
        final int position = articleViewModels.indexOf(readItem);
        final ArticleViewModel newItem = new ArticleViewModel(false, readItem);
        articleViewModels.set(position, newItem);
        notifyItemChanged(position);
    }

    public Observable<ArticleViewModel> onFavouriteArticleClick() {
        return onItemFavouriteClickSubject.throttleFirst(CLICK_THROTTLE_WINDOW_MILLIS, TimeUnit.MILLISECONDS)
                                          .doOnNext(this::toggleArticleFavouriteStatus);
    }

    private void toggleArticleFavouriteStatus(final ArticleViewModel changedItem) {
        final int position = articleViewModels.indexOf(changedItem);
        final ArticleViewModel newArticle = new ArticleViewModel(changedItem, !changedItem.isFavourite);
        articleViewModels.set(position, newArticle);
        notifyItemChanged(position);
    }

    static final class ArticleViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.article_title)
        TextView title;

        @BindView(R.id.article_date)
        TextView publicationDate;

        @BindView(R.id.article_favourite_indicator)
        ImageView favouriteIndicator;

        @BindView(R.id.article_new_indicator)
        View newIndicator;

        private final Subject<ArticleViewModel, ArticleViewModel> clickSubject;
        private final Subject<ArticleViewModel, ArticleViewModel> favouriteClickSubject;

        private ArticleViewModel articleViewModel;

        public ArticleViewHolder(final View itemView, final Subject<ArticleViewModel, ArticleViewModel> clickSubject,
                                 final Subject<ArticleViewModel, ArticleViewModel> favouriteClickSubject) {
            super(itemView);
            this.clickSubject = clickSubject;
            this.favouriteClickSubject = favouriteClickSubject;
            ButterKnife.bind(this, itemView);
        }

        public void setItem(final ArticleViewModel articleViewModel) {
            this.articleViewModel = articleViewModel;
            title.setText(articleViewModel.title);
            publicationDate.setText(articleViewModel.publicationDate);
            favouriteIndicator.setImageResource(articleViewModel.isFavourite ? R.drawable.ic_favorite : R.drawable.ic_not_favorite);
            newIndicator.setVisibility(articleViewModel.isNew ? View.VISIBLE : View.GONE);
        }

        @OnClick(R.id.article_content_container)
        void onArticleClick() {
            clickSubject.onNext(articleViewModel);
        }

        @OnClick(R.id.article_favourite_indicator)
        void onFeedFavouriteArticleClick() {
            favouriteClickSubject.onNext(articleViewModel);
        }
    }
}
