package jun.invitation.domain.invitation.api;

import jakarta.servlet.http.HttpServletRequest;
import jun.invitation.domain.invitation.dto.InvitationDto;
import jun.invitation.domain.invitation.dto.ResponseInvitationDto;
import jun.invitation.domain.invitation.service.InvitationService;
import jun.invitation.dto.ResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor @Slf4j
public class InvitationController {

    private final InvitationService invitationService;

    @GetMapping("/product/invitation/read/{invitationId}")
    public ResponseEntity<ResponseDto> readInvitation(@PathVariable(name = "invitationId") Long invitationId) {

        ResponseInvitationDto responseInvitationDto = invitationService.readInvitation(invitationId);

        ResponseDto<Object> responseDto = ResponseDto.builder()
                .status(HttpStatus.OK.value())
                .message("read success")
                .result(responseInvitationDto)
                .build();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(responseDto);

    }

    @PostMapping("/product/invitation/create")
    public ResponseEntity<ResponseDto> createInvitation(
            @RequestPart(name = "invitationDto") InvitationDto invitationDto,
            @RequestPart(name = "gallery") List<MultipartFile> gallery,
            @RequestPart(name = "mainImage") MultipartFile mainImage, HttpServletRequest request) throws IOException {

        invitationService.createInvitation(invitationDto, gallery, mainImage);

        ResponseDto responseDto = ResponseDto.builder()
                .status(HttpStatus.CREATED.value())
                .message("create success")
                .build();

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(responseDto);
    }

    @DeleteMapping("/product/invitation/delete/{invitationId}")
    public ResponseEntity<ResponseDto> deleteInvitation(@PathVariable(name = "invitationId") Long invitationId) throws Exception {

        invitationService.deleteInvitation(invitationId);

        ResponseDto responseDto = ResponseDto.builder()
                .status(HttpStatus.OK.value())
                .message("Invitation[ID : "+ invitationId+"] successfully deleted.")
                .build();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(responseDto);
    }

    @PutMapping("/product/invitation/update/{invitationId}")
    public ResponseEntity<ResponseDto> updateInvitation(
            @PathVariable(name = "invitationId") Long invitationId ,
            @RequestPart(name = "invitationDto") InvitationDto invitationDto,
            @RequestPart(name = "gallery") List<MultipartFile> gallery,
            @RequestPart(name = "mainImage") MultipartFile mainImage
    ) throws IOException {

        invitationService.updateInvitation(invitationId, invitationDto, gallery, mainImage);

        ResponseDto responseDto = ResponseDto.builder()
                .status(HttpStatus.OK.value())
                .message("Invitation[ID : "+ invitationId+"] successfully updated.")
                .build();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(responseDto);

    }

}
