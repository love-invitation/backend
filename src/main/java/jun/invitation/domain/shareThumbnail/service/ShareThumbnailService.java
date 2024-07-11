package jun.invitation.domain.shareThumbnail.service;

import jun.invitation.aws.s3.ImageUploadKey;
import jun.invitation.aws.s3.ImageUploader;
import jun.invitation.domain.shareThumbnail.dto.ShareThumbnailDto;
import jun.invitation.domain.shareThumbnail.domain.ShareThumbnail;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

import static jun.invitation.aws.s3.ImageUploadKey.*;

@Service
@RequiredArgsConstructor
public class ShareThumbnailService {

    private final ImageUploader imageUploader;

    @Transactional
    public ShareThumbnail create(MultipartFile shareThumbImage, ShareThumbnailDto shareThumbnailDto) throws IOException {

        String savedUrlPath = null;
        String originFileName = null;
        String storeFileName = null;
        String title = null;
        String contents = null;

        if (shareThumbImage != null) {
            Map<ImageUploadKey, String> savedFileMap = imageUploader.upload(shareThumbImage);

            originFileName = savedFileMap.get(ORIGIN_FILE_NAME);
            storeFileName = savedFileMap.get(STORE_FILE_NAME);
            savedUrlPath = savedFileMap.get(IMAGE_URL);
        }

        if (shareThumbnailDto != null){
            title = shareThumbnailDto.getTitle();
            contents = shareThumbnailDto.getContents();
        }

        return new ShareThumbnail(title, contents, savedUrlPath, originFileName, storeFileName);
    }

    @Transactional
    public void deleteS3Image(ShareThumbnail shareThumbnail) {
        if (shareThumbnail != null) {
            String imageStoreFileName = shareThumbnail.getImageStoreFileName();
            if (imageStoreFileName != null) {
                imageUploader.delete(imageStoreFileName);
            }
        }
    }

    @Transactional
    public void update(ShareThumbnailDto newShareThumbnail, ShareThumbnail currentShareThumbnail, MultipartFile newShareThumbnailImage) throws IOException {

        currentShareThumbnail.updateTextValue(
                newShareThumbnail.getTitle(),
                newShareThumbnail.getContents()
                );

        String storeFileName = currentShareThumbnail.getImageStoreFileName();
        if (currentShareThumbnail.getImageStoreFileName() != null) {
            imageUploader.delete(storeFileName);
        }

        if (newShareThumbnailImage != null) {

            Map<ImageUploadKey, String> savedFileMap = imageUploader.upload(newShareThumbnailImage);

            currentShareThumbnail.updateImageValue(
                    savedFileMap.get(ORIGIN_FILE_NAME),
                    savedFileMap.get(STORE_FILE_NAME),
                    savedFileMap.get(IMAGE_URL)
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
