package oxim.digital.reedly.ui.mapper;

import com.annimon.stream.Stream;

import java.util.List;

import oxim.digital.reedly.domain.model.Article;
import oxim.digital.reedly.domain.model.Feed;
import oxim.digital.reedly.ui.model.ArticleViewModel;
import oxim.digital.reedly.ui.model.FeedViewModel;
import oxim.digital.reedly.util.DateUtils;

public final class FeedViewModelMapperImpl implements FeedViewModeMapper {

    private final DateUtils dateUtils;

    public FeedViewModelMapperImpl(final DateUtils dateUtils) {
        this.dateUtils = dateUtils;
    }

    @Override
    public FeedViewModel mapFeedToViewModel(final Feed feed) {
        return new FeedViewModel(feed.id, feed.title, feed.imageUrl, feed.pageLink, feed.description);
    }

    @Override
    public List<FeedViewModel> mapFeedsToViewModels(final List<Feed> feeds) {
        return Stream.of(feeds)
                     .map(this::mapFeedToViewModel)
                     .toList();
    }

    @Override
    public ArticleViewModel mapArticleToViewModel(final Article article) {
        return new ArticleViewModel(article.id, article.title, article.link, dateUtils.format(article.publicationDate, DateUtils.SHORT_DATE_FORMAT), article.isNew, article.isFavourite);
    }

    @Override
    public List<ArticleViewModel> mapArticlesToViewModels(final List<Article> articles) {
        return Stream.of(articles)
                     .map(this::mapArticleToViewModel)
                     .toList();
    }
}
