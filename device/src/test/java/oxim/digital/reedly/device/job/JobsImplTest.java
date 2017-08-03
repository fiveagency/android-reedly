package oxim.digital.reedly.device.job;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import oxim.digital.reedly.device.DeviceTestData;

public final class JobsImplTest {

    private JobsImpl jobsImpl;
    private JobInfo jobInfo;
    private JobScheduler jobScheduler;

    @Before
    public void setUp() throws Exception {
        jobScheduler = Mockito.mock(JobScheduler.class);
        jobInfo = Mockito.mock(JobInfo.class);

        jobsImpl = new JobsImpl(jobScheduler);
    }

    @Test
    public void scheduleWhenJobSchedulerReturnsSuccess() throws Exception {
        Mockito.when(jobScheduler.schedule(jobInfo)).thenReturn(JobScheduler.RESULT_SUCCESS);

        final int result = jobsImpl.schedule(jobInfo);

        Mockito.verify(jobScheduler, Mockito.times(1)).schedule(jobInfo);
        Mockito.verifyNoMoreInteractions(jobScheduler);

        Assert.assertEquals(result, JobScheduler.RESULT_SUCCESS);
    }

    @Test
    public void scheduleWhenJobSchedulerReturnsFailure() throws Exception {
        Mockito.when(jobScheduler.schedule(jobInfo)).thenReturn(JobScheduler.RESULT_FAILURE);

        final int result = jobsImpl.schedule(jobInfo);

        Mockito.verify(jobScheduler, Mockito.times(1)).schedule(jobInfo);
        Mockito.verifyNoMoreInteractions(jobScheduler);

        Assert.assertEquals(result, JobScheduler.RESULT_FAILURE);
    }

    @Test
    public void cancel() throws Exception {
        jobsImpl.cancel(DeviceTestData.TEST_INTEGER);

        Mockito.verify(jobScheduler, Mockito.times(1)).cancel(DeviceTestData.TEST_INTEGER);
        Mockito.verifyNoMoreInteractions(jobScheduler);
    }
}