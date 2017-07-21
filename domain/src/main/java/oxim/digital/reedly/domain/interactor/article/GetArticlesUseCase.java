package oxim.digital.reedly.domain.interactor.article;

import java.util.List;

import oxim.digital.reedly.domain.interactor.type.SingleUseCaseWithParameter;
import oxim.digital.reedly.domain.model.Article;
import oxim.digital.reedly.domain.repository.FeedRepository;
import rx.Single;

public final class GetArticlesUseCase implements SingleUseCaseWithParameter<Integer, List<Article>> {

    // TODO Pagination

    private final FeedRepository feedRepository;

    public GetArticlesUseCase(final FeedRepository feedRepository) {
        this.feedRepository = feedRepository;
    }

    @Override
    public Single<List<Article>> execute(final Integer feedId) {
        return feedRepository.getArticles(feedId);
    }
}
