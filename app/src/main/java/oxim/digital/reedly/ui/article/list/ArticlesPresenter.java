package oxim.digital.reedly.ui.article.list;

import java.util.List;

import javax.inject.Inject;

import oxim.digital.reedly.base.BasePresenter;
import oxim.digital.reedly.domain.interactor.article.GetArticlesUseCase;
import oxim.digital.reedly.domain.interactor.article.MarkArticleAsReadUseCase;
import oxim.digital.reedly.domain.interactor.article.favourite.FavouriteArticleUseCase;
import oxim.digital.reedly.domain.interactor.article.favourite.GetFavouriteArticlesUseCase;
import oxim.digital.reedly.domain.interactor.article.favourite.UnFavouriteArticleUseCase;
import oxim.digital.reedly.ui.mapper.FeedViewModeMapper;
import oxim.digital.reedly.ui.model.ArticleViewModel;
import rx.functions.Action1;

public final class ArticlesPresenter extends BasePresenter<ArticlesContract.View> implements ArticlesContract.Presenter {

    @Inject
    GetArticlesUseCase getArticlesUseCase;

    @Inject
    MarkArticleAsReadUseCase markArticleAsReadUseCase;

    @Inject
    FavouriteArticleUseCase favouriteArticleUseCase;

    @Inject
    UnFavouriteArticleUseCase unFavouriteArticleUseCase;

    @Inject
    GetFavouriteArticlesUseCase getFavouriteArticlesUseCase;

    @Inject
    FeedViewModeMapper feedViewModeMapper;

    public ArticlesPresenter(final ArticlesContract.View view) {
        super(view);
    }

    @Override
    public void fetchArticles(final int feedId) {
        viewActionQueue.subscribeTo(getArticlesUseCase.execute(feedId)
                                                      .map(feedViewModeMapper::mapArticlesToViewModels)
                                                      .map(this::toViewAction),
                                    Throwable::printStackTrace);
    }

    @Override
    public void fetchFavouriteArticles() {
        viewActionQueue.subscribeTo(getFavouriteArticlesUseCase.execute()
                                                               .map(feedViewModeMapper::mapArticlesToViewModels)
                                                               .map(this::toViewAction),
                                    Throwable::printStackTrace);
    }

    private Action1<ArticlesContract.View> toViewAction(final List<ArticleViewModel> articles) {
        return view -> view.showArticles(articles);
    }

    @Override
    public void showArticleContent(final ArticleViewModel articleViewModel) {
        router.showArticleContentScreen(articleViewModel.link);
    }

    @Override
    public void markArticleAsRead(final int articleId) {
        viewActionQueue.subscribeTo(markArticleAsReadUseCase.execute(articleId),
                                    view -> { },
                                    Throwable::printStackTrace);
    }

    @Override
    public void toggleArticleFavourite(final ArticleViewModel articleViewModel) {
        viewActionQueue.subscribeTo(articleViewModel.isFavourite ? unFavouriteArticleUseCase.execute(articleViewModel.id)
                                                                 : favouriteArticleUseCase.execute(articleViewModel.id),
                                    view -> { },
                                    Throwable::printStackTrace);
    }
}
