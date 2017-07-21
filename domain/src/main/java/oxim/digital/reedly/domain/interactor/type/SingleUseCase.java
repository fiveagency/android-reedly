package oxim.digital.reedly.domain.interactor.type;

import rx.Single;

public interface SingleUseCase<T> {

    Single<T> execute();
}
