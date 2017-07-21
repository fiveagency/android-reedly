package oxim.digital.reedly.dagger.fragment;

import android.os.Bundle;

import oxim.digital.reedly.dagger.ComponentFactory;
import oxim.digital.reedly.dagger.activity.DaggerActivity;

public abstract class DaggerFragment extends android.support.v4.app.Fragment {

    private FragmentComponent fragmentComponent;

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        inject(getFragmentComponent());
    }

    protected abstract void inject(FragmentComponent fragmentComponent);

    public FragmentComponent getFragmentComponent() {
        if (fragmentComponent == null) {
            fragmentComponent = ComponentFactory.createFragmentComponent(this, getDaggerActivity().getActivityComponent());
        }

        return fragmentComponent;
    }

    private DaggerActivity getDaggerActivity() {
        return ((DaggerActivity) getActivity());
    }
}
