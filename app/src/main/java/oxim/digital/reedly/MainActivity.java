package oxim.digital.reedly;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;

import javax.inject.Inject;

import oxim.digital.reedly.base.BaseActivity;
import oxim.digital.reedly.base.ScopedPresenter;
import oxim.digital.reedly.dagger.activity.ActivityComponent;
import oxim.digital.reedly.ui.MainPresenter;
import oxim.digital.reedly.ui.feed.subscription.UserSubscriptionsFragment;
import oxim.digital.reedly.util.ActivityUtils;

public final class MainActivity extends BaseActivity {

    @Inject
    FragmentManager fragmentManager;

    @Inject
    ActivityUtils activityUtils;

    @Inject
    MainPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            activityUtils.addFragmentWithTagToActivity(fragmentManager, UserSubscriptionsFragment.newInstance(), R.id.activity_container, UserSubscriptionsFragment.TAG);
        }
    }

    @Override
    protected ScopedPresenter getPresenter() {
        return presenter;
    }

    @Override
    protected void inject(final ActivityComponent activityComponent) {
        activityComponent.inject(this);
    }
}
