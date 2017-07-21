package oxim.digital.reedly.background;

import android.app.Notification;
import android.app.PendingIntent;

public interface NotificationFactory {

    Notification createNewArticlesNotification(PendingIntent contentIntent);
}
