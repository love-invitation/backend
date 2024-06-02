package jun.invitation.domain.gallery.Service;

import jun.invitation.aws.s3.service.S3UploadService;
import jun.invitation.domain.gallery.Gallery;
import jun.invitation.domain.gallery.dao.GalleryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GalleryService {

    private final GalleryRepository galleryRepository;
    private final S3UploadService s3UploadService;

    @Transactional
    public void deleteByUpdate(List<Gallery> galleries) {
        for (Gallery g : galleries) {
            s3UploadService.delete(g.getStoreFileName());
            galleryRepository.delete(g);
        }
    }

    @Transactional
    public void deleteByDelete(List<Gallery> galleries) {
        for (Gallery g : galleries) {
            s3UploadService.delete(g.getStoreFileName());
        }
    }
}
