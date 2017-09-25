package oxim.digital.reedly.configuration;

import org.junit.Before;
import org.junit.Test;

import oxim.digital.reedly.base.BaseView;
import rx.Completable;
import rx.Observable;
import rx.Single;
import rx.observers.TestSubscriber;
import rx.schedulers.Schedulers;

public final class ViewActionQueueTest {

    private ViewActionQueueImpl<BaseView> viewActionQueue;
    private TestSubscriber<Object> testSubscriber;

    @Before
    public void setUp() throws Exception {
        viewActionQueue = new ViewActionQueueImpl<>(Schedulers.immediate());
        testSubscriber = TestSubscriber.create();
        viewActionQueue.viewActionsObservable()
                       .subscribeOn(Schedulers.immediate())
                       .observeOn(Schedulers.immediate())
                       .subscribe(testSubscriber);
    }

    @Test
    public void shouldPassActionsInResumedState() throws Exception {
        viewActionQueue.resume();

        viewActionQueue.subscribeTo(Completable.complete(), view -> {}, throwable -> {});
        testSubscriber.assertValueCount(1);

        viewActionQueue.subscribeTo(Single.just(view -> {}), throwable -> {});
        testSubscriber.assertValueCount(2);

        viewActionQueue.subscribeTo(Observable.just(view -> {}), view -> {}, throwable -> {});
        //on next and completed
        testSubscriber.assertValueCount(4);
    }

    @Test
    public void shouldEnqueueActionsInPausedState() throws Exception {
        viewActionQueue.pause();
        viewActionQueue.subscribeTo(Completable.complete(), view -> {}, throwable -> {});

        testSubscriber.assertValueCount(0);

        viewActionQueue.resume();
        testSubscriber.assertValueCount(1);
    }

    @Test
    public void shouldClearResourcesUponDestroy() throws Exception {
        viewActionQueue.destroy();
        testSubscriber.assertCompleted();
        testSubscriber.assertUnsubscribed();
    }
}