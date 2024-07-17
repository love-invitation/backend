package jun.invitation.global.infrastructure;

import com.github.f4b6a3.tsid.TsidCreator;
import jun.invitation.global.service.port.IdentifierGenerator;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Profile("default")
@Component
public class TsidGenerator implements IdentifierGenerator {
    @Override
    public Long generate() {
        return TsidCreator.getTsid().toLong();
    }
}
