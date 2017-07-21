package oxim.digital.reedly.domain.interactor.article;

import oxim.digital.reedly.domain.interactor.type.CompletableUseCaseWithParameter;
import oxim.digital.reedly.domain.repository.FeedRepository;
import rx.Completable;

public final class MarkArticleAsReadUseCase implements CompletableUseCaseWithParameter<Integer> {

    private final FeedRepository feedRepository;

    public MarkArticleAsReadUseCase(final FeedRepository feedRepository) {
        this.feedRepository = feedRepository;
    }

    @Override
    public Completable execute(final Integer articleId) {
        return feedRepository.markArticleAsRead(articleId);
    }
}
