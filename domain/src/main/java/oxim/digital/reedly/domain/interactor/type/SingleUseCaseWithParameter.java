package oxim.digital.reedly.domain.interactor.type;

import rx.Single;

public interface SingleUseCaseWithParameter<P, R> {

    Single<R> execute(P parameter);
}
