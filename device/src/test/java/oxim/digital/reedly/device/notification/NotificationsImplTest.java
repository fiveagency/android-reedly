package oxim.digital.reedly.device.notification;

import android.app.Notification;
import android.support.v4.app.NotificationManagerCompat;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import oxim.digital.reedly.device.DeviceTestData;

public final class NotificationsImplTest {

    private NotificationsImpl notificationsImpl;
    private NotificationManagerCompat notificationManagerCompat;
    private Notification notification;

    @Before
    public void setUp() throws Exception {
        notification = Mockito.mock(Notification.class);
        notificationManagerCompat = Mockito.mock(NotificationManagerCompat.class);
        notificationsImpl = new NotificationsImpl(notificationManagerCompat);
    }

    @Test
    public void showNotification() throws Exception {
        notificationsImpl.showNotification(DeviceTestData.TEST_INTEGER, notification);

        Mockito.verify(notificationManagerCompat, Mockito.times(1)).notify(DeviceTestData.TEST_INTEGER, notification);
        Mockito.verifyNoMoreInteractions(notificationManagerCompat);
    }

    @Test
    public void updateNotification() throws Exception {
        notificationsImpl.updateNotification(DeviceTestData.TEST_INTEGER, notification);

        Mockito.verify(notificationManagerCompat, Mockito.times(1)).notify(DeviceTestData.TEST_INTEGER, notification);
        Mockito.verifyNoMoreInteractions(notificationManagerCompat);
    }

    @Test
    public void hideNotification() throws Exception {
        notificationsImpl.hideNotification(DeviceTestData.TEST_INTEGER);

        Mockito.verify(notificationManagerCompat, Mockito.times(1)).cancel(DeviceTestData.TEST_INTEGER);
        Mockito.verifyNoMoreInteractions(notificationManagerCompat);
    }

    @Test
    public void hideNotifications() throws Exception {
        notificationsImpl.hideNotifications();

        Mockito.verify(notificationManagerCompat, Mockito.times(1)).cancelAll();
        Mockito.verifyNoMoreInteractions(notificationManagerCompat);
    }
}