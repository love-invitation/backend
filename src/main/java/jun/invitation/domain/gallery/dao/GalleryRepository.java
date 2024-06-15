package jun.invitation.domain.gallery.dao;

import jun.invitation.domain.gallery.Gallery;
import jun.invitation.domain.invitation.domain.Invitation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface GalleryRepository extends JpaRepository<Gallery, Long> {

    @Modifying
    @Query(value = "delete from Gallery g where g in :galleries")
    void deleteByGalleries(@Param(value = "galleries") List<Gallery> galleries);

}
