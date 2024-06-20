package jun.invitation.domain.shareThumbnail.service;

import jun.invitation.aws.s3.service.S3UploadService;
import jun.invitation.domain.shareThumbnail.dto.ShareThumbnailDto;
import jun.invitation.domain.shareThumbnail.dao.ShareThumbnailRepository;
import jun.invitation.domain.shareThumbnail.domain.ShareThumbnail;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ShareThumbnailService {

    private final ShareThumbnailRepository shareThumbnailRepository;
    private final S3UploadService s3UploadService;

    @Transactional
    public ShareThumbnail create(MultipartFile shareThumbImage, ShareThumbnailDto shareThumbnailDto) throws IOException {

        String savedUrlPath = null;
        String originFileName = null;
        String storeFileName = null;
        String title = null;
        String contents = null;

        if (shareThumbImage != null) {
            Map<String, String> savedFileMap = s3UploadService.saveFile(shareThumbImage);

            originFileName = savedFileMap.get("originFileName");
            storeFileName = savedFileMap.get("storeFileName");
            savedUrlPath = savedFileMap.get("imageUrl");
        }

        if (shareThumbnailDto != null){
            title = shareThumbnailDto.getShareTitle();
            contents = shareThumbnailDto.getShareContents();
        }

        return new ShareThumbnail(title, contents, savedUrlPath, originFileName, storeFileName);
    }

    @Transactional
    public void deleteS3Image(ShareThumbnail shareThumbnail) {
        if (shareThumbnail != null) {
            String imageStoreFileName = shareThumbnail.getImageStoreFileName();
            if (imageStoreFileName != null) {
                s3UploadService.delete(imageStoreFileName);
            }
        }
    }

    // TODO : shareThumbnail O -> shareThumbnail = null 로 보낼 시,
    @Transactional
    public void update(ShareThumbnailDto newShareThumbnail, ShareThumbnail currentShareThumbnail, MultipartFile newShareThumbnailImage) throws IOException {

        currentShareThumbnail.updateTextValue(
                newShareThumbnail.getShareTitle(),
                newShareThumbnail.getShareContents()
                );

        String storeFileName = currentShareThumbnail.getImageStoreFileName();
        if (currentShareThumbnail.getImageStoreFileName() != null) {
            s3UploadService.delete(storeFileName);
        }

        if (newShareThumbnailImage != null) {

            Map<String, String> result = s3UploadService.saveFile(newShareThumbnailImage);

            currentShareThumbnail.updateImageValue(
                    result.get("originFileName"),
                    result.get("storeFileName"),
                    result.get("imageUrl")
            );
        } else {
            currentShareThumbnail.updateImageValue(
                    null,
                    null,
                    null
            );
        }
    }
}
