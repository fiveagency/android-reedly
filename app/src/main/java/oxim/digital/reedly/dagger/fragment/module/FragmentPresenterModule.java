package oxim.digital.reedly.dagger.fragment.module;

import dagger.Module;
import dagger.Provides;
import oxim.digital.reedly.dagger.fragment.DaggerFragment;
import oxim.digital.reedly.dagger.fragment.FragmentComponent;
import oxim.digital.reedly.dagger.fragment.FragmentScope;
import oxim.digital.reedly.ui.article.content.ArticleContentContract;
import oxim.digital.reedly.ui.article.content.ArticleContentPresenter;
import oxim.digital.reedly.ui.feed.create.NewFeedSubscriptionContract;
import oxim.digital.reedly.ui.feed.create.NewFeedSubscriptionPresenter;
import oxim.digital.reedly.ui.article.list.ArticlesContract;
import oxim.digital.reedly.ui.article.list.ArticlesPresenter;
import oxim.digital.reedly.ui.feed.subscription.UserSubscriptionsContract;
import oxim.digital.reedly.ui.feed.subscription.UserSubscriptionsPresenter;

@Module
public final class FragmentPresenterModule {

    private final DaggerFragment daggerFragment;

    public FragmentPresenterModule(final DaggerFragment daggerFragment) {
        this.daggerFragment = daggerFragment;
    }

    private FragmentComponent getFragmentComponent() {
        return daggerFragment.getFragmentComponent();
    }

    @Provides
    @FragmentScope
    public UserSubscriptionsContract.Presenter provideUserSubscriptionsPresenter() {
        final UserSubscriptionsPresenter userSubscriptionsPresenter = new UserSubscriptionsPresenter((UserSubscriptionsContract.View) daggerFragment);
        getFragmentComponent().inject(userSubscriptionsPresenter);
        return userSubscriptionsPresenter;
    }

    @Provides
    @FragmentScope
    public ArticlesContract.Presenter provideArticlesPresenter() {
        final ArticlesPresenter articlesPresenter = new ArticlesPresenter((ArticlesContract.View) daggerFragment);
        getFragmentComponent().inject(articlesPresenter);
        return articlesPresenter;
    }

    @Provides
    @FragmentScope
    public ArticleContentContract.Presenter provideArticleContentPresenter() {
        final ArticleContentPresenter articleContentPresenter = new ArticleContentPresenter((ArticleContentContract.View) daggerFragment);
        getFragmentComponent().inject(articleContentPresenter);
        return articleContentPresenter;
    }

    @Provides
    @FragmentScope
    public NewFeedSubscriptionContract.Presenter provideNewFeedSubscriptionPresenter() {
        final NewFeedSubscriptionPresenter newFeedSubscriptionPresenter = new NewFeedSubscriptionPresenter((NewFeedSubscriptionContract.View) daggerFragment);
        getFragmentComponent().inject(newFeedSubscriptionPresenter);
        return newFeedSubscriptionPresenter;
    }
}
