package oxim.digital.reedly.configuration;

import org.junit.Before;
import org.junit.Test;

import oxim.digital.reedly.base.BaseView;
import rx.Completable;
import rx.Single;
import rx.observers.TestSubscriber;
import rx.schedulers.Schedulers;

public final class ViewActionQueueTest {

    private ViewActionQueue<BaseView> viewActionQueue;
    private TestSubscriber<Object> testSubscriber;

    @Before
    public void setUp() throws Exception {
        viewActionQueue = new ViewActionQueue<>(Schedulers.immediate());
        testSubscriber = TestSubscriber.create();
        viewActionQueue.viewActionsObservable()
                       .subscribeOn(Schedulers.immediate())
                       .observeOn(Schedulers.immediate())
                       .subscribe(testSubscriber);
    }

    @Test
    public void shouldPassActionsWhenObservableEmitsAndResumed() throws Exception {
        viewActionQueue.resume();

        viewActionQueue.subscribeTo(Completable.complete(), view -> {}, throwable -> {});
        testSubscriber.assertValueCount(1);

        viewActionQueue.subscribeTo(Single.just(view -> { }), throwable -> {});
        testSubscriber.assertValueCount(2);

        viewActionQueue.subscribeTo(rx.Observable.just(view -> { }), view -> {}, throwable -> {});
        //on next and completed
        testSubscriber.assertValueCount(4);
    }

    @Test
    public void shouldHoldActionsUntilResumedWhenObservableEmits() throws Exception {
        viewActionQueue.pause();
        viewActionQueue.subscribeTo(Completable.complete(), view -> {}, throwable -> {});

        testSubscriber.assertValueCount(0);

        viewActionQueue.resume();
        testSubscriber.assertValueCount(1);
    }

    @Test
    public void shouldBeAbleToDestroyItselfAndUnSubscribeSubscribers() throws Exception {
        viewActionQueue.destroy();
        testSubscriber.assertCompleted();
        testSubscriber.assertUnsubscribed();
    }
}