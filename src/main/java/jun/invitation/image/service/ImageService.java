package jun.invitation.image.service;

import jun.invitation.image.dao.ImageRepository;
import jun.invitation.image.domain.Image;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
}