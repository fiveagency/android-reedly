package oxim.digital.reedly.dagger.application;

import android.app.Application;
import android.content.Context;
import android.support.v7.app.AppCompatDelegate;

import com.facebook.stetho.Stetho;
import com.raizlabs.android.dbflow.config.FlowConfig;
import com.raizlabs.android.dbflow.config.FlowManager;

import javax.inject.Inject;

import oxim.digital.reedly.BuildConfig;
import oxim.digital.reedly.dagger.ComponentFactory;
import oxim.digital.reedly.domain.interactor.feed.update.EnableBackgroundFeedUpdatesUseCase;
import oxim.digital.reedly.domain.interactor.feed.update.ShouldUpdateFeedsInBackgroundUseCase;
import rx.Completable;

/**

 Copyright 2017 Mihael Franceković

 Whole application code licensed under the Apache License, Version 2.0 (the "License");
 you may not use this file except in compliance with the License.
 You may obtain a copy of the License at

 http://www.apache.org/licenses/LICENSE-2.0

 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 See the License for the specific language governing permissions and
 limitations under the License.

 */
public final class ReedlyApplication extends Application {

    @Inject
    ShouldUpdateFeedsInBackgroundUseCase shouldUpdateFeedsInBackgroundUseCase;

    @Inject
    EnableBackgroundFeedUpdatesUseCase enableBackgroundFeedUpdatesUseCase;

    private ApplicationComponent applicationComponent;

    public static ReedlyApplication from(final Context context) {
        return (ReedlyApplication) context.getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        applicationComponent = ComponentFactory.createApplicationComponent(this);
        applicationComponent.inject(this);

        FlowManager.init(new FlowConfig.Builder(this).build());
        checkForBackgroundUpdate();

        if (BuildConfig.DEBUG) {
            Stetho.initializeWithDefaults(this);
        }
    }

    private void checkForBackgroundUpdate() {
        shouldUpdateFeedsInBackgroundUseCase.execute()
                                            .flatMap(shouldUpdate -> ((shouldUpdate) ? enableBackgroundFeedUpdatesUseCase.execute()
                                                                                     : Completable.complete())
                                                    .toSingleDefault(true))
                                            .subscribe();
    }

    public ApplicationComponent getApplicationComponent() {
        return applicationComponent;
    }
}
