package oxim.digital.reedly.domain.interactor.article;

import oxim.digital.reedly.domain.interactor.type.SingleUseCase;
import oxim.digital.reedly.domain.repository.FeedRepository;
import rx.Single;

public final class GetUnreadArticlesCountUseCase implements SingleUseCase<Long> {

    private final FeedRepository feedRepository;

    public GetUnreadArticlesCountUseCase(final FeedRepository feedRepository) {
        this.feedRepository = feedRepository;
    }

    @Override
    public Single<Long> execute() {
        return feedRepository.getUnreadArticlesCount();
    }
}
