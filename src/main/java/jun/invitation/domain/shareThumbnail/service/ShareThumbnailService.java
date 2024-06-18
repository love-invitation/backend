package jun.invitation.domain.shareThumbnail.service;

import jun.invitation.aws.s3.service.S3UploadService;
import jun.invitation.domain.invitation.domain.Invitation;
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
@Transactional
@RequiredArgsConstructor
public class ShareThumbnailService {

    private final ShareThumbnailRepository shareThumbnailRepository;
    private final S3UploadService s3UploadService;

    public ShareThumbnail create(Invitation invitation, MultipartFile shareThumbImage, ShareThumbnailDto shareThumbnailDto) throws IOException {

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

}
