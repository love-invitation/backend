package jun.invitation.domain.guestbook.api;

import jun.invitation.domain.guestbook.dto.GuestbookDto;
import jun.invitation.domain.guestbook.dto.GuestbookResponseDto;
import jun.invitation.domain.guestbook.service.GuestbookService;
import jun.invitation.domain.invitation.service.InvitationService;
import jun.invitation.global.dto.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/products/invitations")
public class GuestbookController {

    private final GuestbookService guestbookService;

    @GetMapping("/{productTsid}/guestbooks")
    public ResponseEntity<ResponseDto<Object>> readGuestbook(@PathVariable(name = "productTsid") Long productTsid,
            @RequestParam(value = "page", defaultValue = "0") int page) {

        Pageable paging = PageRequest.of(page, 3);

        Page<GuestbookResponseDto> responseDtoList = guestbookService.getResponseDtoList(productTsid, paging);

        ResponseDto<Object> result = ResponseDto
                .builder()
                .status(OK.value())
                .result(responseDtoList)
                .build();

        return ResponseEntity
                .status(OK)
                .body(result);
    }

    @PostMapping("/{productTsid}/guestbooks")
    public ResponseEntity<ResponseDto> createGuestbook(
            @PathVariable(name = "productTsid") Long productTsid,
            @RequestBody GuestbookDto guestbookDto
            ) {

        guestbookService.create(guestbookDto, productTsid);

        ResponseDto<Object> responseDto = ResponseDto.builder()
                .status(CREATED.value())
                .build();

        return ResponseEntity
                .status(CREATED)
                .body(responseDto);
    }

    @DeleteMapping("/{productTsid}/guestbooks/{guestbookId}")
    public ResponseEntity<ResponseDto> deleteGuestbook(
            @PathVariable(name = "productTsid") Long productTsid,
            @PathVariable(name = "guestbookId") Long guestbookId,
            @RequestHeader(name = "Password", required = false) String password
    ) {

        guestbookService.deleteGuestbook(productTsid, guestbookId, password);

        ResponseDto<Object> responseDto = ResponseDto.builder()
                .status(OK.value())
                .message("[Guestbook Id: " + guestbookId + "] of [Invitation Tsid: " + productTsid + "] is deleted.")
                .build();

        return ResponseEntity
                .status(OK)
                .body(responseDto);
    }

}
