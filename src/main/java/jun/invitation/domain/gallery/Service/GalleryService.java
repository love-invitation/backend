package jun.invitation.domain.gallery.Service;

import jun.invitation.domain.gallery.Gallery;
import jun.invitation.domain.gallery.dao.GalleryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GalleryService {

    private final GalleryRepository galleryRepository;

    public void saveGallery(Gallery gallery) {
        galleryRepository.save(gallery);
    }

    public void delete(Gallery g) {
        galleryRepository.delete(g);
    }
}
