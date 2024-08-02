package jun.invitation.domain.guestbook.api;

import jun.invitation.domain.guestbook.dto.GuestbookDto;
import jun.invitation.domain.guestbook.dto.GuestbookResponseDto;
import jun.invitation.domain.guestbook.service.GuestbookService;
import jun.invitation.domain.invitation.domain.Invitation;
import jun.invitation.domain.invitation.service.InvitationService;
import jun.invitation.global.dto.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/products/invitations")
public class GuestbookController {

    private final InvitationService invitationService;
    private final GuestbookService guestbookService;

    @GetMapping("/{productId}/guestbooks")
    public ResponseEntity<ResponseDto> readGuestbook(@PathVariable(name = "productId") Long productId,
            @RequestParam(value = "page", defaultValue = "0") int page) {

        Pageable paging = PageRequest.of(page, 3);

        Page<GuestbookResponseDto> responseDtoList = guestbookService.getResponseDtoList(productId, paging);

        ResponseDto<Object> result = ResponseDto
                .builder()
                .status(OK.value())
                .result(responseDtoList)
                .build();

        return ResponseEntity
                .status(OK)
                .body(result);
    }

    // Create
    @PostMapping("/{productId}/guestbooks")
    public ResponseEntity<ResponseDto> createGuestbook(
            @PathVariable(name = "productId") Long productId,
            @RequestBody GuestbookDto guestbookDto
            ) {

        guestbookService.create(guestbookDto, productId);

        ResponseDto<Object> responseDto = ResponseDto.builder()
                .status(CREATED.value())
                .build();

        return ResponseEntity
                .status(CREATED)
                .body(responseDto);
    }

    // Delete
    @DeleteMapping("/{productId}/guestbooks/{guestbookId}")
    public ResponseEntity<ResponseDto> deleteGuestbook(
            @PathVariable(name = "productId") Long productId,
            @PathVariable(name = "guestbookId") Long guestbookId,
            @RequestBody(required = false) String password
    ) {

        guestbookService.deleteGuestbook(productId, guestbookId, password);

        ResponseDto<Object> responseDto = ResponseDto.builder()
                .status(OK.value())
                .message("[Guestbook Id: " + guestbookId + "] of [Invitation Id: " + productId + "] is deleted.")
                .build();

        return ResponseEntity
                .status(OK)
                .body(responseDto);
    }

}
