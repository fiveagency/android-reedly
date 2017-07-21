package oxim.digital.reedly.util;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

public interface ActivityUtils {

    void addFragmentToActivity(@NonNull FragmentManager fragmentManager, @NonNull Fragment fragment, int frameId);

    void addFragmentWithTagToActivity(@NonNull FragmentManager fragmentManager, @NonNull Fragment fragment, int frameId, String tag);

    void addFragmentWithTagToActivity(@NonNull FragmentManager fragmentManager, @NonNull Fragment fragment, String tag, int frameId, String backStackName);

    void setFragmentWithTagToActivity(@NonNull FragmentManager fragmentManager, @NonNull Fragment fragment, String tag, int frameId, String backStackName);

    void setFragmentWithTagToActivity(@NonNull FragmentManager fragmentManager, @NonNull Fragment fragment, String tag, int frameId, String backStackName, boolean animate);

    void removeFragmentFromActivity(@NonNull FragmentManager fragmentManager, @NonNull Fragment fragment);

    boolean propagateBackToTopFragment(@NonNull FragmentManager fragmentManager);

}
