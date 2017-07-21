package oxim.digital.reedly.device.notification;

import android.app.Notification;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationManagerCompat;

public final class NotificationsImpl implements Notifications {

    private final NotificationManagerCompat notificationManagerCompat;

    public NotificationsImpl(@NonNull final Context context) {
        this.notificationManagerCompat = NotificationManagerCompat.from(context);
    }

    @Override
    public void showNotification(final int notificationId, final Notification notification) {
        notificationManagerCompat.notify(notificationId, notification);
    }

    @Override
    public void updateNotification(final int notificationId, final Notification notification) {
        notificationManagerCompat.notify(notificationId, notification);
    }

    @Override
    public void hideNotification(final int notificationId) {
        notificationManagerCompat.cancel(notificationId);
    }

    @Override
    public void hideNotifications() {
        notificationManagerCompat.cancelAll();
    }
}
