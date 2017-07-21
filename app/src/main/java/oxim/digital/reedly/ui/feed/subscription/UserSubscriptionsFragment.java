package oxim.digital.reedly.ui.feed.subscription;

import android.content.res.ColorStateList;
import android.os.Bundle;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.lang.annotation.Retention;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import oxim.digital.reedly.R;
import oxim.digital.reedly.base.BaseFragment;
import oxim.digital.reedly.base.ScopedPresenter;
import oxim.digital.reedly.dagger.fragment.FragmentComponent;
import oxim.digital.reedly.ui.model.FeedViewModel;
import oxim.digital.reedly.util.ImageLoader;

import static java.lang.annotation.RetentionPolicy.SOURCE;

public final class UserSubscriptionsFragment extends BaseFragment implements UserSubscriptionsContract.View {

    public static final String TAG = UserSubscriptionsFragment.class.getSimpleName();

    private static final int ADD_FEED = 100;
    private static final int DELETE_FEED = 200;

    @Retention(SOURCE)
    @IntDef({ADD_FEED, DELETE_FEED})
    private @interface ViewState { }

    @Inject
    UserSubscriptionsContract.Presenter presenter;

    @Inject
    ImageLoader imageLoader;

    @Bind(R.id.user_feeds_recycler_view)
    RecyclerView userFeedsRecyclerView;

    @Bind(R.id.empty_state_view)
    View emptyStateView;

    @Bind(R.id.add_new_feed_button)
    FloatingActionButton actionButton;

    @Bind(R.id.toggle_notifications_button)
    ImageView backgroundUpdatesNotification;

    private RecyclerView.LayoutManager feedsLayoutManager;
    private FeedAdapter feedAdapter;

    private FeedViewModel selectedFeedModel = FeedViewModel.EMPTY;
    private boolean isBackgroundFeedUpdateEnabled;

    @ViewState
    private int viewState = ADD_FEED;

    public static UserSubscriptionsFragment newInstance() {
        return new UserSubscriptionsFragment();
    }

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable final Bundle savedInstanceState) {
        final View fragmentView = inflater.inflate(R.layout.fragment_user_subscriptions, container, false);
        ButterKnife.bind(this, fragmentView);
        return fragmentView;
    }

    @Override
    public void onViewCreated(final View view, @Nullable final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initializeRecyclerView();
    }

    private void initializeRecyclerView() {
        if (userFeedsRecyclerView.getAdapter() == null) {
            feedAdapter = new FeedAdapter(imageLoader);
            userFeedsRecyclerView.setAdapter(feedAdapter);
        } else {
            feedAdapter = (FeedAdapter) userFeedsRecyclerView.getAdapter();
        }
        feedAdapter.onItemClick()
                   .subscribe(this::onFeedClicked);
        feedAdapter.onItemLongClick()
                   .subscribe(this::onFeedSelected);
        feedsLayoutManager = new LinearLayoutManager(null);             // TODO - inject this, save its state on orientation change
        userFeedsRecyclerView.setLayoutManager(feedsLayoutManager);
    }

    private void onFeedClicked(final FeedViewModel feedViewModel) {
        if (viewState == DELETE_FEED) {
            selectedFeedModel = FeedViewModel.EMPTY;
            setViewState(ADD_FEED);
            feedAdapter.clearSelection();
        } else {
            presenter.showArticles(feedViewModel);
        }
    }

    private void onFeedSelected(final FeedViewModel feedViewModel) {
        feedAdapter.clearSelection();
        selectedFeedModel = feedViewModel;
        setViewState(DELETE_FEED);
        feedAdapter.selectFeed(selectedFeedModel);
    }

    private void setViewState(@ViewState final int viewState) {
        this.viewState = viewState;
        if (viewState == ADD_FEED) {
            setupAddFeedViewState();
        } else {
            setupDeleteFeedViewState();
        }
    }

    private void setupAddFeedViewState() {
        actionButton.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorAccent)));
        actionButton.setImageResource(R.drawable.ic_add);
    }

    private void setupDeleteFeedViewState() {
        actionButton.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.dangerRed)));
        actionButton.setImageResource(R.drawable.ic_delete);
    }

    public void refreshUserSubscriptions() {
        presenter.updateUserSubscriptions();
    }

    @Override
    public ScopedPresenter getPresenter() {
        return presenter;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void showFeedSubscriptions(final List<FeedViewModel> feedSubscriptions) {
        setViewState(ADD_FEED);
        feedAdapter.onFeedsUpdate(feedSubscriptions);
        adjustEmptyState(feedSubscriptions.isEmpty());
    }

    @Override
    public void setIsBackgroundFeedUpdateEnabled(final boolean isEnabled) {
        isBackgroundFeedUpdateEnabled = isEnabled;
        backgroundUpdatesNotification.setImageResource(isEnabled ? R.drawable.ic_active_notification : R.drawable.ic_inactive_notification);
    }

    private void adjustEmptyState(final boolean isViewEmpty) {
        emptyStateView.setVisibility(isViewEmpty ? View.VISIBLE : View.GONE);
    }

    @Override
    protected void inject(final FragmentComponent fragmentComponent) {
        fragmentComponent.inject(this);
    }

    @Override
    public boolean onBack() {
        if (viewState == ADD_FEED) {
            return super.onBack();
        }
        selectedFeedModel = FeedViewModel.EMPTY;
        setViewState(ADD_FEED);
        feedAdapter.clearSelection();
        return true;
    }

    @OnClick(R.id.add_new_feed_button)
    public void onSubscriptionsButtonClick() {
        if (viewState == ADD_FEED) {
            presenter.showAddNewFeed();
        } else {
            feedAdapter.clearSelection();
            presenter.unSubscribeFromFeed(selectedFeedModel);
        }
    }

    @OnClick(R.id.show_favourites_button)
    public void onShowFavouriteArticlesButtonClick() {
        presenter.showFavouriteArticles();
    }

    @OnClick(R.id.toggle_notifications_button)
    public void onToggleBackgroundFeedsUpdateButtonClick() {
        if (isBackgroundFeedUpdateEnabled) {
            presenter.disableBackgroundFeedUpdates();
        } else {
            presenter.enableBackgroundFeedUpdates();
        }
    }
}
