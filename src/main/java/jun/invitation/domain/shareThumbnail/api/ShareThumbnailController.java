package jun.invitation.domain.shareThumbnail.api;

import jun.invitation.domain.invitation.service.InvitationService;
import jun.invitation.domain.shareThumbnail.dto.ShareThumbnailResDto;
import jun.invitation.domain.shareThumbnail.service.ShareThumbnailService;
import jun.invitation.global.dto.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/v1/products/invitations")
public class ShareThumbnailController {

    private final InvitationService invitationService;

    @GetMapping("/{tsid}/shareThumbnail")
    public ResponseEntity<ResponseDto> read(@PathVariable(name = "tsid") Long productId) {
        ShareThumbnailResDto shareThumbnail = invitationService.readShareThumbnail(productId);

        ResponseDto<Object> result = ResponseDto.builder()
                .status(HttpStatus.OK.value())
                .result(shareThumbnail)
                .build();

        return ResponseEntity.status(HttpStatus.OK)
                .body(result);
    }

}
