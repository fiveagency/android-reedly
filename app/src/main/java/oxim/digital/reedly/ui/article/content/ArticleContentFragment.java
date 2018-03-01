package oxim.digital.reedly.ui.article.content;

import javax.inject.Inject;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import oxim.digital.reedly.R;
import oxim.digital.reedly.base.BaseFragment;
import oxim.digital.reedly.base.ScopedPresenter;
import oxim.digital.reedly.dagger.fragment.FragmentComponent;

public final class ArticleContentFragment extends BaseFragment implements ArticleContentContract.View {

    public static final String TAG = ArticleContentFragment.class.getSimpleName();

    private static final String KEY_ARTICLE_CONTENT_URL = "key_article_content_url";

    @Inject
    ArticleContentContract.Presenter presenter;

    @BindView(R.id.article_content_web_view)
    WebView articleContentWebView;

    private Unbinder unbinder;

    public static ArticleContentFragment newInstance(final String articleContentUrl) {
        final ArticleContentFragment fragment = new ArticleContentFragment();
        final Bundle arguments = new Bundle();
        arguments.putString(KEY_ARTICLE_CONTENT_URL, articleContentUrl);
        fragment.setArguments(arguments);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable final Bundle savedInstanceState) {
        final View fragmentView = inflater.inflate(R.layout.fragment_article_content, container, false);
        unbinder = ButterKnife.bind(this, fragmentView);
        return fragmentView;
    }

    @Override
    public void onViewCreated(final View view, @Nullable final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        extractArguments(getArguments());
    }

    private void extractArguments(final Bundle arguments) {
        setContentUrl(arguments.getString(KEY_ARTICLE_CONTENT_URL));
    }

    private void setupWebView(final String url) {
        articleContentWebView.setWebViewClient(new ArticleWebClient(url));
        articleContentWebView.getSettings().setJavaScriptEnabled(true);
        articleContentWebView.loadUrl(url);
    }

    public void setContentUrl(final String url) {
        setupWebView(url);
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
    protected void inject(final FragmentComponent fragmentComponent) {
        fragmentComponent.inject(this);
    }
}
