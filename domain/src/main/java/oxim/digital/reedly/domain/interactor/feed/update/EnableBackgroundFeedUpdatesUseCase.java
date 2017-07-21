package oxim.digital.reedly.domain.interactor.feed.update;

import oxim.digital.reedly.domain.interactor.type.CompletableUseCase;
import oxim.digital.reedly.domain.update.FeedsUpdateScheduler;
import rx.Completable;

public final class EnableBackgroundFeedUpdatesUseCase implements CompletableUseCase {

    private final SetShouldUpdateFeedsInBackgroundUseCase setShouldUpdateFeedsInBackgroundUseCase;
    private final FeedsUpdateScheduler feedsUpdateScheduler;

    public EnableBackgroundFeedUpdatesUseCase(final SetShouldUpdateFeedsInBackgroundUseCase setShouldUpdateFeedsInBackgroundUseCase,
                                              final FeedsUpdateScheduler feedsUpdateScheduler) {
        this.setShouldUpdateFeedsInBackgroundUseCase = setShouldUpdateFeedsInBackgroundUseCase;
        this.feedsUpdateScheduler = feedsUpdateScheduler;
    }

    @Override
    public Completable execute() {
        return setShouldUpdateFeedsInBackgroundUseCase.execute(true)
                                                      .concatWith(Completable.fromAction(feedsUpdateScheduler::scheduleBackgroundFeedUpdates));
    }
}
