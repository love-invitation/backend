package jun.invitation.domain.invitation.dao;

import jun.invitation.domain.invitation.domain.Invitation;
import jun.invitation.domain.shareThumbnail.domain.ShareThumbnail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.Optional;

public interface InvitationRepository extends JpaRepository<Invitation, Long> {
    Optional<Invitation> findByTsid(Long tsid);
    void deleteByWedding_DateBefore(LocalDateTime now);

    @Query("select i.shareThumbnail from Invitation i where i.tsid = :tsid")
    ShareThumbnail findShareThumbnailByProductId(@Param(value = "tsid") Long tsid);
}