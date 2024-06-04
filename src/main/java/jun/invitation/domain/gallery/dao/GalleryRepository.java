package jun.invitation.domain.gallery.dao;

import jun.invitation.domain.gallery.Gallery;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GalleryRepository extends JpaRepository<Gallery, Long>, GalleryRepositoryCustom {
}
