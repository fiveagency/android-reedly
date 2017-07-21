package oxim.digital.reedly.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;

import java.util.List;

import javax.inject.Inject;

import oxim.digital.reedly.configuration.ViewIdGenerator;
import oxim.digital.reedly.dagger.activity.DaggerActivity;
import oxim.digital.reedly.domain.util.ActionRouter;
import oxim.digital.reedly.util.ActivityUtils;

public abstract class BaseActivity extends DaggerActivity implements BaseView {

    private static final String KEY_VIEW_ID = "view_id";

    @Inject
    protected FragmentManager fragmentManager;

    @Inject
    ViewIdGenerator viewIdGenerator;

    @Inject
    ActionRouter actionRouter;

    @Inject
    ActivityUtils activityUtils;

    private String viewId;
    private boolean isViewRecreated;

    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initializeView(savedInstanceState);
        getPresenter().start();
    }

    private void initializeView(@Nullable final Bundle savedInstanceState) {
        isViewRecreated = (savedInstanceState != null);
        viewId = (savedInstanceState == null) ? viewIdGenerator.newId()
                                              : savedInstanceState.getString(KEY_VIEW_ID);
    }

    @Override
    public String getViewId() {
        return viewId;
    }

    @Override
    public boolean isRecreated() {
        return isViewRecreated;
    }

    @Override
    protected void onResume() {
        super.onResume();
        getPresenter().activate();
    }

    @Override
    protected void onSaveInstanceState(final Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(KEY_VIEW_ID, viewId);
    }

    @Override
    protected void onPause() {
        getPresenter().deactivate();
        super.onPause();
    }

    @Override
    public void onBackPressed() {
        if (!activityUtils.propagateBackToTopFragment(fragmentManager)) {
            super.onBackPressed();
        }
    }

    @Override
    protected void onDestroy() {
        if (isFinishing()) {
            getPresenter().destroy();
            clearFragments();
        }
        super.onDestroy();
    }

    private void clearFragments() {
        final List<Fragment> fragments = fragmentManager.getFragments();
        for (final Fragment fragment : fragments) {
            if (fragment instanceof BaseFragment) {
                ((BaseFragment) fragment).onPreDestroy();
            }
        }
    }

    protected abstract ScopedPresenter getPresenter();
}
