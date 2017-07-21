package oxim.digital.reedly.dagger.activity.module;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.FragmentManager;

import dagger.Module;
import dagger.Provides;
import oxim.digital.reedly.MainActivity;
import oxim.digital.reedly.dagger.activity.ActivityScope;
import oxim.digital.reedly.dagger.activity.DaggerActivity;
import oxim.digital.reedly.dagger.activity.ForActivity;
import oxim.digital.reedly.domain.util.ActionRouter;
import oxim.digital.reedly.domain.util.ActionRouterImpl;
import oxim.digital.reedly.ui.MainPresenter;
import oxim.digital.reedly.ui.router.Router;
import oxim.digital.reedly.ui.router.RouterImpl;

@Module
public class ActivityModule {

    private final DaggerActivity daggerActivity;

    public ActivityModule(final DaggerActivity daggerActivity) {
        this.daggerActivity = daggerActivity;
    }

    @Provides
    @ActivityScope
    @ForActivity
    Context provideActivityContext() {
        return daggerActivity;
    }

    @Provides
    @ActivityScope
    Activity provideActivity() {
        return daggerActivity;
    }

    @Provides
    @ActivityScope
    FragmentManager provideFragmentManager() {
        return daggerActivity.getSupportFragmentManager();
    }

    @Provides
    @ActivityScope
    Router provideRouter(final FragmentManager fragmentManager) {
        return new RouterImpl(daggerActivity, fragmentManager);
    }

    @Provides
    @ActivityScope
    ActionRouter provideActionRouter() {
        return new ActionRouterImpl();
    }

    @Provides
    @ActivityScope
    MainPresenter provideMainPresenter() {
        final MainPresenter mainPresenter = new MainPresenter((MainActivity) daggerActivity);
        daggerActivity.getActivityComponent().inject(mainPresenter);
        return mainPresenter;
    }

    public interface Exposes {

        Activity activity();

        @ForActivity
        Context context();

        FragmentManager fragmentManager();

        Router router();

        ActionRouter actionRouter();
    }
}
