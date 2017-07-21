package oxim.digital.reedly.configuration;

import java.util.Iterator;
import java.util.LinkedList;

import rx.Completable;
import rx.Observable;
import rx.Scheduler;
import rx.Single;
import rx.functions.Action1;
import rx.subjects.PublishSubject;
import rx.subscriptions.CompositeSubscription;

public final class ViewActionQueue<View> {

    private final LinkedList<Action1<View>> viewActions = new LinkedList<>();
    private final Object queueLock = new Object();

    private final PublishSubject<Action1<View>> viewActionSubject = PublishSubject.create();
    private final CompositeSubscription subscriptions = new CompositeSubscription();

    private final Scheduler observeScheduler;

    public ViewActionQueue(final Scheduler observeScheduler) {
        this.observeScheduler = observeScheduler;
    }

    private boolean isPaused;

    public void subscribeTo(final Observable<Action1<View>> observable, final Action1<View> onCompleteAction, final Action1<Throwable> errorAction) {
        subscriptions.add(observable.observeOn(observeScheduler).subscribe(this::onResult, errorAction::call, () -> onResult(onCompleteAction)));
    }

    public void subscribeTo(final Single<Action1<View>> single, final Action1<Throwable> errorAction) {
        subscriptions.add(single.observeOn(observeScheduler).subscribe(this::onResult, errorAction::call));
    }

    public void subscribeTo(final Completable completable, final Action1<View> onCompleteAction, final Action1<Throwable> errorAction) {
        subscriptions.add(completable.observeOn(observeScheduler).subscribe(() -> onResult(onCompleteAction), errorAction::call));
    }

    private void onResult(final Action1<View> resultAction) {
        if (isPaused) {
            synchronized (queueLock) {
                viewActions.add(resultAction);
            }
        } else {
            viewActionSubject.onNext(resultAction);
        }
    }

    public void pause() {
        isPaused = true;
    }

    public void resume() {
        isPaused = false;
        consumeQueue();
    }

    public void destroy() {
        subscriptions.unsubscribe();
        viewActionSubject.onCompleted();
    }

    private void consumeQueue() {
        synchronized (queueLock) {
            final Iterator<Action1<View>> actionIterator = viewActions.iterator();
            while (actionIterator.hasNext()) {
                final Action1<View> pendingViewAction = actionIterator.next();
                viewActionSubject.onNext(pendingViewAction);
                actionIterator.remove();
            }
        }
    }

    public Observable<Action1<View>> viewActionsObservable() {
        return viewActionSubject;
    }
}
