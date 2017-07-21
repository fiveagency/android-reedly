package oxim.digital.reedly.domain.interactor.feed.update;

import oxim.digital.reedly.domain.interactor.type.CompletableUseCaseWithParameter;
import oxim.digital.reedly.domain.repository.FeedRepository;
import rx.Completable;

public final class SetShouldUpdateFeedsInBackgroundUseCase implements CompletableUseCaseWithParameter<Boolean> {

    private final FeedRepository feedRepository;

    public SetShouldUpdateFeedsInBackgroundUseCase(final FeedRepository feedRepository) {
        this.feedRepository = feedRepository;
    }

    @Override
    public Completable execute(final Boolean shouldUpdate) {
        return feedRepository.setShouldUpdateFeedsInBackground(shouldUpdate);
    }
}
