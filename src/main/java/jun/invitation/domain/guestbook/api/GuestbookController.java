package jun.invitation.domain.guestbook.api;

import jun.invitation.domain.guestbook.dto.GuestbookDto;
import jun.invitation.domain.guestbook.service.GuestbookService;
import jun.invitation.domain.invitation.domain.Invitation;
import jun.invitation.domain.invitation.service.InvitationService;
import jun.invitation.global.dto.ResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequiredArgsConstructor
@Slf4j
public class GuestbookController {

    private final InvitationService invitationService;
    private final GuestbookService guestbookService;

    // Create
    @PostMapping("/product/invitation/{invitationId}/guestbook/create")
    public ResponseEntity<ResponseDto> createGuestbook(
            @PathVariable(name = "invitationId") Long invitationId,
            @RequestBody GuestbookDto guestbookDto
            ) {

        Invitation invitation = invitationService.findInvitation(invitationId);

        guestbookService.requestCreate(guestbookDto, invitation);

        ResponseDto<Object> responseDto = ResponseDto.builder()
                .status(CREATED.value())
                .message(guestbookDto.toString())
                .build();

        return ResponseEntity
                .status(CREATED)
                .body(responseDto);
    }

    // Read

    // Delete

}
