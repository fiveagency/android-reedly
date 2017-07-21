package oxim.digital.reedly.ui.feed.subscription;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.annimon.stream.Stream;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnLongClick;
import oxim.digital.reedly.R;
import oxim.digital.reedly.ui.model.FeedViewModel;
import oxim.digital.reedly.ui.view.RevealFillView;
import oxim.digital.reedly.util.ImageLoader;
import rx.Observable;
import rx.subjects.BehaviorSubject;
import rx.subjects.Subject;

public final class FeedAdapter extends RecyclerView.Adapter<FeedAdapter.FeedViewHolder> {

    private static final long CLICK_THROTTLE_WINDOW_MILLIS = 300L;

    private final ImageLoader imageLoader;

    private List<FeedViewModel> feedViewModels = new ArrayList<>();

    private final Subject<FeedViewModel, FeedViewModel> onItemClickSubject = BehaviorSubject.create();
    private final Subject<FeedViewModel, FeedViewModel> onItemLongClickSubject = BehaviorSubject.create();

    public FeedAdapter(final ImageLoader imageLoader) {
        this.imageLoader = imageLoader;
    }

    @Override
    public FeedViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        final View itemView = LayoutInflater.from(parent.getContext())
                                            .inflate(R.layout.feed_list_item, parent, false);
        return new FeedViewHolder(itemView, imageLoader, onItemClickSubject, onItemLongClickSubject);
    }

    @Override
    public void onBindViewHolder(final FeedViewHolder holder, final int position) {
        holder.setItem(feedViewModels.get(position));
    }

    @Override
    public int getItemCount() {
        return feedViewModels.size();
    }

    public void onFeedsUpdate(final List<FeedViewModel> feedViewModels) {
        this.feedViewModels = feedViewModels;
        notifyDataSetChanged();
    }

    public void selectFeed(final FeedViewModel feedViewModel) {
        final int position = feedViewModels.indexOf(feedViewModel);
        final FeedViewModel newItem = new FeedViewModel(feedViewModel, true);
        feedViewModels.set(position, newItem);
        notifyItemChanged(position);
    }

    public void clearSelection() {
        Stream.of(feedViewModels)
              .filter(feedViewModel -> feedViewModel.isSelected)
              .forEach(this::unSelectFeed);
    }

    public boolean hasSelectedItem() {
        return Stream.of(feedViewModels)
                     .anyMatch(feedViewModel -> feedViewModel.isSelected);
    }

    private void unSelectFeed(final FeedViewModel feedViewModel) {
        final int position = feedViewModels.indexOf(feedViewModel);
        final FeedViewModel newItem = new FeedViewModel(feedViewModel, false);
        feedViewModels.set(position, newItem);
        notifyItemChanged(position);
    }

    public Observable<FeedViewModel> onItemClick() {
        return onItemClickSubject.throttleFirst(CLICK_THROTTLE_WINDOW_MILLIS, TimeUnit.MILLISECONDS);
    }

    public Observable<FeedViewModel> onItemLongClick() {
        return onItemLongClickSubject.throttleFirst(CLICK_THROTTLE_WINDOW_MILLIS, TimeUnit.MILLISECONDS);
    }

    static final class FeedViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.selected_indicator_view)
        RevealFillView selectionIndicator;

        @Bind(R.id.feed_image)
        ImageView feedImage;

        @Bind(R.id.feed_title)
        TextView feedTitle;

        @Bind(R.id.feed_description)
        TextView feedDescription;

        private final ImageLoader imageLoader;

        private final Subject<FeedViewModel, FeedViewModel> clickSubject;
        private final Subject<FeedViewModel, FeedViewModel> longClickSubject;

        private FeedViewModel feedViewModel;

        public FeedViewHolder(final View itemView, final ImageLoader imageLoader,
                              final Subject<FeedViewModel, FeedViewModel> clickSubject,
                              final Subject<FeedViewModel, FeedViewModel> longClickSubject) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            this.imageLoader = imageLoader;
            this.clickSubject = clickSubject;
            this.longClickSubject = longClickSubject;
        }

        public void setItem(final FeedViewModel feedViewModel) {
            this.feedViewModel = feedViewModel;
            imageLoader.loadImage(feedViewModel.imageUrl, feedImage, R.drawable.feed_icon, R.drawable.feed_icon);
            feedTitle.setText(feedViewModel.title);
            feedDescription.setText(feedViewModel.description);
            if (feedViewModel.isSelected) {
                selectionIndicator.startFillAnimation();
            } else {
                selectionIndicator.startHideAnimation();
            }
        }

        @OnClick(R.id.feed_content_container)
        void onFeedClick() {
            clickSubject.onNext(feedViewModel);
        }

        @OnLongClick(R.id.feed_content_container)
        boolean onFeedLongClick() {
            longClickSubject.onNext(feedViewModel);
            return true;
        }
    }
}
