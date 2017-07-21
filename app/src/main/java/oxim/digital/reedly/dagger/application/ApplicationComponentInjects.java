package oxim.digital.reedly.dagger.application;

import oxim.digital.reedly.background.BackgroundFeedsUpdateService;

public interface ApplicationComponentInjects {

    void inject(ReedlyApplication reedlyApplication);

    void inject(BackgroundFeedsUpdateService backgroundFeedsUpdateService);
}
