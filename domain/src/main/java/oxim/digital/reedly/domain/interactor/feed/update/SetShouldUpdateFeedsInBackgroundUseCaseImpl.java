package oxim.digital.reedly.domain.interactor.feed.update;

import oxim.digital.reedly.domain.repository.FeedRepository;
import rx.Completable;

public final class SetShouldUpdateFeedsInBackgroundUseCaseImpl implements SetShouldUpdateFeedsInBackgroundUseCase {

    private final FeedRepository feedRepository;

    public SetShouldUpdateFeedsInBackgroundUseCaseImpl(final FeedRepository feedRepository) {
        this.feedRepository = feedRepository;
    }

    @Override
    public Completable execute(final Boolean shouldUpdate) {
        return feedRepository.setShouldUpdateFeedsInBackground(shouldUpdate);
    }
}
