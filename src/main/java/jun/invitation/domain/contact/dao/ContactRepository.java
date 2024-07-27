package jun.invitation.domain.contact.dao;

import jun.invitation.domain.contact.domain.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ContactRepository extends JpaRepository<Contact, Long> {

    @Modifying
    @Query(value = "delete from Contact c where c.product.id = :id")
    void deleteByProductId(@Param(value = "id") Long productId);

}
