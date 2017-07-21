package oxim.digital.reedly.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public final class DateUtilsImpl implements DateUtils {

    private static final String EMPTY_TIME = "";

    @Override
    public String format(final long time) {
        return getStringFormat(new Date(time), DateUtils.TIME_FORMAT);
    }

    @Override
    public String format(final Date date) {
        return getStringFormat(date, DateUtils.SHORT_DATE_FORMAT);
    }

    @Override
    public String format(final long time, final @DateFormat String dateFormat) {
        return getStringFormat(new Date(time), dateFormat);
    }

    @Override
    public String format(final Date date, final @DateFormat String dateFormat) {
        return getStringFormat(date, dateFormat);
    }

    private String getStringFormat(final Date time, final @DateFormat String format) {
        if (time == null) {
            return EMPTY_TIME;
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat(format, Locale.getDefault());
        return dateFormat.format(time);
    }
}
