package oxim.digital.reedly.ui.feed.create;

import javax.inject.Inject;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import oxim.digital.reedly.R;
import oxim.digital.reedly.base.BaseFragment;
import oxim.digital.reedly.base.ScopedPresenter;
import oxim.digital.reedly.dagger.fragment.FragmentComponent;
import oxim.digital.reedly.domain.util.ActionRouter;
import oxim.digital.reedly.ui.view.TextWatcherAdapter;
import rx.functions.Action1;

public final class NewFeedSubscriptionFragment extends BaseFragment implements NewFeedSubscriptionContract.View {

    public static final String TAG = NewFeedSubscriptionFragment.class.getSimpleName();

    private static final String EMPTY = "";

    @Inject
    NewFeedSubscriptionContract.Presenter presenter;

    @Inject
    ActionRouter actionRouter;

    @BindView(R.id.new_feed_subscription_content_container)
    CardView newFeedSubscriptionContentContainer;

    @BindView(R.id.dialog_background)
    View dialogBackground;

    @BindView(R.id.feed_url_input)
    TextInputEditText feedUrlInput;

    @BindView(R.id.message_text_view)
    TextView messageTextView;

    @BindView(R.id.loading_indicator)
    View loadingIndicator;

    @BindView(R.id.add_feed_button)
    Button addNewFeedButton;

    private Unbinder unbinder;

    public static NewFeedSubscriptionFragment newInstance() {
        return new NewFeedSubscriptionFragment();
    }

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable final Bundle savedInstanceState) {
        final View fragmentView = inflater.inflate(R.layout.fragment_new_feed_subscription, container, false);
       unbinder = ButterKnife.bind(this, fragmentView);
        return fragmentView;
    }

    @Override
    public void onViewCreated(final View view, @Nullable final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        Need inspiration?
//        feedUrlInput.setText("https://xkcd.com/rss.xml");
        feedUrlInput.setText("https://www.reddit.com/r/androiddev/new.rss");
//        feedUrlInput.setText("http://android-developers.blogspot.com/feeds/posts/default?alt=rss");

        feedUrlInput.addTextChangedListener(new ActionTextWatcher(text -> clearMessage()));
    }

    @Override
    public ScopedPresenter getPresenter() {
        return presenter;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void showMessage(final String message) {
        messageTextView.setText(message);
    }

    @Override
    public void clearMessage() {
        messageTextView.setText(EMPTY);
    }

    @Override
    public void showIsLoading(final boolean isLoading) {
        messageTextView.setVisibility(isLoading ? View.GONE : View.VISIBLE);
        loadingIndicator.setVisibility(isLoading ? View.VISIBLE : View.GONE);
        addNewFeedButton.setEnabled(!isLoading);

        if (isLoading) {
            actionRouter.blockActions();
        } else {
            actionRouter.unblockActions();
        }
    }

    @Override
    protected void inject(final FragmentComponent fragmentComponent) {
        fragmentComponent.inject(this);
    }

    @OnClick(R.id.add_feed_button)
    public void onAddFeedButtonClick() {
        presenter.addNewFeed(feedUrlInput.getText().toString());
    }

    @OnClick(R.id.dialog_background)
    public void onDialogBackgroundClick() {
        actionRouter.throttle(() -> presenter.back());
    }

    private static final class ActionTextWatcher extends TextWatcherAdapter {

        private final Action1<CharSequence> onTextChangedAction;

        public ActionTextWatcher(final Action1<CharSequence> onTextChangedAction) {
            this.onTextChangedAction = onTextChangedAction;
        }

        @Override
        public void onTextChanged(final CharSequence charSequence, final int start, final int before, final int count) {
            onTextChangedAction.call(charSequence);
        }
    }
}
