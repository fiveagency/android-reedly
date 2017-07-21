package oxim.digital.reedly.ui.article.content;

import oxim.digital.reedly.base.BasePresenter;

public final class ArticleContentPresenter extends BasePresenter<ArticleContentContract.View> implements ArticleContentContract.Presenter {

    public ArticleContentPresenter(final ArticleContentContract.View view) {
        super(view);
    }
}
