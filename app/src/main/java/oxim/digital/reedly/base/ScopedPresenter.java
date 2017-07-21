package oxim.digital.reedly.base;

public interface ScopedPresenter {

    void start();

    void activate();

    void deactivate();

    void destroy();

    void back();
}
