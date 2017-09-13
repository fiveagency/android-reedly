package oxim.digital.reedly.configuration;

public interface ViewActionQueueProvider {

    ViewActionQueue queueFor(String queueId);

    void dispose(String queueId);
}
