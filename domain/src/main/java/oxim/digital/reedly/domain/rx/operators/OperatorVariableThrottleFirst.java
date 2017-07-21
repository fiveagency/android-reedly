package oxim.digital.reedly.domain.rx.operators;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Scheduler;
import rx.Subscriber;
import rx.schedulers.Schedulers;

public final class OperatorVariableThrottleFirst<T> implements Observable.Operator<T, T>, ThrottleController {

    private final Scheduler scheduler;

    private long timeInMilliseconds;
    private long newTimeInMilliseconds = 0L;

    public OperatorVariableThrottleFirst(final long windowDuration, final TimeUnit unit) {
        this(windowDuration, unit, Schedulers.computation());
    }

    public OperatorVariableThrottleFirst(final long windowDuration, final TimeUnit unit, final Scheduler scheduler) {
        this.timeInMilliseconds = unit.toMillis(windowDuration);
        this.scheduler = scheduler;
    }

    @Override
    public Subscriber<? super T> call(final Subscriber<? super T> subscriber) {
        return new Subscriber<T>(subscriber) {

            private long lastOnNext = 0;

            @Override
            public void onStart() {
                request(Long.MAX_VALUE);
            }

            @Override
            public void onNext(T v) {
                final long now = scheduler.now();
                if (lastOnNext == 0 || now - lastOnNext >= timeInMilliseconds) {
                    lastOnNext = now;
                    if (newTimeInMilliseconds != 0L) {
                        timeInMilliseconds = newTimeInMilliseconds;
                    }
                    subscriber.onNext(v);
                }
            }

            @Override
            public void onCompleted() {
                subscriber.onCompleted();
            }

            @Override
            public void onError(Throwable e) {
                subscriber.onError(e);
            }
        };
    }

    @Override
    public void setThrottleWindow(final long windowDuration, final TimeUnit unit) {
        newTimeInMilliseconds = unit.toMillis(windowDuration);
    }
}
