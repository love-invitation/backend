package jun.invitation.domain.gallery.Service;

import jun.invitation.domain.gallery.Gallery;
import jun.invitation.domain.gallery.dao.GalleryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class GalleryService {

    private final GalleryRepository galleryRepository;

    @Transactional
    public void saveGallery(Gallery gallery) {
        galleryRepository.save(gallery);
    }

    @Transactional
    public void delete(Gallery g) {
        galleryRepository.delete(g);
    }
}
