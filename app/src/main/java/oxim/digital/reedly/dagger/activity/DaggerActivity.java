package oxim.digital.reedly.dagger.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import oxim.digital.reedly.dagger.ComponentFactory;
import oxim.digital.reedly.dagger.application.ReedlyApplication;

public abstract class DaggerActivity extends AppCompatActivity {

    private ActivityComponent activityComponent;

    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        inject(getActivityComponent());
    }

    public ActivityComponent getActivityComponent() {
        if (activityComponent == null) {
            activityComponent = ComponentFactory.createActivityComponent(this, getReedlyApplication());
        }
        return activityComponent;
    }

    private ReedlyApplication getReedlyApplication() {
        return ((ReedlyApplication) getApplication());
    }

    protected abstract void inject(final ActivityComponent activityComponent);
}
