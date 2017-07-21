package oxim.digital.reedly.ui.article.list;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import oxim.digital.reedly.R;
import oxim.digital.reedly.base.BaseFragment;
import oxim.digital.reedly.base.ScopedPresenter;
import oxim.digital.reedly.dagger.fragment.FragmentComponent;
import oxim.digital.reedly.ui.model.ArticleViewModel;

public final class ArticlesFragment extends BaseFragment implements ArticlesContract.View {

    public static final String TAG = ArticlesFragment.class.getSimpleName();

    private static final String KEY_ARTICLES_FEED_ID = "key_articles_feed_id";
    private static final String KEY_FEED_TITLE = "key_feed_title";
    private static final String KEY_FAVOURITE_ARTICLES = "key_favourite_articles";

    @Inject
    ArticlesContract.Presenter presenter;

    @Inject
    Resources resources;

    @Bind(R.id.feedTitle)
    TextView feedTitle;

    @Bind(R.id.articles_recycler_view)
    RecyclerView articlesRecyclerView;

    private RecyclerView.LayoutManager articlesLayoutManager;
    private ArticlesAdapter articlesAdapter;

    public static ArticlesFragment newInstance(final int feedId, final String feedTitle) {
        final ArticlesFragment fragment = new ArticlesFragment();
        final Bundle arguments = new Bundle();
        arguments.putInt(KEY_ARTICLES_FEED_ID, feedId);
        arguments.putString(KEY_FEED_TITLE, feedTitle);
        fragment.setArguments(arguments);
        return fragment;
    }

    public static ArticlesFragment newFavouriteArticlesInstance() {
        final ArticlesFragment fragment = new ArticlesFragment();
        final Bundle arguments = new Bundle();
        arguments.putBoolean(KEY_FAVOURITE_ARTICLES, true);
        fragment.setArguments(arguments);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable final Bundle savedInstanceState) {
        final View fragmentView = inflater.inflate(R.layout.fragment_articles, container, false);
        ButterKnife.bind(this, fragmentView);
        return fragmentView;
    }

    @Override
    public void onViewCreated(final View view, @Nullable final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        extractArguments(getArguments());
        initializeRecyclerView();
    }

    private void extractArguments(final Bundle arguments) {
        if (arguments.getBoolean(KEY_FAVOURITE_ARTICLES, false)) {
            setFavouriteItems();
        } else {
            updateFeed(arguments.getInt(KEY_ARTICLES_FEED_ID), arguments.getString(KEY_FEED_TITLE, resources.getString(R.string.app_name)));
        }
    }

    public void updateFeed(final int feedId, final String feedTitle) {
        updateTitle(feedTitle);
        presenter.fetchArticles(feedId);
    }

    private void updateTitle(final String feedTitle) {
        this.feedTitle.setText(feedTitle);
    }

    public void setFavouriteItems() {
        presenter.fetchFavouriteArticles();
    }

    @Override
    public void showArticles(final List<ArticleViewModel> articles) {
        articlesAdapter.onFeedsUpdate(articles);
    }

    private void initializeRecyclerView() {
        if (articlesRecyclerView.getAdapter() == null) {
            articlesAdapter = new ArticlesAdapter();
            articlesRecyclerView.setAdapter(articlesAdapter);
        } else {
            articlesAdapter = (ArticlesAdapter) articlesRecyclerView.getAdapter();
        }
        articlesAdapter.onItemClick()
                       .subscribe(this::onArticleSelected);
        articlesAdapter.onFavouriteArticleClick()
                       .subscribe(this::onArticleFavouriteChanged);
        articlesLayoutManager = new LinearLayoutManager(null);
        articlesRecyclerView.setLayoutManager(articlesLayoutManager);
    }

    private void onArticleSelected(final ArticleViewModel articleViewModel) {
        presenter.markArticleAsRead(articleViewModel.id);
        presenter.showArticleContent(articleViewModel);
    }

    private void onArticleFavouriteChanged(final ArticleViewModel articleViewModel) {
        presenter.toggleArticleFavourite(articleViewModel);
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
    protected void inject(final FragmentComponent fragmentComponent) {
        fragmentComponent.inject(this);
    }
}
