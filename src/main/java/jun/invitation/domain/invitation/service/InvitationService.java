package jun.invitation.domain.invitation.service;

import jun.invitation.aws.s3.service.S3UploadService;
import jun.invitation.domain.invitation.dao.InvitationRepository;
import jun.invitation.domain.gallery.Gallery;
import jun.invitation.domain.invitation.dto.InvitationDto;
import jun.invitation.domain.invitation.dto.ResponseInvitationDto;
import jun.invitation.domain.invitation.exception.InvitationAccessDeniedException;
import jun.invitation.domain.invitation.exception.InvitationNotFoundException;
import jun.invitation.domain.priority.service.PriorityService;
import jun.invitation.domain.product.domain.Product;
import jun.invitation.domain.gallery.Service.GalleryService;
import jun.invitation.domain.invitation.domain.Invitation;
import jun.invitation.domain.productInfo.domain.ProductInfo;
import jun.invitation.domain.productInfo.service.ProductInfoService;
import jun.invitation.domain.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import static jun.invitation.global.utils.SecurityUtils.*;

@Service @Slf4j
@RequiredArgsConstructor
public class InvitationService {

    private final S3UploadService s3UploadService;
    private final InvitationRepository invitationRepository;
    private final GalleryService galleryService;
    private final ProductInfoService productInfoService;
    private final ProductService productService;
    private final PriorityService priorityService;

    @Transactional
    public Long createInvitation(InvitationDto invitationdto, List<MultipartFile> gallery, MultipartFile mainImage) throws IOException {

        priorityService.savePriority(invitationdto.getPriorityDto());

        Invitation invitation = invitationdto.toInvitation();

        invitation.registerUserProductInfo(getCurrentUser(),
                productInfoService.findById(invitationdto.getProductInfoId()).orElseGet(ProductInfo::new));

        // TODO : invitation 에 priority 넣어줘야하나?

        if (gallery != null)
            saveGallery(gallery, invitation);

        if (mainImage != null)
            invitation.registerMainImage(s3UploadService.saveFile(mainImage));


        return saveInvitation(invitation);

    }

    private Long saveInvitation(Invitation invitation) {
        return invitationRepository.save(invitation).getId();
    }

    @Transactional
    public void deleteInvitation(Long invitationId) throws Exception {

        Invitation invitation = invitationRepository.findById(invitationId).orElseThrow(InvitationNotFoundException::new);

        if (!isYours(getCurrentUser().getId(), invitation.getId())) {
            throw new InvitationAccessDeniedException();
        }


        List<Gallery> galleryList = invitation.getGallery();

        for (Gallery gallery : galleryList) {
            s3UploadService.delete(gallery.getStoreFileName());
        }

        s3UploadService.delete(invitation.getMainImageStoreFileName());

        invitationRepository.delete(invitation);

    }


    @Transactional
    public void updateInvitation(Long invitationId, InvitationDto invitationDto, List<MultipartFile> gallery, MultipartFile mainImage) throws IOException {

        Invitation invitation = invitationRepository.findById(invitationId)
                .orElseThrow(InvitationNotFoundException::new);

        if (!isYours(getCurrentUser().getId(), invitation.getId())) {
            throw new InvitationAccessDeniedException();
        }

        /* 청첩장의 기존 이미지 s3에서 지우고 DB에도 삭제 */
        List<Gallery> galleryList = invitation.getGallery();
        for (Gallery g : galleryList) {
            s3UploadService.delete(g.getStoreFileName());
            galleryService.delete(g);
        }

        /* 청첩장의 메인 이미지 s3에서 지우기 */
        s3UploadService.delete(invitation.getMainImageStoreFileName());

        invitation.update(invitationDto);

        // Gallery 생성
        saveGallery(gallery, invitation);

        // main Image 저장
        invitation.registerMainImage(s3UploadService.saveFile(mainImage));
    }

    @Transactional(readOnly = true)
    public ResponseInvitationDto readInvitation(Long invitationId) {

        Invitation invitation = invitationRepository.findById(invitationId).orElseThrow(InvitationNotFoundException::new);
        ResponseInvitationDto responseInvitationDto = new ResponseInvitationDto(invitation);

        return responseInvitationDto;
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

    public boolean isYours (Long userId, Long productId) {
        Product product = productService.findOne(productId);

        if (product != null && product.getUser().getId() == userId)
            return true;
        else
            return false;
    }

    public Invitation findInvitation(Long invitationId) {
        return invitationRepository.findById(invitationId).orElseGet(null);
    }
}
