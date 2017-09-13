package oxim.digital.reedly.configuration;

import rx.Completable;
import rx.Observable;
import rx.Single;
import rx.functions.Action1;

public interface ViewActionQueue<View> {

    void subscribeTo(Observable<Action1<View>> observable, Action1<View> onCompleteAction, Action1<Throwable> errorAction);

    void subscribeTo(Single<Action1<View>> single, Action1<Throwable> errorAction);

    void subscribeTo(Completable completable, Action1<View> onCompleteAction, Action1<Throwable> errorAction);

    void pause();

    void resume();

    void destroy();

    Observable<Action1<View>> viewActionsObservable();
}
