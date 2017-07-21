package oxim.digital.reedly.data.util;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferenceUtilsImpl implements PreferenceUtils {

    private static final String USER_PREFERENCES = "user_preferences";

    private static final String KEY_SHOULD_UPDATE_FEEDS_IN_BACKGROUND = "key_should_update_feed_in_background";

    private final SharedPreferences preferences;

    public PreferenceUtilsImpl(final Context context) {
        this.preferences = context.getSharedPreferences(USER_PREFERENCES, Context.MODE_PRIVATE);
    }

    @Override
    public boolean shouldUpdateFeedsInBackground() {
        return preferences.getBoolean(KEY_SHOULD_UPDATE_FEEDS_IN_BACKGROUND, true);
    }

    @Override
    public void setShouldUpdateFeedsInBackground(final boolean shouldUpdate) {
        preferences.edit()
                   .putBoolean(KEY_SHOULD_UPDATE_FEEDS_IN_BACKGROUND, shouldUpdate)
                   .apply();
    }
}
