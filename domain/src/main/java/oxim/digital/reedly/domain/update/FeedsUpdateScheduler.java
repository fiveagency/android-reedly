package oxim.digital.reedly.domain.update;

public interface FeedsUpdateScheduler {

    void scheduleBackgroundFeedUpdates();

    void cancelBackgroundFeedUpdates();
}
