package oxim.digital.reedly.domain.interactor.feed.update;

import oxim.digital.reedly.domain.interactor.type.SingleUseCase;
import oxim.digital.reedly.domain.repository.FeedRepository;
import rx.Single;

public final class ShouldUpdateFeedsInBackgroundUseCase implements SingleUseCase<Boolean> {

    private final FeedRepository feedRepository;

    public ShouldUpdateFeedsInBackgroundUseCase(final FeedRepository feedRepository) {
        this.feedRepository = feedRepository;
    }

    @Override
    public Single<Boolean> execute() {
        return feedRepository.shouldUpdateFeedsInBackground();
    }
}
