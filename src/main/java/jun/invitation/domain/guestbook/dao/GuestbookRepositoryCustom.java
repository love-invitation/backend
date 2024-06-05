package jun.invitation.domain.guestbook.dao;

public interface GuestbookRepositoryCustom {
    void deleteByInvitationId(Long invitationId);
}
