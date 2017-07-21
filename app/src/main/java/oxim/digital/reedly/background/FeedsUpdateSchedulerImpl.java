package oxim.digital.reedly.background;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.util.Log;

import oxim.digital.reedly.device.job.Jobs;
import oxim.digital.reedly.domain.update.FeedsUpdateScheduler;

public final class FeedsUpdateSchedulerImpl implements FeedsUpdateScheduler {

    private static final String TAG = FeedsUpdateSchedulerImpl.class.getSimpleName();

    private final JobInfo feedsUpdateJobInfo;
    private final Jobs jobScheduler;

    public FeedsUpdateSchedulerImpl(final JobInfo feedsUpdateJobInfo, final Jobs jobScheduler) {
        this.feedsUpdateJobInfo = feedsUpdateJobInfo;
        this.jobScheduler = jobScheduler;
    }

    @Override
    public void scheduleBackgroundFeedUpdates() {
        final int scheduleResult = jobScheduler.schedule(feedsUpdateJobInfo);
        checkScheduleResult(scheduleResult);
    }

    @Override
    public void cancelBackgroundFeedUpdates() {
        jobScheduler.cancel(feedsUpdateJobInfo.getId());
    }

    private void checkScheduleResult(final int scheduleResult) {
        if (scheduleResult != JobScheduler.RESULT_SUCCESS) {
            Log.e(TAG, "Failed to schedule background feeds update");
        }
    }
}
