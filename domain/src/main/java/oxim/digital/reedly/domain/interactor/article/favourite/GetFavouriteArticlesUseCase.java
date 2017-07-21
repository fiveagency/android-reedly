package oxim.digital.reedly.domain.interactor.article.favourite;

import java.util.List;

import oxim.digital.reedly.domain.interactor.type.SingleUseCase;
import oxim.digital.reedly.domain.model.Article;
import oxim.digital.reedly.domain.repository.FeedRepository;
import rx.Single;

public final class GetFavouriteArticlesUseCase implements SingleUseCase<List<Article>>{

    private final FeedRepository feedRepository;

    public GetFavouriteArticlesUseCase(final FeedRepository feedRepository) {
        this.feedRepository = feedRepository;
    }

    @Override
    public Single<List<Article>> execute() {
        return feedRepository.getFavouriteArticles();
    }
}
