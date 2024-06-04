package jun.invitation.domain.gallery.dao;

import jun.invitation.domain.gallery.Gallery;

import java.util.List;

public interface GalleryRepositoryCustom {
    void deleteByGalleries(List<Gallery> galleries);
}
