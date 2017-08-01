package oxim.digital.reedly.domain.interactor.article;

import org.mockito.Mockito;

import oxim.digital.reedly.domain.repository.FeedRepository;
import rx.Completable;

public final class MarkArticleAsReadUseCaseTest {

    private MarkArticleAsReadUseCase markArticleAsReadUseCase;
    private FeedRepository feedRepository;

    @org.junit.Before
    public void setUp() throws Exception {
        feedRepository = Mockito.mock(FeedRepository.class);
        markArticleAsReadUseCase = new MarkArticleAsReadUseCase(feedRepository);
    }

    @org.junit.Test
    public void execute() throws Exception {
        Mockito.when(feedRepository.markArticleAsRead(Mockito.anyInt())).thenReturn(Completable.complete());

        markArticleAsReadUseCase.execute(32).subscribe();

        Mockito.verify(feedRepository, Mockito.times(1)).markArticleAsRead(32);
    }
}