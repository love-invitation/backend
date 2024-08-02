package jun.invitation.domain.invitation.service;

import jun.invitation.aws.s3.ImageUploadKey;
import jun.invitation.aws.s3.ImageUploader;
import jun.invitation.domain.account.domain.Account;
import jun.invitation.domain.account.dto.AccountInfoDto;
import jun.invitation.domain.account.dto.AccountReqDto;
import jun.invitation.domain.account.dto.AccountResDto;
import jun.invitation.domain.account.service.AccountService;
import jun.invitation.domain.contact.domain.Contact;
import jun.invitation.domain.contact.dto.ContactInfoDto;
import jun.invitation.domain.contact.dto.ContactReqDto;
import jun.invitation.domain.contact.dto.ContactResDto;
import jun.invitation.domain.contact.service.ContactService;
import jun.invitation.domain.gallery.Gallery;
import jun.invitation.domain.gallery.Service.GalleryService;
import jun.invitation.domain.gallery.dto.GalleryInfoDto;
import jun.invitation.domain.invitation.dao.InvitationRepository;
import jun.invitation.domain.invitation.domain.Invitation;
import jun.invitation.domain.invitation.domain.embedded.FamilyInfo;
import jun.invitation.domain.reservation.domain.Reservation;
import jun.invitation.domain.invitation.dto.*;
import jun.invitation.domain.invitation.exception.ProductNotFoundException;
import jun.invitation.domain.orders.domain.Orders;
import jun.invitation.domain.orders.service.OrderService;
import jun.invitation.domain.priority.PriorityName;
import jun.invitation.domain.priority.domain.Priority;
import jun.invitation.domain.priority.dto.PriorityDto;
import jun.invitation.domain.priority.service.PriorityService;
import jun.invitation.domain.product.domain.Product;
import jun.invitation.domain.product.service.ProductService;
import jun.invitation.domain.productInfo.domain.ProductInfo;
import jun.invitation.domain.productInfo.service.ProductInfoService;
import jun.invitation.domain.reservation.dto.DateDto;
import jun.invitation.domain.reservation.dto.PlaceDto;
import jun.invitation.domain.reservation.service.ReservationService;
import jun.invitation.domain.shareThumbnail.domain.ShareThumbnail;
import jun.invitation.domain.shareThumbnail.dto.ShareThumbnailDto;
import jun.invitation.domain.shareThumbnail.dto.ShareThumbnailResDto;
import jun.invitation.domain.shareThumbnail.service.ShareThumbnailService;
import jun.invitation.domain.transport.domain.Transport;
import jun.invitation.domain.transport.dto.TransportDto;
import jun.invitation.domain.transport.dto.TransportInfoDto;
import jun.invitation.domain.transport.service.TransportService;
import jun.invitation.global.service.port.IdentifierGenerator;
import jun.invitation.image.domain.Image;
import jun.invitation.image.service.ImageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import static jun.invitation.domain.invitation.domain.embedded.WeddingSide.BRIDE;
import static jun.invitation.domain.invitation.domain.embedded.WeddingSide.GROOM;
import static jun.invitation.domain.priority.PriorityName.*;
import static jun.invitation.global.utils.SecurityUtils.getCurrentUser;

@Service
@Slf4j
@RequiredArgsConstructor
public class InvitationService {

    private final ImageUploader imageUploader;
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
    public Long create(InvitationDto invitationdto, List<MultipartFile> gallery, MultipartFile mainImage, MultipartFile shareThumbnailImage) throws IOException  {

        Invitation invitation = invitationdto.toInvitation();

        priorityService.create(invitationdto.getPriority(), invitation);

        Reservation createdReservation = reservationService.create(invitationdto.getBooking(), invitationdto.getPlace());
        ShareThumbnail createdThumbnail = shareThumbnailService.create(shareThumbnailImage, invitationdto.getThumbnail());
        ProductInfo productInfo = productInfoService.findById(invitationdto.getProductInfoId());

        /* 갤러리 저장 */
        if (!ObjectUtils.isEmpty(gallery))
            galleryService.save(gallery, invitation);

        invitation.register(
                getCurrentUser(),
                identifierGenerator.generate(),
                productInfo,
                createdThumbnail,
                createdReservation
        );

        /* 교통수단 저장 */
        List<TransportDto> transportDtos = invitationdto.getTransport();

        if (!ObjectUtils.isEmpty(transportDtos))
            transportService.save(transportDtos, invitation);


        /* 연락처 저장 */
        Optional.ofNullable(invitationdto.getContacts())
                .ifPresent(c -> {
                    contactService.save(c.getGroom(), invitation, GROOM);
                    contactService.save(c.getBride(), invitation, BRIDE);
                });

        /* 계좌번호 저장 */
        Optional.ofNullable(invitationdto.getAccounts())
                .ifPresent(a -> {
                    accountService.register(a.getGroom(), invitation, GROOM);
                    accountService.register(a.getBride(), invitation, BRIDE);
                });

        /* 메인 이미지 저장 */
        Optional.ofNullable(mainImage)
                .ifPresent(m -> {
                    Map<ImageUploadKey, String> map = imageUploader.upload(mainImage);
                    Image image = imageService.create(map);
                    invitation.registerMainImage(image);
                });


        Long invitationTsid = invitationRepository.save(invitation).getTsid();
        orderService.create(invitation);

        return invitationTsid;

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

//        if (invitation.getMainImage() != null && invitation.getMainImage().getStoreFileName() != null) {
//            imageUploader.delete(invitation.getMainImage().getStoreFileName());
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
                .collect(Collectors.toCollection(ArrayList::new));

        /* share thumbnail -> List<Image> images 에 추가 */
        Optional.ofNullable(invitation.getShareThumbnail())
                .map(ShareThumbnail::getImage)
                .ifPresent(images::add);

        /* main image -> List<Image> images 에 추가 */
        Optional.ofNullable(invitation.getMainImage())
                .ifPresent(i -> {
                    images.add(i);
                    imageUploader.delete(i.getStoreFileName());
                });

        return images;
    }

    @Transactional
    @CacheEvict(value = "Products", key = "#tsid", cacheManager = "cacheManager")
    public void update(Long tsid, InvitationDto invitationDto, List<MultipartFile> newGalleries, MultipartFile mainImage, MultipartFile shareThumbnail) throws IOException {

        Invitation invitation = invitationRepository.findByTsid(tsid)
                .orElseThrow(ProductNotFoundException::new);

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

        /* contact */
        ContactReqDto newContacts = invitationDto.getContacts();
        List<Contact> currentContacts = invitation.getContacts();
        contactService.update(newContacts, currentContacts, invitation);

        /* account */
        AccountReqDto newAccounts = invitationDto.getAccounts();
        List<Account> currentAccounts = invitation.getAccounts();
        accountService.update(newAccounts, currentAccounts, invitation);

        /* ShareThumbnail */
        ShareThumbnailDto newShareThumbnail = invitationDto.getThumbnail();
        ShareThumbnail currentShareThumbnail = invitation.getShareThumbnail();

        shareThumbnailService.update(newShareThumbnail, currentShareThumbnail, shareThumbnail);

        mainImageUpdate(mainImage, invitation);

        /* reservation */
        reservationService.update(
                invitationDto.getPlace(),
                invitationDto.getBooking(),
                invitation.getReservation()
        );

        invitation.update(
                invitationDto.getGuestbookCheck(),
                invitationDto.getTitle(),
                invitationDto.getContents(),
                invitationDto.getBride(),
                invitationDto.getGroom(),
                invitationDto.getCoverContents()
        );

    }

    /**
     *  기존 o, main Image o : 기존 삭제 , 메인 이미지 저장 o
     *  기존 o, main Image x : 기존 삭제 , 메인 이미지 저장 x
     *  기존 x, main Image O : 기존 삭제 x, 메인 이미지 저장 o
     *  기존 x, main Image x : 아무 행동 x
     */
    private void mainImageUpdate(MultipartFile mainImage, Invitation invitation) throws RuntimeException {
        String mainImageStoreFileName = invitation.getMainImage() == null ? null : invitation.getMainImage().getStoreFileName();
        CompletableFuture<Map<ImageUploadKey, String>> future;
        // 기존 o, main Image o : 기존 삭제 , 메인 이미지 저장 o
        if (mainImageStoreFileName != null && mainImage != null) {
            imageUploader.delete(mainImageStoreFileName);
            future = imageUploader.uploadAsync(mainImage);
            registerImage(invitation, future.join());
        } else if (mainImageStoreFileName != null && mainImage == null) {
            imageUploader.delete(mainImageStoreFileName);
            invitation.registerMainImage(null);
        } else if (mainImageStoreFileName == null && mainImage != null){
            future = imageUploader.uploadAsync(mainImage);
            registerImage(invitation, future.join());
        }
    }

    private void registerImage(Invitation invitation, Map<ImageUploadKey, String> map) {
        Image image = imageService.create(map);
        invitation.registerMainImage(image);
    }

    @Transactional(readOnly = true)
    @Cacheable(value = "Products", key = "#tsid", cacheManager = "cacheManager")
    public LinkedHashMap<String, Object> read(Long tsid) {

        Invitation invitation = invitationRepository.findByTsidIdWithALL(tsid)
                .orElseThrow(ProductNotFoundException::new);

        return sortByPriority(invitation);
    }


    private LinkedHashMap<String, Object> sortByPriority(Invitation invitation) {

        List<Priority> priorities = invitation.getPriority();
        Reservation reservation = invitation.getReservation();
        FamilyInfo groomInfo = invitation.getGroomInfo();
        FamilyInfo brideInfo = invitation.getBrideInfo();

        Orders orders = orderService.findOrder(invitation.getId());

        LinkedHashMap<String, Object> result = new LinkedHashMap<>();

        result.put(TSID.getPriorityName(), invitation.getTsid());
        result.put(ISPAID.getPriorityName(), orders.getIsPaid());
        result.put(COVER.getPriorityName(), new CoverDto( invitation));

        for (Priority priority : priorities) {
            PriorityName name = fromPriorityName(priority.getName());
            Integer priorityValue = priority.getPriority();

            switch (name) {
                case ARTICLE:
                    result.put(ARTICLE.getPriorityName(),
                            new ArticleDto(invitation.getTitle(), invitation.getContents(),
                                    groomInfo, brideInfo, priorityValue)
                    );
                    break;
                case BOOKING:
                    result.put(BOOKING.getPriorityName(),
                            new DateDto(reservation, priorityValue)
                    );
                    break;
                case PLACE:
                    result.put(PLACE.getPriorityName(),
                            new PlaceDto(reservation, priorityValue)
                    );
                    break;
                case TRANSPORT:
                    result.put(TRANSPORT.getPriorityName(),
                            new TransportInfoDto(invitation.getTransport(), priorityValue)
                    );
                    break;
                case GALLERY:
                    result.put(GALLERY.getPriorityName(),
                            new GalleryInfoDto(invitation.getGallery(), priorityValue)
                    );
                    break;
                case CONTACT:

                    Map<String, List<ContactInfoDto>> classifiedContact = contactService.classifyByWeddingSide(invitation.getContacts());

                    result.put(CONTACT.getPriorityName(),
                            new ContactResDto(
                                    classifiedContact.get(GROOM.getSide()),
                                    classifiedContact.get(BRIDE.getSide()),
                                    priorityValue
                            )
                    );
                    break;
                case ACCOUNT:

                    Map<String, List<AccountInfoDto>> classifiedMap = accountService.classifyBySide(invitation.getAccounts());

                    result.put(ACCOUNT.getPriorityName(), new AccountResDto(classifiedMap, priorityValue));
                    break;
            }
        }

        // null을 넣어서 줘야하는지 아니면 지금처럼 result에 아예 값을 넣지 않을지, 프론트 개발자한테 물어봐야함
        Optional.ofNullable(invitation.getShareThumbnail())
                .ifPresentOrElse(s -> result.put(THUMBNAIL.getPriorityName(), new ShareThumbnailResDto(s)),
                        ()->result.put(THUMBNAIL.getPriorityName(), null));

        return result;
    }

    public boolean isYours (Long userId, Long productId) {
        Product product = productService.findOne(productId);

        return product.getUser().getId().equals(userId);
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
