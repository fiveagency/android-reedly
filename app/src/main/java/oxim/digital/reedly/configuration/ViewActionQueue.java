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

public final class ViewActionQueue<View> implements ViewActionHandler<View> {

    private final LinkedList<Action1<View>> viewActions = new LinkedList<>();
    private final Object queueLock = new Object();

    private final PublishSubject<Action1<View>> viewActionSubject = PublishSubject.create();
    private final CompositeSubscription subscriptions = new CompositeSubscription();

    private final Scheduler observeScheduler;

    public ViewActionQueue(final Scheduler observeScheduler) {
        this.observeScheduler = observeScheduler;
    }

    private boolean isPaused;

    @Override
    public void subscribeTo(final Observable<Action1<View>> observable, final Action1<View> onCompleteAction, final Action1<Throwable> errorAction) {
        subscriptions.add(observable.observeOn(observeScheduler).subscribe(this::onResult, errorAction::call, () -> onResult(onCompleteAction)));
    }

    @Override
    public void subscribeTo(final Single<Action1<View>> single, final Action1<Throwable> errorAction) {
        subscriptions.add(single.observeOn(observeScheduler).subscribe(this::onResult, errorAction::call));
    }

    @Override
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

    @Override
    public void pause() {
        isPaused = true;
    }

    @Override
    public void resume() {
        isPaused = false;
        consumeQueue();
    }

    @Override
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

    @Override
    public Observable<Action1<View>> viewActionsObservable() {
        return viewActionSubject;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        final ViewActionQueue<?> that = (ViewActionQueue<?>) o;

        if (isPaused != that.isPaused) {
            return false;
        }
        if (viewActions != null ? !viewActions.equals(that.viewActions) : that.viewActions != null) {
            return false;
        }
        if (queueLock != null ? !queueLock.equals(that.queueLock) : that.queueLock != null) {
            return false;
        }
        if (viewActionSubject != null ? !viewActionSubject.equals(that.viewActionSubject) : that.viewActionSubject != null) {
            return false;
        }
        if (subscriptions != null ? !subscriptions.equals(that.subscriptions) : that.subscriptions != null) {
            return false;
        }
        return observeScheduler != null ? observeScheduler.equals(that.observeScheduler) : that.observeScheduler == null;
    }

    @Override
    public int hashCode() {
        int result = viewActions != null ? viewActions.hashCode() : 0;
        result = 31 * result + (queueLock != null ? queueLock.hashCode() : 0);
        result = 31 * result + (viewActionSubject != null ? viewActionSubject.hashCode() : 0);
        result = 31 * result + (subscriptions != null ? subscriptions.hashCode() : 0);
        result = 31 * result + (observeScheduler != null ? observeScheduler.hashCode() : 0);
        result = 31 * result + (isPaused ? 1 : 0);
        return result;
    }
}
