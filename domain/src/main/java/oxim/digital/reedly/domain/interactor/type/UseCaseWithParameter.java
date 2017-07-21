package oxim.digital.reedly.domain.interactor.type;

import rx.Observable;

public interface UseCaseWithParameter<P, R> {

    Observable<R> execute(P parameter);
}
