package oxim.digital.reedly.dagger.activity;

import oxim.digital.reedly.MainActivity;
import oxim.digital.reedly.ui.MainPresenter;

public interface ActivityComponentInjects {

    void inject(MainActivity mainActivity);
    void inject(MainPresenter mainPresenter);
}
