package oxim.digital.reedly.domain.util;

import java.util.concurrent.TimeUnit;

import oxim.digital.reedly.domain.rx.operators.OperatorVariableThrottleFirst;
import oxim.digital.reedly.domain.rx.operators.ThrottleController;
import rx.Subscription;
import rx.functions.Action0;
import rx.subjects.PublishSubject;

public final class ActionRouterImpl implements ActionRouter {

    private static final long DEFAULT_WINDOW_DURATION_MILLIS = 500L;

    private PublishSubject<Action0> router;
    private Subscription routerSubscription;
    private boolean actionsBlocked;

    private long primaryWindowDuration = DEFAULT_WINDOW_DURATION_MILLIS;
    private TimeUnit primaryTimeUnit = TimeUnit.MILLISECONDS;

    private ThrottleController throttleController;

    public ActionRouterImpl() {
        initRouter();
        listenRouter();
    }

    @Override
    public void setTiming(final long windowDuration, final TimeUnit timeUnit) {
        unSubscribe();
        primaryWindowDuration = windowDuration;
        primaryTimeUnit = timeUnit;
        listenRouter();
    }

    @Override
    public void throttle(final long windowDuration, final TimeUnit timeUnit, final Action0 action) {
        if (router != null && throttleController != null && !actionsBlocked) {
            try {
                throttleController.setThrottleWindow(windowDuration, timeUnit);
                router.onNext(action);
            } catch (final Throwable throwable) {
                System.err.println(throwable.getMessage());
                unSubscribe();
                initRouter();
                listenRouter();
                throw throwable;
            }
        }
    }

    @Override
    public void blockActions() {
        actionsBlocked = true;
    }

    @Override
    public void unblockActions() {
        actionsBlocked = false;
    }

    @Override
    public void throttle(final Action0 action) {
        throttle(primaryWindowDuration, primaryTimeUnit, action);
    }

    private void initRouter() {
        router = PublishSubject.create();
    }

    private void listenRouter() {
        if (router != null) {
            final OperatorVariableThrottleFirst<Action0> operatorVariableThrottleFirst = new OperatorVariableThrottleFirst<>(primaryWindowDuration, primaryTimeUnit);
            routerSubscription = router.lift(operatorVariableThrottleFirst)
                                       .subscribe(Action0::call);
            throttleController = operatorVariableThrottleFirst;
        }
    }

    private void unSubscribe() {
        if (routerSubscription != null && !routerSubscription.isUnsubscribed()) {
            routerSubscription.unsubscribe();
            routerSubscription = null;
        }
    }
}
