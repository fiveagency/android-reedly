package oxim.digital.reedly.dagger.fragment;

import oxim.digital.reedly.ui.article.content.ArticleContentFragment;
import oxim.digital.reedly.ui.article.content.ArticleContentPresenter;
import oxim.digital.reedly.ui.feed.create.NewFeedSubscriptionFragment;
import oxim.digital.reedly.ui.feed.create.NewFeedSubscriptionPresenter;
import oxim.digital.reedly.ui.article.list.ArticlesFragment;
import oxim.digital.reedly.ui.article.list.ArticlesPresenter;
import oxim.digital.reedly.ui.feed.subscription.UserSubscriptionsFragment;
import oxim.digital.reedly.ui.feed.subscription.UserSubscriptionsPresenter;

public interface FragmentComponentInjects {

    void inject(UserSubscriptionsFragment userSubscriptionsFragment);
    void inject(UserSubscriptionsPresenter userSubscriptionsPresenter);

    void inject(ArticlesFragment articlesFragment);
    void inject(ArticlesPresenter articlesPresenter);

    void inject(ArticleContentFragment articleContentFragment);
    void inject(ArticleContentPresenter articleContentPresenter);

    void inject(NewFeedSubscriptionFragment newFeedSubscriptionFragment);
    void inject(NewFeedSubscriptionPresenter newFeedSubscriptionPresenter);
}
