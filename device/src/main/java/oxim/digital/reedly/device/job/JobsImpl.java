package oxim.digital.reedly.device.job;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;

public final class JobsImpl implements Jobs {

    private final JobScheduler jobScheduler;

    public JobsImpl(final JobScheduler jobScheduler) {
        this.jobScheduler = jobScheduler;
    }

    @Override
    public int schedule(final JobInfo jobInfo) {
        return jobScheduler.schedule(jobInfo);
    }

    @Override
    public void cancel(final int jobId) {
        jobScheduler.cancel(jobId);
    }
}
