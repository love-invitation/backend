package jun.invitation.mock;

import jun.invitation.global.service.port.UuidHolder;
import lombok.RequiredArgsConstructor;

public class TestUuidHolder implements UuidHolder {
    private String uuid;

    public TestUuidHolder(String uuid) {
        this.uuid = uuid;
    }

    @Override
    public String random() {
        return uuid;
    }

    public void changeUuid(String updateUuid) {
        this.uuid = updateUuid;
    }
}
