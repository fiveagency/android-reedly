package oxim.digital.reedly.device.notification;

import android.app.Notification;

public interface Notifications {

    void showNotification(int notificationId, Notification notification);

    void updateNotification(int notificationId, Notification notification);

    void hideNotification(int notificationId);

    void hideNotifications();
}
