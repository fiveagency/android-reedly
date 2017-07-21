package oxim.digital.reedly.dagger;

import oxim.digital.reedly.dagger.activity.ActivityComponent;
import oxim.digital.reedly.dagger.activity.DaggerActivity;
import oxim.digital.reedly.dagger.application.ApplicationComponent;
import oxim.digital.reedly.dagger.application.ReedlyApplication;
import oxim.digital.reedly.dagger.fragment.DaggerFragment;
import oxim.digital.reedly.dagger.fragment.FragmentComponent;

public final class ComponentFactory {

    private ComponentFactory() {
    }

    public static ApplicationComponent createApplicationComponent(final ReedlyApplication reedlyApplication) {
        return ApplicationComponent.Initializer.init(reedlyApplication);
    }

    public static ActivityComponent createActivityComponent(final DaggerActivity daggerActivity, final ReedlyApplication reedlyApplication) {
        return ActivityComponent.Initializer.init(daggerActivity, reedlyApplication.getApplicationComponent());
    }

    public static FragmentComponent createFragmentComponent(final DaggerFragment daggerFragment, final ActivityComponent activityComponent) {
        return FragmentComponent.Initializer.init(daggerFragment, activityComponent);
    }
}
