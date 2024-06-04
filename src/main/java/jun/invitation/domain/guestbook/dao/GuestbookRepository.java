package jun.invitation.domain.guestbook.dao;

import jun.invitation.domain.guestbook.domain.Guestbook;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GuestbookRepository extends JpaRepository<Guestbook, Long>, GuestbookRepositoryCustom {
    Page<Guestbook> findByInvitation_id(Long invitationId, Pageable pageable);
}
