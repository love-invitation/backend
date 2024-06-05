package jun.invitation.domain.gallery.Service;

import jun.invitation.aws.s3.service.S3UploadService;
import jun.invitation.domain.gallery.Gallery;
import jun.invitation.domain.gallery.dao.GalleryRepository;
import jun.invitation.domain.invitation.domain.Invitation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class GalleryService {

    private final GalleryRepository galleryRepository;
    private final S3UploadService s3UploadService;

    @Transactional
    public void delete(List<Gallery> galleries) {
        for (Gallery g : galleries) {
            s3UploadService.delete(g.getStoreFileName());
            galleryRepository.delete(g);
        }
    }

    @Transactional
    public void delete(Long invitationId) {
        galleryRepository.deleteByInvitationId(invitationId);
    }

    public void save(List<MultipartFile> gallery, Invitation invitation) throws IOException {

        Long sequence = 1L;

        for (MultipartFile file : gallery) {
            Map<String, String> savedFileMap = s3UploadService.saveFile(file);

            if (savedFileMap != null) {
                String originFileName = savedFileMap.get("originFileName");
                String storeFileName = savedFileMap.get("storeFileName");
                String savedUrlPath = savedFileMap.get("imageUrl");

                Gallery newGallery = new Gallery(originFileName,storeFileName,sequence++,savedUrlPath);
                newGallery.setInvitation(invitation);
            }
        }
    }
}
