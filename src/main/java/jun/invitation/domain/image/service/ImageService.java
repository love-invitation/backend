package jun.invitation.domain.image.service;

import jun.invitation.global.aws.s3.ImageUploadKey;
import jun.invitation.global.aws.s3.ImageUploader;
import jun.invitation.domain.image.dao.ImageRepository;
import jun.invitation.domain.invitation.domain.Invitation;
import jun.invitation.domain.image.domain.Image;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static jun.invitation.global.aws.s3.ImageUploadKey.*;

@RequiredArgsConstructor
@Service
public class ImageService {

    private final ImageRepository imageRepository;
    private final ImageUploader imageUploader;

    private Image save(Image image) {
        return imageRepository.save(image);
    }

    @Transactional
    public void delete(List<Image> images) {
        imageRepository.deleteByImages(images);
    }

    private Image fromMap(Map<ImageUploadKey, String> map) {
        return Image.builder()
                .url(map.get(IMAGE_URL))
                .originName(map.get(ORIGIN_FILE_NAME))
                .storeFileName(map.get(STORE_FILE_NAME))
                .build();
    }

    @Transactional
    public Image create(Map<ImageUploadKey, String> map) {
        Image image = fromMap(map);
        return save(image);
    }

    @Transactional
    public Image saveWithImageUpload(MultipartFile multipartFile) {
        Map<ImageUploadKey, String> map = imageUploader.upload(multipartFile);
        Image image = fromMap(map);
        return save(image);
    }

    @Transactional
    public void update(MultipartFile multipartFile, Invitation invitation) {
        // 기존 이미지 삭제
        Optional.ofNullable(invitation.getMainImage())
                .map(Image::getStoreFileName)
                .ifPresent(storeName-> {
                    imageUploader.delete(storeName);
                    invitation.registerMainImage(null);
                });

        Map<ImageUploadKey, String> map = imageUploader.upload(multipartFile);

        if (!ObjectUtils.isEmpty(map)) {
            Image image = create(map);
            invitation.registerMainImage(image);
        }
    }
}