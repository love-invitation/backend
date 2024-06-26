package jun.invitation.domain.invitation.service;

import jun.invitation.aws.s3.service.S3UploadService;
import jun.invitation.domain.account.domain.Account;
import jun.invitation.domain.account.dto.AccountReqDto;
import jun.invitation.domain.account.dto.AccountResDto;
import jun.invitation.domain.account.service.AccountService;
import jun.invitation.domain.contact.domain.Contact;
import jun.invitation.domain.contact.dto.ContactReqDto;
import jun.invitation.domain.contact.dto.ContactResDto;
import jun.invitation.domain.contact.service.ContactService;
import jun.invitation.domain.gallery.Gallery;
import jun.invitation.domain.gallery.Service.GalleryService;
import jun.invitation.domain.gallery.dto.GalleryInfoDto;
import jun.invitation.domain.guestbook.dto.GuestbookListDto;
import jun.invitation.domain.guestbook.dto.GuestbookResponseDto;
import jun.invitation.domain.guestbook.service.GuestbookService;
import jun.invitation.domain.invitation.dao.InvitationRepository;
import jun.invitation.domain.invitation.domain.embedded.FamilyInfo;
import jun.invitation.domain.invitation.domain.Invitation;
import jun.invitation.domain.invitation.domain.embedded.Wedding;
import jun.invitation.domain.invitation.dto.*;
import jun.invitation.domain.invitation.exception.InvitationNotFoundException;
import jun.invitation.domain.orders.domain.Orders;
import jun.invitation.domain.orders.service.OrderService;
import jun.invitation.domain.priority.domain.Priority;
import jun.invitation.domain.priority.dto.PriorityDto;
import jun.invitation.domain.priority.service.PriorityService;
import jun.invitation.domain.product.domain.Product;
import jun.invitation.domain.product.service.ProductService;
import jun.invitation.domain.productInfo.domain.ProductInfo;
import jun.invitation.domain.productInfo.service.ProductInfoService;
import jun.invitation.domain.shareThumbnail.domain.ShareThumbnail;
import jun.invitation.domain.shareThumbnail.dto.ShareThumbnailDto;
import jun.invitation.domain.shareThumbnail.dto.ShareThumbnailResDto;
import jun.invitation.domain.shareThumbnail.service.ShareThumbnailService;
import jun.invitation.domain.transport.domain.Transport;
import jun.invitation.domain.transport.dto.TransportDto;
import jun.invitation.domain.transport.dto.TransportInfoDto;
import jun.invitation.domain.transport.service.TransportService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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
    private final ShareThumbnailService shareThumbnailService;
    private final OrderService orderService;
    private final ContactService contactService;
    private final AccountService accountService;

    @Scheduled(cron = "0 0 0 * * ?")
    @Transactional
    public void removeAfterWedding() {
        LocalDateTime now = LocalDateTime.now();
        invitationRepository.deleteByWedding_DateBefore(now);
    }

    @Transactional
    public Long createInvitation(InvitationDto invitationdto, List<MultipartFile> gallery, MultipartFile mainImage, MultipartFile shareThumbnailImage) throws IOException  {

        Invitation invitation = invitationdto.toInvitation();
        priorityService.savePriority(invitationdto.getPriority(), invitation);

        ProductInfo productInfo = productInfoService.findById(invitationdto.getProductInfoId());

        ShareThumbnail createdThumbnail = shareThumbnailService.create(shareThumbnailImage, invitationdto.getThumbnail());
        invitation.registerShareThumbnail(createdThumbnail);

        invitation.register(
//                getCurrentUser(),
                null,
                productInfo
                );

        /* 갤러리 저장 */
        if (gallery != null) {
            galleryService.save(gallery, invitation);
        }

        /* 교통수단 저장 */
        List<TransportDto> transportDtos = invitationdto.getTransport();

        if (transportDtos != null) {
            transportService.save(transportDtos, invitation);
        }

        /* 연락처 저장 */
        ContactReqDto contacts = invitationdto.getContacts();

        if (contacts != null) {
            contactService.save(contacts.getGroom(), invitation, "Bride");
            contactService.save(contacts.getBride(), invitation, "Groom");
        }

        /* 계좌번호 저장 */
        AccountReqDto accounts = invitationdto.getAccounts();

        if (accounts != null) {
            accountService.save(accounts.getGroom(), invitation, "Groom");
            accountService.save(accounts.getBride(), invitation, "Bride");
        }


        /* 메인 이미지 저장 */
        if (mainImage != null) {
            invitation.registerMainImage(s3UploadService.saveFile(mainImage));
        }

        Long invitationTsid = saveInvitation(invitation);
        saveOrder(invitation);

        return invitationTsid;

    }
    private void saveOrder(Invitation invitation) {
        orderService.requestOrder(invitation);
    }


    private Long saveInvitation(Invitation invitation) {
        return invitationRepository.save(invitation).getTsid();
    }

    @Transactional
    public void deleteInvitation(Long invitationId) {

        Invitation invitation = invitationRepository.findById(invitationId).orElseThrow(InvitationNotFoundException::new);

//        if (!isYours(getCurrentUser().getId(), invitation.getId())) {
//            throw new InvitationAccessDeniedException();
//        }

        if (invitation.getMainImageStoreFileName() != null) {
            s3UploadService.delete(invitation.getMainImageStoreFileName());
        }

        galleryService.delete(invitation.getGallery());
        guestbookService.delete(invitationId);
        transportService.delete(invitationId);
        contactService.delete(invitationId);
        accountService.delete(invitationId);
        orderService.delete(invitationId);
        priorityService.delete(invitationId);
        shareThumbnailService.deleteS3Image(invitation.getShareThumbnail());
        productService.deleteByInvitation(invitationId);
    }


    @Transactional
    public void updateInvitation(Long invitationId, InvitationDto invitationDto, List<MultipartFile> newGalleries, MultipartFile mainImage, MultipartFile shareThumbnail) throws IOException {

        Invitation invitation = invitationRepository.findById(invitationId)
                .orElseThrow(InvitationNotFoundException::new);

        // 유효성 check
//        if (!isYours(getCurrentUser().getId(), invitation.getId())) {
//            throw new InvitationAccessDeniedException();
//        }

        List<Gallery> currentGalleries = invitation.getGallery();
        galleryService.update(currentGalleries,invitation, newGalleries);

        List<Transport> currentTransports = invitation.getTransport();
        List<TransportDto> newTransportDtos = invitationDto.getTransport();
        transportService.update(currentTransports, invitation, newTransportDtos);

        List<PriorityDto> newPriority = invitationDto.getPriority();
        List<Priority> currentPriority = invitation.getPriority();

        priorityService.update(newPriority, currentPriority);

        /**
         * contact
         */
        ContactReqDto newContacts = invitationDto.getContacts();
        List<Contact> currentContacts = invitation.getContacts();
        contactService.update(newContacts, currentContacts, invitation);


        /**
         * account
         */
        AccountReqDto newAccounts = invitationDto.getAccounts();
        List<Account> currentAccounts = invitation.getAccounts();
        accountService.update(newAccounts, currentAccounts, invitation);

        /**
         * ShareThumbnail
         */
        ShareThumbnailDto newShareThumbnail = invitationDto.getThumbnail();
        ShareThumbnail currentShareThumbnail = invitation.getShareThumbnail();

        shareThumbnailService.update(newShareThumbnail, currentShareThumbnail, shareThumbnail);

        mainImageUpdate(mainImage, invitation);
        invitation.update(invitationDto);
    }

    /**
     *  기존 o, main Image o : 기존 삭제 , 메인 이미지 저장 o
     *  기존 o, main Image x : 기존 삭제 , 메인 이미지 저장 x
     *  기존 x, main Image O : 기존 삭제 x, 메인 이미지 저장 o
     *  기존 x, main Image x : 아무 행동 x
     */
    private void mainImageUpdate(MultipartFile mainImage, Invitation invitation) throws IOException {
        String mainImageStoreFileName = invitation.getMainImageStoreFileName();

        // 기존 o, main Image o : 기존 삭제 , 메인 이미지 저장 o
        if (mainImageStoreFileName != null && mainImage != null) {
            s3UploadService.delete(mainImageStoreFileName);
            invitation.registerMainImage(s3UploadService.saveFile(mainImage));
        } else if (mainImageStoreFileName != null && mainImage == null) {
            s3UploadService.delete(mainImageStoreFileName);
            invitation.registerMainImage(null);
        } else if (mainImageStoreFileName == null && mainImage != null){
            invitation.registerMainImage(s3UploadService.saveFile(mainImage));
        }
    }

    @Transactional(readOnly = true)
    public LinkedHashMap<String, Object> readInvitation(Long invitationTsid) {

        Invitation invitation = invitationRepository.findByTsid(invitationTsid).orElseThrow(InvitationNotFoundException::new);

        LinkedHashMap<String, Object> stringObjectLinkedHashMap = sortByPriority(invitation);

        return stringObjectLinkedHashMap;
    }


    private LinkedHashMap<String, Object> sortByPriority(Invitation invitation) {

        List<Priority> priorities = invitation.getPriority();
        Wedding wedding = invitation.getWedding();
        FamilyInfo groomInfo = invitation.getGroomInfo();
        FamilyInfo brideInfo = invitation.getBrideInfo();

        Orders orders = orderService.requestFindOrder(invitation.getId());

        LinkedHashMap<String, Object> result = new LinkedHashMap<>();

        result.put("tsid", invitation.getTsid());
        result.put("isPaid", orders.getIsPaid());
        result.put("cover", new CoverDto( invitation));

        for (Priority priority : priorities) {
            String name = priority.getName();

            Integer priorityValue = priority.getPriority();

            switch (name) {
                case "article":
                    result.put("article",
                            new ArticleDto(invitation.getTitle(), invitation.getContents(),
                                    groomInfo, brideInfo, priorityValue)
                    );
                    break;
                case "weddingDate":
                    result.put("booking",
                            new WeddingDateDto(wedding, priorityValue)
                    );
                    break;
                case "weddingPlace":
                    result.put("place",
                            new WeddingPlaceDto(wedding, priorityValue)
                    );
                    break;
                case "transport":
                    result.put("transport",
                            new TransportInfoDto(invitation.getTransport(), priorityValue)
                    );
                    break;
                case "gallery":
                    result.put("gallery",
                            new GalleryInfoDto(invitation.getGallery(), priorityValue)
                    );
                    break;
                case "contact":

                    Map<String, List> seperatedContactMap = contactService.getSeperatedMap(invitation.getContacts());

                    result.put("contact",
                            new ContactResDto(
                                    seperatedContactMap.get("groom"),
                                    seperatedContactMap.get("bride"),
                                    priorityValue
                            )
                    );
                    break;
                case "account":

                    Map<String, List> seperatedAccountMap = accountService.getSeperatedMap(invitation.getAccounts());

                    result.put("account",
                            new AccountResDto(
                                    seperatedAccountMap.get("groom"),
                                    seperatedAccountMap.get("bride"),
                                    priorityValue
                            )
                    );
                    break;
            }
        }

        ShareThumbnail shareThumbnail = invitation.getShareThumbnail();
        String shareThumbTitle = shareThumbnail.getTitle();
        String shareThumbContents = shareThumbnail.getContents();
        String shareThumbImageUrl = shareThumbnail.getImageUrl();
        result.put("thumbnail", new ShareThumbnailResDto(shareThumbTitle,shareThumbContents, shareThumbImageUrl));

        return result;
    }

    public boolean isYours (Long userId, Long productId) {
        Product product = productService.findOne(productId);

        if (product != null && product.getUser().getId() == userId)
            return true;
        else
            return false;
    }

    public Invitation requestFindInvitation(Long invitationId) {
        return invitationRepository.findById(invitationId).orElseThrow(InvitationNotFoundException::new);
    }

    @Transactional(readOnly = true)
    public Invitation requestFindByTsidInvitation(Long invitationTsid) {
        return invitationRepository.findByTsid(invitationTsid).orElseThrow(InvitationNotFoundException::new);
    }

    @Transactional(readOnly = true)
    public ShareThumbnailResDto readShareThumbnail(Long productId) {

        ShareThumbnail shareThumbnail = invitationRepository.findShareThumbnailByProductId(productId);

        return new ShareThumbnailResDto(
                shareThumbnail.getTitle(),
                shareThumbnail.getContents(),
                shareThumbnail.getImageUrl()
        );
    }
}
