package oxim.digital.reedly.domain.interactor.feed;

import oxim.digital.reedly.domain.interactor.type.CompletableUseCaseWithParameter;
import oxim.digital.reedly.domain.repository.FeedRepository;
import rx.Completable;

public final class AddNewFeedUseCase implements CompletableUseCaseWithParameter<String> {

    private final FeedRepository feedRepository;

    public AddNewFeedUseCase(final FeedRepository feedRepository) {
        this.feedRepository = feedRepository;
    }

    @Override
    public Completable execute(final String feedUrl) {
        return feedRepository.createNewFeed(feedUrl);
    }
}
