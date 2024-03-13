package jun.invitation.domain.invitation.api;

import jun.invitation.domain.invitation.domain.Invitation;
import jun.invitation.domain.invitation.dto.InvitationDto;
import jun.invitation.domain.invitation.dto.ResponseInvitationDto;
import jun.invitation.domain.invitation.service.InvitationService;
import jun.invitation.dto.ResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.InvalidPropertiesFormatException;
import java.util.List;

import static jakarta.servlet.http.HttpServletResponse.*;
import static jun.invitation.global.SecurityUtils.*;

@RestController
@RequiredArgsConstructor @Slf4j
public class InvitationController {

    private final InvitationService invitationService;

    @GetMapping("/product/invitation/read/{invitationId}")
    public ResponseEntity<ResponseDto> readInvitation(@PathVariable(name = "invitationId") Long invitationId) {
        Long userId = getCurrentUser().getId();
        if (!invitationService.isYours(userId, invitationId)) {
            return ResponseEntity.status(SC_FORBIDDEN).body(
                    ResponseDto.builder()
                            .status(SC_FORBIDDEN)
                            .message("Fail.. You're not owner.")
                            .build()
            );
        }

        ResponseInvitationDto responseInvitationDto = invitationService.readInvitation(invitationId);

        return ResponseEntity.ok(
                ResponseDto.builder()
                        .status(SC_OK)
                        .message("read success")
                        .result(responseInvitationDto)
                        .build()
        );

    }

    @PostMapping("/product/invitation/create")
    public ResponseEntity<ResponseDto> createInvitation(
            @RequestPart(name = "invitationDto") InvitationDto invitationDto,
            @RequestPart(name = "gallery") List<MultipartFile> gallery,
            @RequestPart(name = "mainImage") MultipartFile mainImage) throws IOException {


        Invitation invitation = invitationService.createInvitation(invitationDto, gallery, mainImage);
        Long result = invitationService.saveInvitation(invitation);
        log.info(invitation.toString());

        if (result == null) {
            return ResponseEntity.status(SC_BAD_REQUEST).body(
                    ResponseDto.builder()
                            .status(SC_BAD_REQUEST)
                            .message("Fail. check your parameter.")
                            .build()
            );
        }

        return ResponseEntity.ok(
                ResponseDto.builder()
                        .status(SC_OK)
                        .message("create success")
                        .build()
        );
    }

    @DeleteMapping("/product/invitation/delete/{invitationId}")
    public void deleteInvitation(@PathVariable(name = "invitationId") Long invitationId) throws Exception {

        invitationService.deleteInvitation(invitationId);

    }

    @PutMapping("/product/invitation/update/{invitationId}")
    public void updateInvitation(
            @PathVariable(name = "invitationId") Long invitationId ,
            @RequestPart(name = "invitationDto") InvitationDto invitationDto,
            @RequestPart(name = "gallery") List<MultipartFile> gallery,
            @RequestPart(name = "mainImage") MultipartFile mainImage
    ) throws IOException {

        invitationService.updateInvitation(invitationId, invitationDto, gallery, mainImage);

    }

}
