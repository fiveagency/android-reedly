package oxim.digital.reedly.domain.interactor.type;

import rx.Completable;

public interface CompletableUseCaseWithParameter<P> {

    Completable execute(P parameter);
}
