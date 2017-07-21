package oxim.digital.reedly.ui.mapper;

import java.util.List;

import oxim.digital.reedly.domain.model.Feed;
import oxim.digital.reedly.domain.model.Article;
import oxim.digital.reedly.ui.model.ArticleViewModel;
import oxim.digital.reedly.ui.model.FeedViewModel;

public interface FeedViewModeMapper {

    FeedViewModel mapFeedToViewModel(Feed feed);

    List<FeedViewModel> mapFeedsToViewModels(List<Feed> feeds);

    ArticleViewModel mapArticleToViewModel(Article article);

    List<ArticleViewModel> mapArticlesToViewModels(List<Article> articles);
}
