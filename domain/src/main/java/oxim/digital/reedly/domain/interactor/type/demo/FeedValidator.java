package oxim.digital.reedly.domain.interactor.type.demo;

import rx.Completable;

public interface FeedValidator {

    boolean isValid(String feedUrl);

    Completable validateUrl(String feedUrl);
}
