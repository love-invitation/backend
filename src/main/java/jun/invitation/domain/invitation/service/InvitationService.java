package jun.invitation.domain.invitation.service;

import jun.invitation.aws.s3.service.S3UploadService;
import jun.invitation.domain.guestbook.dto.GuestbookDto;
import jun.invitation.domain.guestbook.dto.GuestbookListDto;
import jun.invitation.domain.guestbook.dto.GuestbookResponseDto;
import jun.invitation.domain.guestbook.service.GuestbookService;
import jun.invitation.domain.invitation.dao.InvitationRepository;
import jun.invitation.domain.gallery.Gallery;
import jun.invitation.domain.invitation.domain.*;
import jun.invitation.domain.invitation.dto.*;
import jun.invitation.domain.invitation.exception.InvitationAccessDeniedException;
import jun.invitation.domain.invitation.exception.InvitationNotFoundException;
import jun.invitation.domain.priority.domain.Priority;
import jun.invitation.domain.priority.service.PriorityService;
import jun.invitation.domain.product.domain.Product;
import jun.invitation.domain.gallery.Service.GalleryService;
import jun.invitation.domain.productInfo.domain.ProductInfo;
import jun.invitation.domain.productInfo.service.ProductInfoService;
import jun.invitation.domain.product.service.ProductService;
import jun.invitation.domain.transport.domain.Transport;
import jun.invitation.domain.transport.service.TransportService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

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
    private final TransportService transportService;
    private final GuestbookService guestbookService;

    @Transactional
    public Long createInvitation(InvitationDto invitationdto, List<MultipartFile> gallery, MultipartFile mainImage) throws IOException {

        Priority priority = priorityService.savePriority(invitationdto.getPriorityDto());

        Invitation invitation = invitationdto.toInvitation();

        invitation.registerUserProductInfo(
                getCurrentUser(),
                productInfoService.findById(invitationdto.getProductInfoId()).orElseGet(ProductInfo::new),
                priority
                );

        if (gallery != null) {
            saveGallery(gallery, invitation);
        }
        List<TransportDto> transportDtos = invitationdto.getTransport();
        if (transportDtos != null) {
            saveTransport(transportDtos, invitation);
        }
        if (mainImage != null) {
            invitation.registerMainImage(s3UploadService.saveFile(mainImage));
        }

        return saveInvitation(invitation);

    }

    private void saveTransport(List<TransportDto> transportDtos, Invitation invitation) {
        for (TransportDto transportDto : transportDtos) {
            Transport transport = new Transport(transportDto, invitation);
            transportService.addTransport(transport);
        }
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
    public LinkedHashMap<String, Object> readInvitation(Long invitationId) {

        Invitation invitation = invitationRepository.findById(invitationId).orElseThrow(InvitationNotFoundException::new);

        LinkedHashMap<String, Object> stringObjectLinkedHashMap = sortByPriority(invitation);

        return stringObjectLinkedHashMap;
    }


    private LinkedHashMap<String, Object> sortByPriority(Invitation invitation) {

        Priority priority = invitation.getPriority();
        Wedding wedding = invitation.getWedding();
        GroomInfo groomInfo = invitation.getGroomInfo();
        BrideInfo brideInfo = invitation.getBrideInfo();

        List<String> sortedPriorityList = priority.getSortedPriorityList();

        LinkedHashMap<String, Object> result = new LinkedHashMap<>();

        result.put("id", invitation.getId());
        result.put("thumbnail", new ThumbnailDto( invitation));

        for (int i = 0; i < sortedPriorityList.size(); i++) {

            switch (sortedPriorityList.get(i)) {
                case "article" :
                    result.put("article",
                            new ArticleDto(invitation.getTitle(), invitation.getContents(),
                                    groomInfo, brideInfo, priority.getArticle())
                    );
                    break;
                case "weddingDate":
                    result.put("weddingDate",
                            new WeddingDateDto(wedding, priority.getWeddingDate())
                    );
                    break;
                case "weddingPlace":
                    result.put("weddingPlace",
                            new WeddingPlaceDto(wedding, priority.getWeddingPlace())
                    );
                    break;
                case "transport":
                    result.put("transport",
                            new TransportInfoDto(invitation.getTransport(), priority.getTransport())
                    );
                    break;
                case "gallery":
                    result.put("gallery",
                            new GalleryInfoDto(invitation.getGallery(), priority.getGallery())
                    );
                    break;
                case "contact":
                    result.put("contact",
                            new ContactDto(groomInfo,brideInfo, priority.getContact())
                    );
                    break;
                case "account":
                    result.put("account",
                            new AccountDto(groomInfo,brideInfo,priority.getAccount())
                    );
                    break;
                case "guestbook":
                    result.put("guestbook",
                            new GuestbookListDto(guestbookService.getResponseDtoList(invitation), priority.getGuestbook())
                    );
            }
        }


        return result;
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
        return invitationRepository.findById(invitationId).orElseThrow(InvitationNotFoundException::new);
    }
}
