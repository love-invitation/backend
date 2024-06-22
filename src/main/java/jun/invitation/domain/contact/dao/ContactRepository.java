package jun.invitation.domain.contact.dao;

import jun.invitation.domain.contact.domain.Contact;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContactRepository extends JpaRepository<Contact, Long> {
}
