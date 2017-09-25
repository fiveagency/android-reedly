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
    public void shouldReturnSuccessWhenJobIsScheduled() throws Exception {
        testScheduleWhenJobSchedulerReturnsResult(JobScheduler.RESULT_SUCCESS);
    }

    @Test
    public void shouldReturnFailureWhenJobCannotBeScheduled() throws Exception {
        testScheduleWhenJobSchedulerReturnsResult(JobScheduler.RESULT_FAILURE);
    }

    private void testScheduleWhenJobSchedulerReturnsResult(final int expectedResult) {
        Mockito.when(jobScheduler.schedule(jobInfo)).thenReturn(expectedResult);

        final int givenResult = jobsImpl.schedule(jobInfo);

        Mockito.verify(jobScheduler, Mockito.times(1)).schedule(jobInfo);
        Mockito.verifyNoMoreInteractions(jobScheduler);

        Assert.assertEquals(givenResult, expectedResult);
    }

    @Test
    public void shouldCancelExistingJob() throws Exception {
        jobsImpl.cancel(DeviceTestData.TEST_INTEGER);

        Mockito.verify(jobScheduler, Mockito.times(1)).cancel(DeviceTestData.TEST_INTEGER);
        Mockito.verifyNoMoreInteractions(jobScheduler);
    }
}