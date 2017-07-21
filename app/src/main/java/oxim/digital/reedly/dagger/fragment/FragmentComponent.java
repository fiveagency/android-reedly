package oxim.digital.reedly.dagger.fragment;

import dagger.Component;
import oxim.digital.reedly.dagger.activity.ActivityComponent;
import oxim.digital.reedly.dagger.fragment.module.FragmentModule;
import oxim.digital.reedly.dagger.fragment.module.FragmentPresenterModule;

@FragmentScope
@Component(
        dependencies = ActivityComponent.class,
        modules = {
                FragmentModule.class,
                FragmentPresenterModule.class
        }
)
public interface FragmentComponent extends FragmentComponentInjects, FragmentComponentExposes {

    final class Initializer {

        static public FragmentComponent init(final DaggerFragment fragment, final ActivityComponent activityComponent) {
            return DaggerFragmentComponent.builder()
                                          .activityComponent(activityComponent)
                                          .fragmentModule(new FragmentModule(fragment))
                                          .fragmentPresenterModule(new FragmentPresenterModule(fragment))
                                          .build();
        }

        private Initializer() {
        }
    }
}
