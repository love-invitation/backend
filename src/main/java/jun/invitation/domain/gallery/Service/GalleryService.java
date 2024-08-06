package jun.invitation.domain.gallery.Service;

import jun.invitation.global.aws.s3.ImageUploadKey;
import jun.invitation.global.aws.s3.ImageUploader;
import jun.invitation.domain.gallery.Gallery;
import jun.invitation.domain.gallery.dao.GalleryRepository;
import jun.invitation.domain.invitation.domain.Invitation;
import jun.invitation.domain.image.domain.Image;
import jun.invitation.domain.image.service.ImageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
@Slf4j
public class GalleryService {

    private final GalleryRepository galleryRepository;
    private final ImageService imageService;

    private final ImageUploader imageUploader;

    @Transactional
    public void delete(List<Gallery> galleries) {
        if (ObjectUtils.isEmpty(galleries))
            return;

        galleries.forEach(g -> imageUploader.delete(g.getImage().getStoreFileName()));
        galleryRepository.deleteByGalleries(galleries);
    }

    @Transactional
    public void create(List<MultipartFile> gallery, Invitation invitation) {

        if (ObjectUtils.isEmpty(gallery))
            return;


        Long sequence = 1L;

        List<CompletableFuture<Map<ImageUploadKey, String>>> futures = gallery.stream()
                .map(imageUploader::uploadAsync)
                .toList();

        for (CompletableFuture<Map<ImageUploadKey, String>> future : futures) {
            Map<ImageUploadKey, String> map = future.join();
            if (map != null) {
                Image image = imageService.create(map);
                Gallery newGallery = new Gallery(sequence++, image);
                newGallery.setInvitation(invitation);
            }
        }
    }

    @Transactional
    public void update(Invitation invitation, List<MultipartFile> newGalleries) {
        List<Gallery> currentGalleries = invitation.getGallery();

        if (!ObjectUtils.isEmpty(currentGalleries)) {
            delete(currentGalleries);
            invitation.getGallery().clear();
        }

        if(!ObjectUtils.isEmpty(newGalleries)) {
            create(newGalleries, invitation);
        }
    }
}
