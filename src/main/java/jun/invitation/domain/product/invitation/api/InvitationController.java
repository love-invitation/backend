package jun.invitation.domain.product.invitation.api;

import jun.invitation.domain.jwt.JwtProperties;
import jun.invitation.domain.product.invitation.domain.Invitation;
import jun.invitation.domain.product.invitation.dto.InvitationDto;
import jun.invitation.domain.product.invitation.dto.ResponseInvitationDto;
import jun.invitation.domain.product.invitation.service.InvitationService;
import jun.invitation.domain.user.domain.User;
import jun.invitation.dto.ResponseDto;
import jun.invitation.global.SecurityUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

import static jakarta.servlet.http.HttpServletResponse.SC_FORBIDDEN;
import static jakarta.servlet.http.HttpServletResponse.SC_OK;
import static jun.invitation.global.SecurityUtils.*;

@RestController
@RequiredArgsConstructor @Slf4j
public class InvitationController {

    private final InvitationService invitationService;

    @GetMapping("/product/invitation/read/{invitationId}")
    public ResponseEntity<ResponseDto> readInvitation(@PathVariable(name = "invitationId") Long invitationId) {

        if (!invitationService.isYours(getCurrentUser().getId(), invitationId)) {
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
                        .message("success")
                        .result(responseInvitationDto)
                        .build()
        );

    }

    @PostMapping("/product/invitation/create")
    public void createInvitation(
            @RequestPart(name = "invitationDto") InvitationDto invitationDto,
            @RequestPart(name = "gallery") List<MultipartFile> gallery,
            @RequestPart(name = "mainImage") MultipartFile mainImage) throws IOException {


        Invitation invitation = invitationService.createInvitation(invitationDto, gallery, mainImage);
        invitationService.saveInvitation(invitation);
        log.info(invitation.toString());

    }

    @DeleteMapping("/product/invitation/delete/{invitationId}")
    public void deleteInvitation(@PathVariable(name = "invitationId") Long invitationId) throws IOException {

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
