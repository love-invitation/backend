package jun.invitation;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.Date;
import java.util.TimeZone;

@SpringBootApplication
@EnableJpaAuditing
public class InvitationApplication {

	public static void main(String[] args) {
		SpringApplication.run(InvitationApplication.class, args);
	}

}
