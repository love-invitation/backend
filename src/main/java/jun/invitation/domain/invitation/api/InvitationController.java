package jun.invitation.domain.invitation.api;

import jun.invitation.domain.invitation.dto.InvitationDto;
import jun.invitation.domain.invitation.service.InvitationService;
import jun.invitation.global.dto.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/products/invitations")
public class InvitationController {

    private final InvitationService invitationService;

    @PostMapping
    public ResponseEntity<ResponseDto> createInvitation(
            @RequestPart(name = "invitationDto") InvitationDto invitationDto,
            @RequestPart(name = "gallery", required = false) List<MultipartFile> gallery,
            @RequestPart(name = "mainImage", required = false) MultipartFile mainImage,
            @RequestPart(name = "shareThumbnail", required = false) MultipartFile shareThumbnail) throws IOException {

        Long invitationTsid = invitationService.create(invitationDto, gallery, mainImage, shareThumbnail);
        ResponseDto responseDto = ResponseDto.builder()
                .status(CREATED.value())
                .message("create success.")
                .result(invitationTsid)
                .build();

        return ResponseEntity
                .status(CREATED)
                .body(responseDto);
    }

    @GetMapping("/{invitationTsid}")
    public ResponseEntity<ResponseDto> getInvitation(@PathVariable(name = "invitationTsid") Long invitationTsid) {

        LinkedHashMap<String, Object> result = invitationService.read(invitationTsid);


        ResponseDto<Object> responseDto = ResponseDto.builder()
                .status(OK.value())
                .message("read success")
                .result(result)
                .build();

        return ResponseEntity
                .status(OK)
                .body(responseDto);

    }

    @PutMapping("/{invitationTsid}")
    public ResponseEntity<ResponseDto> updateInvitation(
            @PathVariable(name = "invitationTsid") Long invitationTsid ,
            @RequestPart(name = "invitationDto") InvitationDto invitationDto,
            @RequestPart(name = "gallery", required = false) List<MultipartFile> gallery,
            @RequestPart(name = "mainImage", required = false) MultipartFile mainImage,
            @RequestPart(name = "shareThumbnail", required = false) MultipartFile shareThumbnail
    ) throws IOException {

        invitationService.update(invitationTsid, invitationDto, gallery, mainImage, shareThumbnail);

        ResponseDto responseDto = ResponseDto.builder()
                .status(OK.value())
                .message("Invitation[ID : "+ invitationTsid+"] successfully updated.")
                .build();

        return ResponseEntity
                .status(OK)
                .body(responseDto);

    }

    @DeleteMapping("/{invitationId}")
    public ResponseEntity<ResponseDto> deleteInvitation(@PathVariable(name = "invitationId") Long invitationId) throws Exception {

        invitationService.delete(invitationId);

        ResponseDto responseDto = ResponseDto.builder()
                .status(OK.value())
                .message("Invitation[ID : "+ invitationId+" ] successfully deleted.")
                .build();

        return ResponseEntity
                .status(OK)
                .body(responseDto);
    }
}