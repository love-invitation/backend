package jun.invitation.domain.product.invitation.api;

import jun.invitation.domain.auth.PrincipalDetails;
import jun.invitation.domain.product.invitation.domain.Invitation;
import jun.invitation.domain.product.invitation.dto.InvitationDto;
import jun.invitation.domain.product.invitation.service.InvitationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor @Slf4j
public class InvitationController {

    private final InvitationService invitationService;


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




}
