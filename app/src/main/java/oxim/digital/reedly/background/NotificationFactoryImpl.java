package oxim.digital.reedly.background;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.res.Resources;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.res.ResourcesCompat;
import oxim.digital.reedly.R;

public final class NotificationFactoryImpl implements NotificationFactory {

    private final Context context;
    private final Resources resources;

    public NotificationFactoryImpl(final Context context, final Resources resources) {
        this.context = context;
        this.resources = resources;
    }

    @Override
    public Notification createNewArticlesNotification(final PendingIntent contentIntent) {
        final NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context);
        return notificationBuilder.setAutoCancel(true)
                                  .setColor(ResourcesCompat.getColor(resources, R.color.colorPrimary, null))
                                  .setSmallIcon(R.drawable.notification_icon)
                                  .setContentTitle(resources.getString(R.string.new_articles))
                                  .setContentText(resources.getString(R.string.there_have_been_new_articles))
                                  .setContentIntent(contentIntent)
                                  .build();
    }
}
