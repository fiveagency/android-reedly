package oxim.digital.reedly.domain.interactor.type.demo;

import oxim.digital.reedly.domain.interactor.type.CompletableUseCaseWithParameter;
import oxim.digital.reedly.domain.repository.FeedRepository;
import rx.Completable;

public final class ReactiveAddFeedCompletableUseCase implements CompletableUseCaseWithParameter<String> {

    // TODO - Check this implementation
    private final FeedValidator feedValidator;
    private final FeedRepository feedRepository;

    public ReactiveAddFeedCompletableUseCase(final FeedValidator feedValidator, final FeedRepository feedRepository) {
        this.feedValidator = feedValidator;
        this.feedRepository = feedRepository;
    }

    @Override
    public Completable execute(final String fedUrl) {
        return feedValidator.validateUrl(fedUrl)
                            .andThen(feedRepository.createNewFeed(fedUrl));
    }
}
