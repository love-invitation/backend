package jun.invitation.domain.shareThumbnail.service;

import jun.invitation.TestDataInit;
import jun.invitation.domain.shareThumbnail.domain.ShareThumbnail;
import jun.invitation.domain.shareThumbnail.dto.ShareThumbnailDto;
import jun.invitation.image.domain.Image;
import jun.invitation.mock.FakeImageUploader;
import jun.invitation.mock.TestUuidHolder;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.annotation.Rollback;

import java.io.IOException;

@Slf4j
@SpringBootTest
@TestDataInit
class ShareThumbnailServiceTest {

    @Autowired
    private ShareThumbnailService shareThumbnailService;

    @Autowired
    private FakeImageUploader fakeImageUploader;

    @Test
    void ShareThumbnailService는_ThumbnailDto와_MultipartFile로_ShareThumbnail을_생성할_수_있다() throws IOException {
        //given
        ShareThumbnailDto shareThumbnailDto = new ShareThumbnailDto(
                "썸네일 제목",
                "썸네일 내용"
        );

        MockMultipartFile file = new MockMultipartFile(
                "청첩장 썸네일 이미지",
                "thumbnail.png",
                MediaType.IMAGE_PNG_VALUE,
                "thumbnail".getBytes()
        );

        //when
        ShareThumbnail shareThumbnail = shareThumbnailService.create(file, shareThumbnailDto);

        //then
        Image thumbnailImage = shareThumbnail.getImage();
        Assertions.assertThat(shareThumbnail.getTitle()).isEqualTo("썸네일 제목");
        Assertions.assertThat(shareThumbnail.getContents()).isEqualTo("썸네일 내용");
        Assertions.assertThat(thumbnailImage.getUrl()).isEqualTo("https://test.com/ggdfsfsd-sdfadssdsa-sdfadsafad.png");
        Assertions.assertThat(thumbnailImage.getOriginName()).isEqualTo("thumbnail.png");
        Assertions.assertThat(thumbnailImage.getStoreFileName()).isEqualTo("ggdfsfsd-sdfadssdsa-sdfadsafad.png");
    }

    @Test
    void ShareThumbnailService는_ImageUploader를_통해_Image를_삭제할_수_있다() throws IOException {
        //given
        ShareThumbnailDto shareThumbnailDto = new ShareThumbnailDto(
                "썸네일 제목",
                "썸네일 내용"
        );

        MockMultipartFile file = new MockMultipartFile(
                "청첩장 썸네일 이미지",
                "thumbnail.png",
                MediaType.IMAGE_PNG_VALUE,
                "thumbnail".getBytes()
        );

        ShareThumbnail shareThumbnail = shareThumbnailService.create(file, shareThumbnailDto);

        //when
        shareThumbnailService.deleteImage(shareThumbnail);

        //then
        Assertions.assertThat(fakeImageUploader.hasImg(shareThumbnail.getImage().getStoreFileName())).isFalse();
    }
    
    @Test
    public void ShareThumbnailService는_ShareThumbnailDto와_MultipartFile를_통해_ShareThumbnail을_업데이트_할_수_있다() throws Exception {
        //given
        ShareThumbnailDto shareThumbnailDto = new ShareThumbnailDto(
                "썸네일 제목",
                "썸네일 내용"
        );

        MockMultipartFile file = new MockMultipartFile(
                "청첩장 썸네일 이미지",
                "thumbnail.png",
                MediaType.IMAGE_PNG_VALUE,
                "thumbnail".getBytes()
        );

        ShareThumbnailDto newShareThumbnailDto = new ShareThumbnailDto(
                "업데이트 썸네일 제목",
                "업데이트 썸네일 내용"
        );

        MockMultipartFile newFile = new MockMultipartFile(
                "청첩장 업데이트 썸네일 이미지",
                "newThumbnail.png",
                MediaType.IMAGE_PNG_VALUE,
                "newThumbnail".getBytes()
        );

        ShareThumbnail shareThumbnail = shareThumbnailService.create(file, shareThumbnailDto);

        //when
        fakeImageUploader.changeUuid("update-ggdfsfsd-sdfadssdsa-sdfadsafad");
        shareThumbnailService.update(newShareThumbnailDto, shareThumbnail, newFile);

        //then
        Image thumbnailImage = shareThumbnail.getImage();
        Assertions.assertThat(shareThumbnail.getTitle()).isEqualTo("업데이트 썸네일 제목");
        Assertions.assertThat(shareThumbnail.getContents()).isEqualTo("업데이트 썸네일 내용");
        Assertions.assertThat(thumbnailImage.getUrl()).isEqualTo("https://test.com/update-ggdfsfsd-sdfadssdsa-sdfadsafad.png");
        Assertions.assertThat(thumbnailImage.getOriginName()).isEqualTo("newThumbnail.png");
        Assertions.assertThat(thumbnailImage.getStoreFileName()).isEqualTo("update-ggdfsfsd-sdfadssdsa-sdfadsafad.png");
    }

}