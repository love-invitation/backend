package jun.invitation.global.infrastructure;

import jun.invitation.global.service.port.UuidHolder;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class SystemUuidHolder implements UuidHolder {

    @Override
    public String random() {
        return UUID.randomUUID().toString();
    }
}
