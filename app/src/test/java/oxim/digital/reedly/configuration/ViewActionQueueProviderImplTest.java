package oxim.digital.reedly.configuration;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import oxim.digital.reedly.AppTestData;
import rx.schedulers.Schedulers;

public final class ViewActionQueueProviderImplTest {

    private ViewActionQueueProviderImpl viewActionQueueProvider;

    @Before
    public void setUp() throws Exception {
        viewActionQueueProvider = new ViewActionQueueProviderImpl(Schedulers.immediate());
    }

    @Test
    public void shouldReturnSameQueueForSameId() throws Exception {
        final ViewActionQueue firstQueue = viewActionQueueProvider.queueFor(AppTestData.TEST_STRING_ID);
        final ViewActionQueue secondQueue = viewActionQueueProvider.queueFor(AppTestData.TEST_STRING_ID);
        final ViewActionQueue thirdQueue = viewActionQueueProvider.queueFor(AppTestData.TEST_STRING_ID_2);

        Assert.assertEquals(firstQueue, secondQueue);
        Assert.assertNotEquals(firstQueue, thirdQueue);
        Assert.assertNotEquals(secondQueue, thirdQueue);
    }

    @Test
    public void shouldRemoveQueue() throws Exception {
        final ViewActionQueue firstQueue = viewActionQueueProvider.queueFor(AppTestData.TEST_STRING_ID);
        viewActionQueueProvider.dispose(AppTestData.TEST_STRING_ID);

        final ViewActionQueue secondQueue = viewActionQueueProvider.queueFor(AppTestData.TEST_STRING_ID);

        Assert.assertNotEquals(firstQueue, secondQueue);
    }
}