package jun.invitation;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Slf4j
public class InvitationApplication {

	public static void main(String[] args) {
		log.info("==0209==");
		SpringApplication.run(InvitationApplication.class, args);

	}

}
