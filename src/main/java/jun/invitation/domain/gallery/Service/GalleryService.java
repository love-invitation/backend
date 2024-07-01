package jun.invitation.domain.gallery.Service;

import jun.invitation.aws.s3.service.S3UploadService;
import jun.invitation.domain.gallery.Gallery;
import jun.invitation.domain.gallery.dao.GalleryRepository;
import jun.invitation.domain.invitation.domain.Invitation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
@Slf4j
public class GalleryService {

    private final GalleryRepository galleryRepository;
    private final S3UploadService s3UploadService;

    @Transactional
    public void delete(List<Gallery> galleries) {
        if (galleries.isEmpty()) {
            return;
        }
        galleries.forEach(g -> {
            s3UploadService.delete(g.getStoreFileName());
        });
        galleryRepository.deleteByGalleries(galleries);
    }

    public void save(List<MultipartFile> gallery, Invitation invitation) throws IOException {

        Long sequence = 1L;

        List<CompletableFuture<Map<String, String>>> futures = gallery.stream()
                .map(s3UploadService::saveFileAsync)
                .toList();

        for (CompletableFuture<Map<String, String>> future : futures) {
            Map<String, String> savedFileMap = future.join();
            if (savedFileMap != null) {
                String originFileName = savedFileMap.get("originFileName");
                String storeFileName = savedFileMap.get("storeFileName");
                String savedUrlPath = savedFileMap.get("imageUrl");

                Gallery newGallery = new Gallery(originFileName, storeFileName, sequence++, savedUrlPath);
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
