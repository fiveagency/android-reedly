package oxim.digital.reedly.base;

import android.os.Bundle;

import javax.inject.Inject;

import oxim.digital.reedly.configuration.RandomViewIdGenerator;
import oxim.digital.reedly.configuration.ViewIdGenerator;
import oxim.digital.reedly.dagger.fragment.DaggerFragment;
import oxim.digital.reedly.domain.util.ActionRouter;

public abstract class BaseFragment extends DaggerFragment implements BaseView, BackPropagatingFragment {

    private static final String KEY_VIEW_ID = "view_id";

    @Inject
    ViewIdGenerator viewIdGenerator;

    @Inject
    ActionRouter actionRouter;

    private String viewId;
    private boolean isViewRecreated;

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initializeView(savedInstanceState);
        getPresenter().start();
    }

    private void initializeView(final Bundle savedInstanceState) {
        isViewRecreated = (savedInstanceState != null);
        viewId = (savedInstanceState == null) ? viewIdGenerator.newId()
                                              : savedInstanceState.getString(KEY_VIEW_ID);
    }

    @Override
    public String getViewId() {
        return viewId;
    }

    @Override
    public void onResume() {
        super.onResume();
        getPresenter().activate();
    }

    @Override
    public void onPause() {
        getPresenter().deactivate();
        super.onPause();
    }

    @Override
    public void onSaveInstanceState(final Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(KEY_VIEW_ID, viewId);
    }

    public void onPreDestroy() {
        getPresenter().destroy();
    }

    @Override
    public boolean isRecreated() {
        return isViewRecreated;
    }

    @Override
    public boolean onBack() {
        actionRouter.throttle(() -> getPresenter().back());
        return true;
    }

    public abstract ScopedPresenter getPresenter();
}
