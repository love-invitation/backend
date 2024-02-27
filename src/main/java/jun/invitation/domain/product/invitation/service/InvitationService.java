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
import java.util.Map;
import java.util.Optional;

@Service @Slf4j
@RequiredArgsConstructor
public class InvitationService {

    private final S3UploadService s3UploadService;
    private final InvitationRepository invitationRepository;
    private final GalleryService galleryService;
    private final ProductInfoService productInfoService;

    public Invitation createInvitation(InvitationDto invitationdto, List<MultipartFile> gallery, MultipartFile mainImage) throws IOException {

        Invitation invitation = invitationdto.toInvitation();

        invitation.registerUserProductInfo(findUser(),
                productInfoService.findById(invitationdto.getProductInfoId()).orElseGet(()-> new ProductInfo()));

        saveGallery(gallery, invitation);

        /* main picture save */
        invitation.registerMainImage(s3UploadService.saveFile(mainImage));

        return invitation;

    }

    private void saveGallery(List<MultipartFile> gallery, Invitation invitation) throws IOException {

        Long sequence = 1L;

        for (MultipartFile file : gallery) {
            Map<String, String> savedFileMap = s3UploadService.saveFile(file);

            if (savedFileMap != null) {
                String originFileName = savedFileMap.get("originFileName");
                String storeFileName = savedFileMap.get("storeFileName");
                String savedUrlPath = savedFileMap.get("imageUrl");

                Gallery newGallery = new Gallery(originFileName,storeFileName,sequence++,savedUrlPath);
                newGallery.setInvitation(invitation);
                galleryService.saveGallery(newGallery);
            }

        }
    }

    public User findUser() {
        PrincipalDetails principalDetails = (PrincipalDetails) SecurityContextHolder.
                getContext().getAuthentication().getPrincipal();

        return principalDetails.getUser();
    }

    public Long saveInvitation(Invitation invitation) {
        return invitationRepository.save(invitation).getId();
    }

    public void deleteInvitation(Long invitationId) {
        Optional<Invitation> invitationOpt = invitationRepository.findById(invitationId);

        if (invitationOpt.isPresent()) {
            Invitation invitation = invitationOpt.get();
            List<Gallery> galleryList = invitation.getGallery();

            for (Gallery gallery : galleryList) {
                s3UploadService.delete(gallery.getStoreFileName());
            }
            s3UploadService.delete(invitation.getMainImageStoreFileName());

            invitationRepository.delete(invitation);
        }
    }
}
