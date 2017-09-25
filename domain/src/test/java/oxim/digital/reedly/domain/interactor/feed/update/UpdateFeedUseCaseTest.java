package oxim.digital.reedly.domain.interactor.feed.update;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.IOException;

import oxim.digital.reedly.domain.interactor.DomainTestData;
import oxim.digital.reedly.domain.repository.FeedRepository;
import rx.Completable;
import rx.observers.TestSubscriber;

public final class UpdateFeedUseCaseTest {

    private UpdateFeedUseCase updateFeedUseCase;
    private FeedRepository feedRepository;
    private TestSubscriber<Object> testSubscriber;

    @Before
    public void setUp() throws Exception {
        feedRepository = Mockito.mock(FeedRepository.class);
        updateFeedUseCase = new UpdateFeedUseCase(feedRepository);
        testSubscriber = new TestSubscriber<>();
    }

    @Test
    public void shouldUpdateFeedInRepository() throws Exception {
        Mockito.when(feedRepository.pullArticlesForFeedFromOrigin(Mockito.any())).thenReturn(Completable.complete());

        updateFeedUseCase.execute(DomainTestData.TEST_FEED).subscribe(testSubscriber);

        Mockito.verify(feedRepository, Mockito.times(1)).pullArticlesForFeedFromOrigin(DomainTestData.TEST_FEED);
        Mockito.verifyNoMoreInteractions(feedRepository);

        testSubscriber.assertCompleted();
    }


}