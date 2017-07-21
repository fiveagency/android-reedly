package oxim.digital.reedly.ui.article.list;

import java.util.List;

import oxim.digital.reedly.base.BaseView;
import oxim.digital.reedly.base.ScopedPresenter;
import oxim.digital.reedly.ui.model.ArticleViewModel;

public interface ArticlesContract {

    interface View extends BaseView {

        void showArticles(List<ArticleViewModel> articles);
    }

    interface Presenter extends ScopedPresenter {

        void fetchArticles(int feedId);

        void fetchFavouriteArticles();

        void showArticleContent(ArticleViewModel articleViewModel);

        void markArticleAsRead(int articleId);

        void toggleArticleFavourite(ArticleViewModel articleViewModel);
    }
}
