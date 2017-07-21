package oxim.digital.reedly.domain.interactor.feed;

import oxim.digital.reedly.domain.interactor.type.SingleUseCaseWithParameter;
import oxim.digital.reedly.domain.repository.FeedRepository;
import rx.Single;

public final class IsUserSubscribedToFeedUseCase implements SingleUseCaseWithParameter<String, Boolean> {

    private final FeedRepository feedRepository;

    public IsUserSubscribedToFeedUseCase(final FeedRepository feedRepository) {
        this.feedRepository = feedRepository;
    }

    @Override
    public Single<Boolean> execute(final String feedUrl) {
        return feedRepository.feedExists(feedUrl);
    }
}
