package jun.invitation.domain.gallery.Service;

import jun.invitation.aws.s3.ImageUploadKey;
import jun.invitation.aws.s3.ImageUploader;
import jun.invitation.domain.gallery.Gallery;
import jun.invitation.domain.gallery.dao.GalleryRepository;
import jun.invitation.domain.invitation.domain.Invitation;
import jun.invitation.image.domain.Image;
import jun.invitation.image.service.ImageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import static jun.invitation.aws.s3.ImageUploadKey.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class GalleryService {

    private final GalleryRepository galleryRepository;
    private final ImageService imageService;

    private final ImageUploader imageUploader;

    @Transactional
    public void delete(List<Gallery> galleries) {
        if (galleries.isEmpty()) {
            return;
        }
        galleries.forEach(g -> {
            imageUploader.delete(g.getImage().getStoreFileName());
        });
        galleryRepository.deleteByGalleries(galleries);
    }

    @Transactional
    public void save(List<MultipartFile> gallery, Invitation invitation) throws IOException {

        Long sequence = 1L;

        List<CompletableFuture<Map<ImageUploadKey, String>>> futures = gallery.stream()
                .map(imageUploader::uploadAsync)
                .toList();

        for (CompletableFuture<Map<ImageUploadKey, String>> future : futures) {
            Map<ImageUploadKey, String> map = future.join();
            if (map != null) {
                Image image = imageService.fromMap(map);

                imageService.save(image);
                Gallery newGallery = new Gallery(sequence++, image);
                newGallery.setInvitation(invitation);
            }
        }
    }

    /**
     * 1. 기존 gallery o, new gallery o : 기존 gallery 삭제, new gallery 저장
     * 2. 기존 gallery o, new gallery x : 기존 gallery 삭제
     * 3. 기존 gallery x, new gallery o : new gallery 저장
     * 4. 기존 gallery x, new gallery x : 아무 행동 x
     */
    public void update(List<Gallery> currentGalleries, Invitation invitation, List<MultipartFile> newGalleries) throws IOException {
        // 1.
        if (!currentGalleries.isEmpty() && newGalleries != null) {
            delete(currentGalleries);
            invitation.getGallery().clear();
            save(newGalleries, invitation);
        } else if (!currentGalleries.isEmpty() && newGalleries == null){
            // 2.
            delete(currentGalleries);
        } else if (currentGalleries.isEmpty() && newGalleries != null) {
            // 3.
            save(newGalleries, invitation);
        }
    }
}
