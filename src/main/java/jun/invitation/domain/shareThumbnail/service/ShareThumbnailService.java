package jun.invitation.domain.shareThumbnail.service;

import jun.invitation.aws.s3.ImageUploadKey;
import jun.invitation.aws.s3.ImageUploader;
import jun.invitation.domain.shareThumbnail.domain.ShareThumbnail;
import jun.invitation.domain.shareThumbnail.dto.ShareThumbnailDto;
import jun.invitation.image.domain.Image;
import jun.invitation.image.service.ImageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ShareThumbnailService {

    private final ImageUploader imageUploader;
    private final ImageService imageService;

    @Transactional
    public ShareThumbnail create(MultipartFile shareThumbImage, ShareThumbnailDto shareThumbnailDto) throws IOException {

        Optional<MultipartFile> multipartOpt = Optional.ofNullable(shareThumbImage);
        Optional<ShareThumbnailDto> thumbnailDtoOpt = Optional.ofNullable(shareThumbnailDto);

        return multipartOpt.flatMap(multipartFile -> thumbnailDtoOpt.map(thumbnailDto -> {
                    Map<ImageUploadKey, String> map = imageUploader.upload(multipartFile);
                    Image image = imageService.create(map);
                    return ShareThumbnail.builder()
                            .title(shareThumbnailDto.getTitle())
                            .contents(shareThumbnailDto.getContents())
                            .image(image)
                            .build();
                }))
                .orElse(null);
    }

    @Transactional
    public void deleteImage(ShareThumbnail shareThumbnail) {
        Optional.ofNullable(shareThumbnail)
                .map(ShareThumbnail::getImage)
                .map(Image::getStoreFileName)
                .ifPresent(imageUploader::delete);
    }

    @Transactional
    public void update(ShareThumbnailDto updateThumbnail, ShareThumbnail thumbnail, MultipartFile multipartFile) throws IOException {

        if (cannotUpdate(updateThumbnail, thumbnail, multipartFile))
            return;

        // text update
        thumbnail.updateText(updateThumbnail.getTitle(), updateThumbnail.getContents());

        // image update
        deleteCurrentImage(thumbnail);
        updateImage(multipartFile, thumbnail);
    }

    private boolean cannotUpdate(ShareThumbnailDto updateThumbnail, ShareThumbnail thumbnail, MultipartFile multipartFile) {
        return ObjectUtils.isEmpty(updateThumbnail)
                || ObjectUtils.isEmpty(multipartFile)
                || ObjectUtils.isEmpty(thumbnail);
    }

    private void deleteCurrentImage(ShareThumbnail shareThumbnail) {
        Optional.ofNullable(shareThumbnail.getImage())
                .map(Image::getStoreFileName)
                .ifPresent(imageUploader::delete);
    }

    private void updateImage(MultipartFile multipartFile, ShareThumbnail shareThumbnail) {
        Map<ImageUploadKey, String> map = imageUploader.upload(multipartFile);
        Image image = imageService.create(map);
        shareThumbnail.registerImage(image);
    }
}