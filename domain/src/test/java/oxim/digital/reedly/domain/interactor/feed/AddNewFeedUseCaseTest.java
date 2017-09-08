package oxim.digital.reedly.domain.interactor.feed;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.IOException;

import oxim.digital.reedly.domain.interactor.DomainTestData;
import oxim.digital.reedly.domain.repository.FeedRepository;
import rx.Completable;
import rx.observers.TestSubscriber;

public final class AddNewFeedUseCaseTest {

    private AddNewFeedUseCase addNewFeedUseCase;
    private FeedRepository feedRepository;
    private TestSubscriber testSubscriber;

    @Before
    public void setUp() throws Exception {
        feedRepository = Mockito.mock(FeedRepository.class);
        addNewFeedUseCase = new AddNewFeedUseCase(feedRepository);
        testSubscriber = new TestSubscriber();
    }

    @Test
    public void shouldCreateNewFeedByUrl() throws Exception {
        Mockito.when(feedRepository.createNewFeed(Mockito.any())).thenReturn(Completable.complete());

        addNewFeedUseCase.execute(DomainTestData.TEST_URL_STRING).subscribe(testSubscriber);

        Mockito.verify(feedRepository, Mockito.times(1)).createNewFeed(DomainTestData.TEST_URL_STRING);
        Mockito.verifyNoMoreInteractions(feedRepository);

        testSubscriber.assertCompleted();
    }


}