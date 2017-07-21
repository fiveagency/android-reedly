package oxim.digital.reedly.util;

import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Date;

public interface DateUtils {

    String SHORT_DATE_FORMAT = "MMM d";
    String TIME_FORMAT = "HH:mm";
    String SHORT_DATE_TIME_FORMAT = SHORT_DATE_FORMAT + ", " + TIME_FORMAT;

    @Retention(RetentionPolicy.SOURCE)
    @StringDef({SHORT_DATE_FORMAT, TIME_FORMAT, SHORT_DATE_TIME_FORMAT})
    @interface DateFormat { }

    String format(long time);

    String format(Date date);

    String format(long time, @DateFormat String dateFormat);

    String format(Date date, @DateFormat String dateFormat);
}
