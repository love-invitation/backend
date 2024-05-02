package jun.invitation.domain.guestbook.dao;

import jun.invitation.domain.guestbook.domain.Guestbook;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GuestbookRepository extends JpaRepository<Guestbook, Long> {
}
