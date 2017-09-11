package oxim.digital.reedly.configuration;

import java.util.HashMap;
import java.util.Map;

import rx.Scheduler;

public final class ViewActionQueueProviderImpl implements ViewActionQueueProvider {

    private final Scheduler mainScheduler;
    private final Map<String, ViewActionQueue> viewActionQueueMap = new HashMap<>();

    public ViewActionQueueProviderImpl(final Scheduler mainScheduler) {
        this.mainScheduler = mainScheduler;
    }

    @Override
    public ViewActionQueue queueFor(final String queueId) {
        final ViewActionQueue viewActionQueue = viewActionQueueMap.get(queueId);
        if (viewActionQueue != null) {
            return viewActionQueue;
        }

        final ViewActionQueue newQueue = new ViewActionQueue(mainScheduler);
        viewActionQueueMap.put(queueId, newQueue);
        return newQueue;
    }

    @Override
    public void dispose(final String queueId) {
        viewActionQueueMap.remove(queueId);
    }
}
