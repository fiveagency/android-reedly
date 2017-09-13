package oxim.digital.reedly.configuration;

import java.util.UUID;

public final class RandomViewIdGenerator implements ViewIdGenerator {

    @Override
    public String newId() {
        return UUID.randomUUID().toString();
    }
}
