package jun.invitation.domain.guestbook.dao;

import jun.invitation.domain.gallery.Gallery;
import jun.invitation.domain.guestbook.domain.Guestbook;

import java.util.List;

public interface GuestbookRepositoryCustom {
    void deleteByGuestbooks(List<Guestbook> guestbooks);
}
