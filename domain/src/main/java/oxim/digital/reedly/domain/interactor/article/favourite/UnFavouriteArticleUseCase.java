package oxim.digital.reedly.domain.interactor.article.favourite;

import oxim.digital.reedly.domain.interactor.type.CompletableUseCaseWithParameter;
import oxim.digital.reedly.domain.repository.FeedRepository;
import rx.Completable;

public final class UnFavouriteArticleUseCase implements CompletableUseCaseWithParameter<Integer> {

    private final FeedRepository feedRepository;

    public UnFavouriteArticleUseCase(final FeedRepository feedRepository) {
        this.feedRepository = feedRepository;
    }

    @Override
    public Completable execute(final Integer articleId) {
        return feedRepository.unFavouriteArticle(articleId);
    }
}
