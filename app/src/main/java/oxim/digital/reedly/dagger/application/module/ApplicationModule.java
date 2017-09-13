package oxim.digital.reedly.dagger.application.module;

import android.content.Context;
import android.content.res.Resources;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import oxim.digital.reedly.configuration.RandomViewIdGenerator;
import oxim.digital.reedly.configuration.ViewActionQueueProvider;
import oxim.digital.reedly.configuration.ViewActionQueueProviderImpl;
import oxim.digital.reedly.configuration.ViewIdGenerator;
import oxim.digital.reedly.dagger.application.ForApplication;
import oxim.digital.reedly.dagger.application.ReedlyApplication;
import rx.Scheduler;

@Module
public final class ApplicationModule {

    private final ReedlyApplication reedlyApplication;

    public ApplicationModule(final ReedlyApplication reedlyApplication) {
        this.reedlyApplication = reedlyApplication;
    }

    @Provides
    @Singleton
    ReedlyApplication provideReedlyApplication() {
        return reedlyApplication;
    }

    @Provides
    @Singleton
    @ForApplication
    Context provideContext() {
        return reedlyApplication;
    }

    @Provides
    @Singleton
    ViewIdGenerator provideViewIdGenerator() {
        return new RandomViewIdGenerator();
    }

    @Provides
    @Singleton
    ViewActionQueueProvider provideViewActionQueueProvider(final @Named(ThreadingModule.MAIN_SCHEDULER) Scheduler mainScheduler) {
        return new ViewActionQueueProviderImpl(mainScheduler);
    }

    @Provides
    @Singleton
    Resources provideResources() {
        return reedlyApplication.getResources();
    }

    public interface Exposes {

        ReedlyApplication reedlyApplication();

        @ForApplication
        Context context();

        ViewIdGenerator viewIdGenerator();

        ViewActionQueueProvider viewActionQueueProvider();

        Resources resources();
    }
}
