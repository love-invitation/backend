package jun.invitation.domain.shareThumbnail.service;

import jun.invitation.aws.s3.ImageUploadKey;
import jun.invitation.aws.s3.ImageUploader;
import jun.invitation.domain.shareThumbnail.domain.ShareThumbnail;
import jun.invitation.domain.shareThumbnail.dto.ShareThumbnailDto;
import jun.invitation.mock.FakeImageUploader;
import jun.invitation.mock.TestUuidHolder;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;
import java.util.Map;

import static jun.invitation.aws.s3.ImageUploadKey.*;
import static org.junit.jupiter.api.Assertions.*;

@Slf4j
class ShareThumbnailServiceTest {

    @Test
    void ShareThumbnailDto와_MultipartFile로_ShareThumbnail을_생성할_수_있다() throws IOException {
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


        ImageUploader fakeImageUploader = FakeImageUploader.builder()
                .uuidHolder(new TestUuidHolder("ggdfsfsd-sdfadssdsa-sdfadsafad"))
                .build();

        ShareThumbnailService shareThumbnailService = new ShareThumbnailService(fakeImageUploader);

        //when
        ShareThumbnail shareThumbnail = shareThumbnailService.create(file, shareThumbnailDto);

        //then
        Assertions.assertThat(shareThumbnail.getTitle()).isEqualTo("썸네일 제목");
        Assertions.assertThat(shareThumbnail.getContents()).isEqualTo("썸네일 내용");
        Assertions.assertThat(shareThumbnail.getImageUrl()).isEqualTo("https://test/img.png");
        Assertions.assertThat(shareThumbnail.getImageOriginName()).isEqualTo("thumbnail.png");
        Assertions.assertThat(shareThumbnail.getImageStoreFileName()).isEqualTo("ggdfsfsd-sdfadssdsa-sdfadsafad.png");

    }

}