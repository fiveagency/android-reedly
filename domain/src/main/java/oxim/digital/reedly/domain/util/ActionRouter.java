package oxim.digital.reedly.domain.util;

import java.util.concurrent.TimeUnit;

import rx.functions.Action0;

public interface ActionRouter {

    void setTiming(long windowDuration, TimeUnit timeUnit);

    void throttle(Action0 action);

    void throttle(long windowDuration, TimeUnit timeUnit, Action0 action);

    void blockActions();

    void unblockActions();
}
