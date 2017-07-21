package oxim.digital.reedly.data.util.connectivity;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Utility class to check the current network status. Are we connected to the
 * internet? If yes, are we connected via Wifi or Cellular?
 */
public final class NetworkMonitor {

    /**
     * Check if we are online.
     *
     * @param context the application context (usually an MCLApplication)
     * @return true if online (either Wifi or Cellular), otherwise false
     */
    static public boolean isConnected(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo info = connectivityManager.getActiveNetworkInfo();
        return info != null && info.isConnected();
    }

    /**
     * Check if we are online via Wifi.
     *
     * @param context context
     * @return true if online via Wifi, otherwise false
     */
    static public boolean isWifi(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo info = connectivityManager.getActiveNetworkInfo();

        return info != null && info.isConnected()
                && info.getType() == ConnectivityManager.TYPE_WIFI;
    }

    /**
     * Check if we are online via Cellular (4G, 3G, EDGE, etc).
     *
     * @param context the application context (usually an MCLApplication)
     * @return true if online via cellular network, otherwise false
     */
    static public boolean isCellular(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo info = connectivityManager.getActiveNetworkInfo();

        return info != null && info.isConnected()
                && info.getType() == ConnectivityManager.TYPE_MOBILE;
    }
}