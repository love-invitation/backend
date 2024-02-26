package jun.invitation.domain.product.invitation.service;

import jun.invitation.domain.auth.PrincipalDetails;
import jun.invitation.domain.aws.s3.service.S3UploadService;
import jun.invitation.domain.product.domain.Product;
import jun.invitation.domain.product.invitation.dao.InvitationRepository;
import jun.invitation.domain.product.invitation.domain.Gallery.Gallery;
import jun.invitation.domain.product.invitation.domain.Gallery.Service.GalleryService;
import jun.invitation.domain.product.invitation.domain.Invitation;
import jun.invitation.domain.product.invitation.dto.InvitationDto;
import jun.invitation.domain.product.productInfo.domain.ProductInfo;
import jun.invitation.domain.product.productInfo.service.ProductInfoService;
import jun.invitation.domain.product.service.ProductService;
import jun.invitation.domain.user.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service @Slf4j
@RequiredArgsConstructor
public class InvitationService {

    private final S3UploadService s3UploadService;
    private final InvitationRepository invitationRepository;
    private final GalleryService galleryService;
    private final ProductService productService;
    private final ProductInfoService productInfoService;

    /**
     * 1. invitationDto -> invitation 객체에 mapping : Done
     * 2. S3에 저장하고 반환 받은 경로를 값으로 gallery -> List<Gallery> 객체 생성
     * 3. S3에 저장하고 invitation 객체 안에 mainImageUrl에 값 저장
     * 4. 반환
     */
    public Invitation createInvitation(InvitationDto invitationdto, List<MultipartFile> gallery, MultipartFile mainImage) throws IOException {

        Invitation invitation = invitationdto.toInvitation();

        invitation.registerUserProductInfo(findUser(),
                productInfoService.findById(invitationdto.getProductInfoId()).orElseGet(()-> new ProductInfo()));

        Long sequence = 1L;
        for (MultipartFile file : gallery) {
            String savedUrlPath = s3UploadService.saveFile(file);

            log.info("savedUrlPath={}",savedUrlPath);
            Gallery newGallery = new Gallery(sequence++,savedUrlPath);
            newGallery.setInvitation(invitation);
            galleryService.saveGallery(newGallery);
        }

        /* main picture... */
        invitation.registerMainImage(s3UploadService.saveFile(mainImage));

        return invitation;

    }

    public User findUser() {
        PrincipalDetails principalDetails = (PrincipalDetails) SecurityContextHolder.
                getContext().getAuthentication().getPrincipal();

        return principalDetails.getUser();
    }

    public Long saveInvitation(Invitation invitation) {
        return invitationRepository.save(invitation).getId();
    }
}
