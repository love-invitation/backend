package jun.invitation.domain.invitation.api;

import jakarta.servlet.http.HttpServletRequest;
import jun.invitation.domain.contact.dto.ContactReqDto;
import jun.invitation.domain.gallery.Gallery;
import jun.invitation.domain.guestbook.domain.Guestbook;
import jun.invitation.domain.invitation.dao.InvitationRepository;
import jun.invitation.domain.invitation.domain.Invitation;
import jun.invitation.domain.invitation.domain.embedded.Wedding;
import jun.invitation.domain.invitation.dto.ContactInfoDto;
import jun.invitation.domain.invitation.dto.InvitationDto;
import jun.invitation.domain.transport.dto.TransportDto;
import jun.invitation.domain.invitation.service.InvitationService;
import jun.invitation.domain.orders.dao.OrderRepository;
import jun.invitation.domain.orders.domain.Orders;
import jun.invitation.domain.transport.domain.Transport;
import jun.invitation.global.dto.ResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequiredArgsConstructor
@Slf4j
public class InvitationController {

    private final InvitationService invitationService;
    private final InvitationRepository invitationRepository;
    private final OrderRepository orderRepository;

    @GetMapping("/product/invitation/read/{invitationTsid}")
    public ResponseEntity<ResponseDto> getInvitation(@PathVariable(name = "invitationTsid") Long invitationTsid) {

        LinkedHashMap<String, Object> result = invitationService.readInvitation(invitationTsid);


        ResponseDto<Object> responseDto = ResponseDto.builder()
                .status(OK.value())
                .message("read success")
                .result(result)
                .build();

        return ResponseEntity
                .status(OK)
                .body(responseDto);

    }

    @PostMapping("/product/invitation/create")
    public ResponseEntity<ResponseDto> createInvitation(
            @RequestPart(name = "invitationDto") InvitationDto invitationDto,
            @RequestPart(name = "gallery", required = false) List<MultipartFile> gallery,
            @RequestPart(name = "mainImage", required = false) MultipartFile mainImage,
            @RequestPart(name = "shareThumbnail", required = false) MultipartFile shareThumbnail) throws IOException {

        ContactReqDto contacts = invitationDto.getContacts();

        if (contacts != null) {
            List<ContactInfoDto> brideContactInfo = contacts.getBrideContactInfo();
            List<ContactInfoDto> groomContactInfo = contacts.getGroomContactInfo();

            if (brideContactInfo != null) {
                for (ContactInfoDto b : brideContactInfo) {
                    log.info(b.getName());
                    log.info(b.getRelation());
                    log.info(b.getPhoneNumber());
                }
            } else {
                log.info("empty");
            }

            if (groomContactInfo != null) {
                for (ContactInfoDto g : groomContactInfo) {
                    log.info(g.getName());
                    log.info(g.getRelation());
                    log.info(g.getPhoneNumber());
                }
            } else {
                log.info("empty");
            }
        }

        invitationService.createInvitation(invitationDto, gallery, mainImage, shareThumbnail);
        ResponseDto responseDto = ResponseDto.builder()
                .status(CREATED.value())
                .message("create success")
                .build();

        return ResponseEntity
                .status(CREATED)
                .body(responseDto);
    }

    @DeleteMapping("/product/invitation/delete/{invitationId}")
    public ResponseEntity<ResponseDto> deleteInvitation(@PathVariable(name = "invitationId") Long invitationId) throws Exception {

        invitationService.deleteInvitation(invitationId);

        ResponseDto responseDto = ResponseDto.builder()
                .status(OK.value())
                .message("Invitation[ID : "+ invitationId+"] successfully deleted.")
                .build();

        return ResponseEntity
                .status(OK)
                .body(responseDto);
    }

    @PutMapping("/product/invitation/update/{invitationId}")
    public ResponseEntity<ResponseDto> updateInvitation(
            @PathVariable(name = "invitationId") Long invitationId ,
            @RequestPart(name = "invitationDto") InvitationDto invitationDto,
            @RequestPart(name = "gallery", required = false) List<MultipartFile> gallery,
            @RequestPart(name = "mainImage", required = false) MultipartFile mainImage,
            @RequestPart(name = "shareThumbnail", required = false) MultipartFile shareThumbnail
    ) throws IOException {

        invitationService.updateInvitation(invitationId, invitationDto, gallery, mainImage, shareThumbnail);

        ResponseDto responseDto = ResponseDto.builder()
                .status(OK.value())
                .message("Invitation[ID : "+ invitationId+"] successfully updated.")
                .build();

        return ResponseEntity
                .status(OK)
                .body(responseDto);

    }

    @GetMapping("/test/insert")
    public ResponseEntity<ResponseDto> insert() {
        Invitation invitation = new Invitation(null, null, null, null, new Wedding(null, null, null, LocalDateTime.now().minusDays(1), null),true,null,null);
        invitation.register(null, null);

        for (int i = 0; i < 12 ; i++) {
            Gallery gallery = new Gallery();
            gallery.setInvitation(invitation);
        }

        for (int i = 0; i < 10; i++) {
            Guestbook guestbook = new Guestbook("hong" + i, "1234", "잘살아 " + i);
            guestbook.registerInvitation(invitation);
        }

        for (int i = 0; i < 3; i++) {
            new Transport(new TransportDto("hh" + i, "gg" + i))
                    .register(invitation);
        }


        invitationRepository.save(invitation);

        orderRepository.save(
                new Orders(null, invitation)
        );

        return ResponseEntity
                .status(OK)
                .build();
    }

}
