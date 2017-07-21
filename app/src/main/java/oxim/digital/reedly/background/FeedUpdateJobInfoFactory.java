package oxim.digital.reedly.background;

import android.app.job.JobInfo;
import android.content.ComponentName;

import java.util.concurrent.TimeUnit;

public final class FeedUpdateJobInfoFactory {

    private FeedUpdateJobInfoFactory() {
    }

    public static JobInfo createJobInfo(final int jobId, final int intervalMins, final ComponentName jobService) {
        final JobInfo.Builder jobInfoBuilder = new JobInfo.Builder(jobId, jobService);
        final long jobInterval = TimeUnit.MINUTES.toMillis(intervalMins);

        jobInfoBuilder.setPersisted(true)
                      .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
                      .setPeriodic(jobInterval);

        return jobInfoBuilder.build();
    }
}
