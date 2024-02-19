package jun.invitation.domain.product.invitation.service;

import jun.invitation.domain.aws.s3.service.S3UploadService;
import jun.invitation.domain.product.invitation.dao.InvitationRepository;
import jun.invitation.domain.product.invitation.domain.Gallery;
import jun.invitation.domain.product.invitation.domain.Invitation;
import jun.invitation.domain.product.invitation.dto.InvitationDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service @Slf4j
@RequiredArgsConstructor
public class InvitationService {

    private final S3UploadService s3UploadService;
    private final InvitationRepository invitationRepository;

    /**
     * 1. invitationDto -> invitation 객체에 mapping :
     * 2. S3에 저장하고 반환 받은 경로를 값으로 gallery -> List<Gallery> 객체 생성
     * 3. S3에 저장하고 invitation 객체 안에 mainImageUrl에 값 저장
     * 4. 반환
     */
    public Invitation createInvitation(InvitationDto invitationdto, List<MultipartFile> gallery, MultipartFile mainImage) {

        log.info("invitationDto = {}" , invitationdto.toString());

        Invitation invitation = invitationdto.toInvitation();
        log.info(invitation.toString());

//        for (MultipartFile file : gallery) {
//
//        }

        return invitation;

    }
//    public String saveToS3(List<MultipartFile> gallery) throws IOException {
//
//        List<Gallery> galleries = new ArrayList<>();
//        for (MultipartFile file : gallery) {
//            String s = s3UploadService.saveFile(file);
//
//            /* 양방향 매핑 .. */
//            new Gallery();
//        }
//    }
}
