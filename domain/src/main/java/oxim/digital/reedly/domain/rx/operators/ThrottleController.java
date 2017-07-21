package oxim.digital.reedly.domain.rx.operators;

import java.util.concurrent.TimeUnit;

public interface ThrottleController {

    void setThrottleWindow(long windowDuration, TimeUnit unit);
}
