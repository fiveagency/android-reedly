package oxim.digital.reedly;

import oxim.digital.reedly.configuration.ViewActionQueue;
import rx.Completable;
import rx.Observable;
import rx.Scheduler;
import rx.Single;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import rx.subjects.PublishSubject;

public final class MockViewActionQueue<View> implements ViewActionQueue<View> {

    private final PublishSubject<Action1<View>> subject = PublishSubject.create();
    private final Scheduler scheduler = Schedulers.immediate();

    @Override
    public void subscribeTo(final Observable<Action1<View>> observable, final Action1<View> onCompleteAction, final Action1<Throwable> errorAction) {
        observable.subscribeOn(scheduler)
                  .subscribe(this::onResult, errorAction::call);
    }

    @Override
    public void subscribeTo(final Single<Action1<View>> single, final Action1<Throwable> errorAction) {
        single.subscribeOn(scheduler)
              .subscribe(this::onResult, errorAction::call);
    }

    @Override
    public void subscribeTo(final Completable completable, final Action1<View> onCompleteAction, final Action1<Throwable> errorAction) {
        completable.subscribeOn(scheduler)
                   .subscribe(() -> onResult(onCompleteAction), errorAction::call);
    }

    private void onResult(final Action1<View> resultAction) {
        subject.onNext(resultAction);
    }

    @Override
    public void pause() {
        //noop
    }

    @Override
    public void resume() {
        //noop
    }

    @Override
    public void destroy() {
        //noop
    }

    @Override
    public Observable<Action1<View>> viewActionsObservable() {
        return subject;
    }
}
