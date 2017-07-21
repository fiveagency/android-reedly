package oxim.digital.reedly.domain.interactor.type;

import rx.Completable;

public interface CompletableUseCase {

    Completable execute();
}
