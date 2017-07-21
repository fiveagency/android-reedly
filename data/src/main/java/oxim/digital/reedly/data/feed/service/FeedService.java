package oxim.digital.reedly.data.feed.service;

import oxim.digital.reedly.data.feed.service.model.ApiFeed;
import rx.Single;

public interface FeedService {

    Single<ApiFeed> fetchFeed(final String feedUrl);
}
