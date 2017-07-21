package oxim.digital.reedly.domain.interactor.feed;

import java.util.List;

import oxim.digital.reedly.domain.interactor.type.SingleUseCase;
import oxim.digital.reedly.domain.model.Feed;
import oxim.digital.reedly.domain.repository.FeedRepository;
import rx.Single;

public final class GetUserFeedsUseCase implements SingleUseCase<List<Feed>> {

    private final FeedRepository feedRepository;

    public GetUserFeedsUseCase(final FeedRepository feedRepository) {
        this.feedRepository = feedRepository;
    }

    @Override
    public Single<List<Feed>> execute() {
        return Single.defer(feedRepository::getUserFeeds);
    }
}
