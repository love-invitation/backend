package jun.invitation.image.service;

import jun.invitation.aws.s3.ImageUploadKey;
import jun.invitation.image.dao.ImageRepository;
import jun.invitation.image.domain.Image;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

import static jun.invitation.aws.s3.ImageUploadKey.*;

@RequiredArgsConstructor
@Service
public class ImageService {

    private final ImageRepository imageRepository;

    @Transactional
    public Image save(Image image) {
        return imageRepository.save(image);
    }

    @Transactional
    public void delete(List<Image> images) {
        imageRepository.deleteByImages(images);
    }

    public Image fromMap(Map<ImageUploadKey, String> map) {
        return Image.builder()
                .url(map.get(IMAGE_URL))
                .originName(map.get(ORIGIN_FILE_NAME))
                .storeFileName(map.get(STORE_FILE_NAME))
                .build();
    }
}