package jun.invitation.mock;

import jun.invitation.global.service.port.UuidHolder;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class TestUuidHolder implements UuidHolder {
    private final String uuid;
    @Override
    public String random() {
        return uuid;
    }
}
