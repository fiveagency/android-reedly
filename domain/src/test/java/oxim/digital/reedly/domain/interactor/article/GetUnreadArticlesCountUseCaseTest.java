package oxim.digital.reedly.domain.interactor.article;

import org.mockito.Mockito;

import oxim.digital.reedly.domain.repository.FeedRepository;
import rx.Single;

public final class GetUnreadArticlesCountUseCaseTest {

    private GetUnreadArticlesCountUseCase getUnreadArticlesCountUseCase;
    private FeedRepository feedRepository;

    @org.junit.Before
    public void setUp() throws Exception {
        feedRepository = Mockito.mock(FeedRepository.class);
        getUnreadArticlesCountUseCase = new GetUnreadArticlesCountUseCase(feedRepository);
    }

    @org.junit.Test
    public void execute() throws Exception {
        Mockito.when(feedRepository.getUnreadArticlesCount()).thenReturn(Single.just(34L));

        getUnreadArticlesCountUseCase.execute().subscribe();

        Mockito.verify(feedRepository, Mockito.times(1)).getUnreadArticlesCount();
    }
}