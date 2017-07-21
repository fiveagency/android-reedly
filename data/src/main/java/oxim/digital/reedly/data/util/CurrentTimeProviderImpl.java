package oxim.digital.reedly.data.util;

public class CurrentTimeProviderImpl implements CurrentTimeProvider {

    @Override
    public long getCurrentTime() {
        return System.currentTimeMillis();
    }
}
