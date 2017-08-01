package oxim.digital.reedly.domain.interactor.feed.update;

import org.mockito.Mockito;

import oxim.digital.reedly.domain.model.Feed;
import oxim.digital.reedly.domain.repository.FeedRepository;
import rx.Completable;

public final class UpdateFeedUseCaseTest {

    private UpdateFeedUseCase updateFeedUseCase;
    private FeedRepository feedRepository;

    @org.junit.Before
    public void setUp() throws Exception {
        feedRepository = Mockito.mock(FeedRepository.class);

        updateFeedUseCase = new UpdateFeedUseCase(feedRepository);
    }

    @org.junit.Test
    public void execute() throws Exception {
        Mockito.when(feedRepository.updateArticles(Mockito.any())).thenReturn(Completable.complete());

        final Feed feed = new Feed(78, "", "", "", "", "");
        updateFeedUseCase.execute(feed).subscribe();

        Mockito.verify(feedRepository, Mockito.times(1)).updateArticles(feed);
    }
}