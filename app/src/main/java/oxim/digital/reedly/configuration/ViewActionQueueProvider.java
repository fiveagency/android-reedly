package oxim.digital.reedly.configuration;

public interface ViewActionQueueProvider {

    ViewActionHandler queueFor(String queueId);

    void dispose(String queueId);
}
