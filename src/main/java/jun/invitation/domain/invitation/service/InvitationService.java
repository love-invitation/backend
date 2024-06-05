package jun.invitation.domain.invitation.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jun.invitation.aws.s3.service.S3UploadService;
import jun.invitation.domain.guestbook.dto.GuestbookListDto;
import jun.invitation.domain.guestbook.dto.GuestbookResponseDto;
import jun.invitation.domain.guestbook.service.GuestbookService;
import jun.invitation.domain.invitation.dao.InvitationRepository;
import jun.invitation.domain.gallery.Gallery;
import jun.invitation.domain.invitation.domain.*;
import jun.invitation.domain.invitation.dto.*;
import jun.invitation.domain.invitation.exception.InvitationAccessDeniedException;
import jun.invitation.domain.invitation.exception.InvitationNotFoundException;
import jun.invitation.domain.orders.domain.Orders;
import jun.invitation.domain.orders.service.OrderService;
import jun.invitation.domain.priority.domain.Priority;
import jun.invitation.domain.priority.service.PriorityService;
import jun.invitation.domain.product.domain.Product;
import jun.invitation.domain.gallery.Service.GalleryService;
import jun.invitation.domain.productInfo.domain.ProductInfo;
import jun.invitation.domain.productInfo.service.ProductInfoService;
import jun.invitation.domain.product.service.ProductService;
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
    private final OrderService orderService;

    @PersistenceContext
    private EntityManager em;

    @Scheduled(cron = "0 0 0 * * ?")
    @Transactional
    public void removeAfterWedding() {
        LocalDateTime now = LocalDateTime.now();
        invitationRepository.deleteByWedding_DateBefore(now);
    }

    @Transactional
    public Long createInvitation(InvitationDto invitationdto, List<MultipartFile> gallery, MultipartFile mainImage) throws IOException  {

        /* 우선 순위 저장*/
        Priority priority = priorityService.savePriority(invitationdto.getPriorityDto());

        Invitation invitation = invitationdto.toInvitation();

        ProductInfo productInfo = productInfoService.findById(invitationdto.getProductInfoId());

        invitation.register(
                getCurrentUser(),
                productInfo,
                priority
                );

        /* 갤러리 저장 */
        if (gallery != null) {
            galleryService.saveGallery(gallery, invitation);
        }
        List<TransportDto> transportDtos = invitationdto.getTransport();

        /* 교통수단 저장 */
        if (transportDtos != null) {
            transportService.save(transportDtos, invitation);
        }

        /* 메인 이미지 저장 */
        if (mainImage != null) {
            invitation.registerMainImage(s3UploadService.saveFile(mainImage));
        }

        Long invitationTsid = saveInvitation(invitation);
        saveOrders(invitation);

        return invitationTsid;

    }

    private void saveOrders(Invitation invitation) {
        orderService.requestOrder(invitation);
    }


    private Long saveInvitation(Invitation invitation) {
        return invitationRepository.save(invitation).getTsid();
    }

    @Transactional
    public void deleteInvitation(Long invitationId) {

        Invitation invitation = invitationRepository.findById(invitationId).orElseThrow(InvitationNotFoundException::new);

        if (!isYours(getCurrentUser().getId(), invitation.getId())) {
            throw new InvitationAccessDeniedException();
        }

        if (invitation.getMainImageStoreFileName() != null) {
            s3UploadService.delete(invitation.getMainImageStoreFileName());
        }

        galleryService.deleteByGalleries(invitationId);
        guestbookService.deleteByGuestbooks(invitationId);
        transportService.delete(invitationId);
        orderService.delete(invitationId);
        productService.deleteByInvitation(invitation);
        priorityService.delete(invitation.getPriority());
    }


    @Transactional
    public void updateInvitation(Long invitationId, InvitationDto invitationDto, List<MultipartFile> gallery, MultipartFile mainImage) throws IOException {

        Invitation invitation = invitationRepository.findById(invitationId)
                .orElseThrow(InvitationNotFoundException::new);

        if (!isYours(getCurrentUser().getId(), invitation.getId())) {
            throw new InvitationAccessDeniedException();
        }

        /* 청첩장의 기존 이미지 s3에서 지우고 DB에도 삭제 */
        List<Gallery> galleries = invitation.getGallery();
        galleryService.deleteByUpdate(galleries);

        /* 청첩장의 메인 이미지 s3에서 지우기 */
        s3UploadService.delete(invitation.getMainImageStoreFileName());

        invitation.update(invitationDto);

        // Gallery 생성
        galleryService.saveGallery(gallery, invitation);

        // main Image 저장
        invitation.registerMainImage(s3UploadService.saveFile(mainImage));
    }

    @Transactional(readOnly = true)
    public LinkedHashMap<String, Object> readInvitation(Long invitationTsid) {

        Invitation invitation = invitationRepository.findByTsid(invitationTsid).orElseThrow(InvitationNotFoundException::new);

        LinkedHashMap<String, Object> stringObjectLinkedHashMap = sortByPriority(invitation);

        return stringObjectLinkedHashMap;
    }


    private LinkedHashMap<String, Object> sortByPriority(Invitation invitation) {

        Priority priority = invitation.getPriority();
        Wedding wedding = invitation.getWedding();
        GroomInfo groomInfo = invitation.getGroomInfo();
        BrideInfo brideInfo = invitation.getBrideInfo();

        Orders orders = orderService.requestFindOrder(invitation.getId());

        List<String> sortedPriorityList = priority.getSortedPriorityList();

        LinkedHashMap<String, Object> result = new LinkedHashMap<>();

        result.put("tsid", invitation.getTsid());
        result.put("isPaid", orders.getIsPaid());
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
                    Pageable page = PageRequest.of(0,3);
                    Page<GuestbookResponseDto> guestbookResponseDtoPage = guestbookService.getResponseDtoList(invitation.getId(), page);
                    result.put("guestbook",
                            new GuestbookListDto(guestbookResponseDtoPage, priority.getGuestbook())
                    );
            }
        }


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

    public Invitation requestFindByTsidInvitation(Long invitationTsid) {
        return invitationRepository.findByTsid(invitationTsid).orElseThrow(InvitationNotFoundException::new);
    }
}
