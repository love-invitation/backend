package jun.invitation.domain.shareThumbnail.service;

import jun.invitation.aws.s3.ImageUploadKey;
import jun.invitation.aws.s3.ImageUploader;
import jun.invitation.domain.shareThumbnail.domain.ShareThumbnail;
import jun.invitation.domain.shareThumbnail.dto.ShareThumbnailDto;
import jun.invitation.image.domain.Image;
import jun.invitation.image.service.ImageService;
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
    private final ImageService imageService;

    @Transactional
    public ShareThumbnail create(MultipartFile shareThumbImage, ShareThumbnailDto shareThumbnailDto) throws IOException {

        Image image = null;
        String title = null;
        String contents = null;

        if (shareThumbImage != null) {
            Map<ImageUploadKey, String> map = imageUploader.upload(shareThumbImage);

            image = Image.builder()
                    .url(map.get(IMAGE_URL))
                    .originName(map.get(ORIGIN_FILE_NAME))
                    .storeFileName(map.get(STORE_FILE_NAME))
                    .build();

            imageService.save(image);
        }

        if (shareThumbnailDto != null){
            title = shareThumbnailDto.getTitle();
            contents = shareThumbnailDto.getContents();
        }

        return new ShareThumbnail(title, contents, image);
    }

    @Transactional
    public void deleteImage(ShareThumbnail shareThumbnail) {
        if (shareThumbnail != null) {
            String imageStoreFileName = shareThumbnail.getImage().getStoreFileName();
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

        String storeFileName = currentShareThumbnail.getImage() == null ? null : currentShareThumbnail.getImage().getStoreFileName();
        if (storeFileName != null) {
            imageUploader.delete(storeFileName);
        }

        if (newShareThumbnailImage != null) {

            Map<ImageUploadKey, String> savedFileMap = imageUploader.upload(newShareThumbnailImage);

            Image image = Image.builder()
                    .url(savedFileMap.get(IMAGE_URL))
                    .originName(savedFileMap.get(ORIGIN_FILE_NAME))
                    .storeFileName(savedFileMap.get(STORE_FILE_NAME))
                    .build();

            imageService.save(image);

            currentShareThumbnail.updateImageValue(image);
        } else {
            currentShareThumbnail.updateImageValue(null);
        }
    }
}
