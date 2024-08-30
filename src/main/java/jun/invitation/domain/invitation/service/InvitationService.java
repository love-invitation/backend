package jun.invitation.domain.invitation.service;

import jun.invitation.domain.account.service.AccountService;
import jun.invitation.domain.contact.service.ContactService;
import jun.invitation.domain.gallery.Gallery;
import jun.invitation.domain.gallery.Service.GalleryService;
import jun.invitation.domain.invitation.dao.InvitationRepository;
import jun.invitation.domain.invitation.domain.Invitation;
import jun.invitation.domain.reservation.domain.Reservation;
import jun.invitation.domain.invitation.dto.*;
import jun.invitation.domain.invitation.exception.ProductNotFoundException;
import jun.invitation.domain.orders.service.OrderService;
import jun.invitation.domain.priority.service.PriorityService;
import jun.invitation.domain.product.service.ProductService;
import jun.invitation.domain.productInfo.domain.ProductInfo;
import jun.invitation.domain.productInfo.service.ProductInfoService;
import jun.invitation.domain.reservation.service.ReservationService;
import jun.invitation.domain.shareThumbnail.domain.ShareThumbnail;
import jun.invitation.domain.shareThumbnail.dto.ShareThumbnailResDto;
import jun.invitation.domain.shareThumbnail.service.ShareThumbnailService;
import jun.invitation.domain.transport.service.TransportService;
import jun.invitation.global.service.port.IdentifierGenerator;
import jun.invitation.domain.image.domain.Image;
import jun.invitation.domain.image.service.ImageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

import static java.util.stream.Collectors.*;
import static jun.invitation.global.utils.SecurityUtils.getCurrentUser;

@Service
@Slf4j
@RequiredArgsConstructor
public class InvitationService {

    private final InvitationRepository invitationRepository;
    private final GalleryService galleryService;
    private final ProductInfoService productInfoService;
    private final ProductService productService;
    private final PriorityService priorityService;
    private final TransportService transportService;
    private final ShareThumbnailService shareThumbnailService;
    private final OrderService orderService;
    private final ContactService contactService;
    private final AccountService accountService;
    private final ImageService imageService;
    private final ReservationService reservationService;

    private final IdentifierGenerator identifierGenerator;


//    @Scheduled(cron = "0 0 0 * * ?")
//    @Transactional
//    public void removeAfterWedding() {
//        // todo : 다른 Entity들도 삭제해야함
//        LocalDateTime now = LocalDateTime.now();
//        invitationRepository.deleteByWedding_DateBefore(now);
//    }

    @Transactional
    public Long create(InvitationDto invitationdto, List<MultipartFile> gallery, MultipartFile mainImage, MultipartFile shareThumbnailImage) {

        Invitation invitation = invitationdto.toInvitation();

        Reservation createdReservation = reservationService.create(invitationdto.getBooking(), invitationdto.getPlace());
        ShareThumbnail createdThumbnail = shareThumbnailService.create(shareThumbnailImage, invitationdto.getThumbnail());
        ProductInfo productInfo = productInfoService.findById(invitationdto.getProductInfoId());

        saveMainImage(mainImage, invitation);

        invitation.register(
                getCurrentUser(),
                identifierGenerator.generate(),
                productInfo,
                createdThumbnail,
                createdReservation
        );

        priorityService.create(invitationdto.getPriority(), invitation);
        galleryService.create(gallery, invitation);
        transportService.create(invitationdto.getTransport(), invitation);
        contactService.create(invitationdto.getContacts(), invitation);
        accountService.create(invitationdto.getAccounts(), invitation);

        Long invitationTsid = invitationRepository.save(invitation).getTsid();
        orderService.create(invitation);

        return invitationTsid;

    }

    private void saveMainImage(MultipartFile mainImage, Invitation invitation) {
        Optional.ofNullable(mainImage)
                .ifPresent(m -> {
                    Image image = imageService.saveWithImageUpload(m);
                    invitation.registerMainImage(image);
                });
    }

    @Transactional
    @CacheEvict(value = "Products", key = "#tsid", cacheManager = "cacheManager")
    public void delete(Long tsid) {

        Invitation invitation = invitationRepository.findByTsid(tsid)
                .orElseThrow(ProductNotFoundException::new);

        Long invitationId = invitation.getId();

//        if (!isYours(getCurrentUser().getId(), invitation.getId())) {
//            throw new InvitationAccessDeniedException();
//        }

        List<Image> images = getImageToDelete(invitation);

        galleryService.delete(invitation.getGallery());
        transportService.delete(invitationId);
        contactService.delete(invitationId);
        accountService.delete(invitationId);
        orderService.delete(invitationId);
        priorityService.delete(invitationId);
        shareThumbnailService.deleteImage(invitation.getShareThumbnail());
        productService.delete(invitationId);
        imageService.delete(images);
    }

    private List<Image> getImageToDelete(Invitation invitation) {

        List<Image> images = invitation.getGallery()
                .stream().map(Gallery::getImage)
                .collect(toCollection(ArrayList::new));

        /* share thumbnail -> List<Image> images 에 추가 */
        Optional.ofNullable(invitation.getShareThumbnail())
                .map(ShareThumbnail::getImage)
                .ifPresent(images::add);

        /* main image -> List<Image> images 에 추가 */
        Optional.ofNullable(invitation.getMainImage())
                .ifPresent(images::add);

        return images;
    }

    @Transactional
    @CacheEvict(value = "Products", key = "#tsid", cacheManager = "cacheManager")
    public void update(Long tsid, InvitationDto invitationDto, List<MultipartFile> newGalleries, MultipartFile mainImage, MultipartFile shareThumbnail) {

        Invitation invitation = invitationRepository.findByTsid(tsid)
                .orElseThrow(ProductNotFoundException::new);

        // 유효성 check
//        if (!isYours(getCurrentUser().getId(), invitation.getId())) {
//            throw new InvitationAccessDeniedException();
//        }

        galleryService.update(invitation, newGalleries);
        transportService.update(invitation, invitationDto.getTransport());
        priorityService.update(invitationDto.getPriority(), invitation.getPriority());
        contactService.update(invitationDto.getContacts(), invitation);
        accountService.update(invitationDto.getAccounts(), invitation);
        shareThumbnailService.update(invitationDto.getThumbnail(), invitation.getShareThumbnail(), shareThumbnail);
        imageService.update(mainImage, invitation);

        reservationService.update(
                invitationDto.getPlace(),
                invitationDto.getBooking(),
                invitation.getReservation());

        invitation.update(
                invitationDto.getGuestbookCheck(),
                invitationDto.getTitle(),
                invitationDto.getContents(),
                invitationDto.getBride(),
                invitationDto.getGroom(),
                invitationDto.getCoverContents());

    }

    @Transactional(readOnly = true)
    @Cacheable(value = "Products", key = "#tsid", cacheManager = "cacheManager")
    public LinkedHashMap<String, Object> read(Long tsid) {

        Invitation invitation = invitationRepository.findByTsidIdWithALL(tsid)
                .orElseThrow(ProductNotFoundException::new);

        LinkedHashMap<String, Object> result = new LinkedHashMap<>();

        priorityService.sortByPriority(invitation, result);

        return result;
    }


    @Transactional(readOnly = true)
    public Invitation findById(Long invitationId) {
        return invitationRepository
                .findById(invitationId)
                .orElseThrow(ProductNotFoundException::new);
    }

    @Transactional(readOnly = true)
    public Invitation findByTsid(Long tsid) {
        return invitationRepository
                .findByTsid(tsid)
                .orElseThrow(ProductNotFoundException::new);
    }

    @Transactional(readOnly = true)
    public ShareThumbnailResDto readShareThumbnail(Long productId) {

        ShareThumbnail shareThumbnail = invitationRepository.findShareThumbnailByProductId(productId);

        return new ShareThumbnailResDto(
                shareThumbnail.getTitle(),
                shareThumbnail.getContents(),
                shareThumbnail.getImage().getUrl()
        );
    }
}
