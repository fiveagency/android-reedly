package oxim.digital.reedly.domain.interactor.type;

import rx.Observable;

public interface UseCase<T> {

    Observable<T> execute();
}
