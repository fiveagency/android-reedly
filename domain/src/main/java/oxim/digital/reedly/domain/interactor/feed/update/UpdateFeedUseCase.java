package oxim.digital.reedly.domain.interactor.feed.update;

import oxim.digital.reedly.domain.interactor.type.CompletableUseCaseWithParameter;
import oxim.digital.reedly.domain.model.Feed;
import oxim.digital.reedly.domain.repository.FeedRepository;
import rx.Completable;

public final class UpdateFeedUseCase implements CompletableUseCaseWithParameter<Feed> {

    private final FeedRepository feedRepository;

    public UpdateFeedUseCase(final FeedRepository feedRepository) {
        this.feedRepository = feedRepository;
    }

    @Override
    public Completable execute(final Feed feed) {
        return feedRepository.pullArticlesForFeedFromOrigin(feed);
    }
}
