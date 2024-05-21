package jun.invitation.domain.user.api;

import jun.invitation.auth.jwt.service.TokenService;
import jun.invitation.domain.user.domain.User;
import jun.invitation.domain.user.dto.UserDto;
import jun.invitation.global.dto.ResponseDto;
import jun.invitation.global.utils.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequiredArgsConstructor
public class UserController {

    private TokenService tokenService;

    @GetMapping("/user/info")
    public ResponseEntity<ResponseDto> receiveUserInfoReq() {

        User currentUser = SecurityUtils.getCurrentUser();

        UserDto userDto = new UserDto(currentUser.getEmail());

        ResponseDto<Object> result = ResponseDto.builder()
                .status(OK.value())
                .result(userDto)
                .build();

        return ResponseEntity
                .status(OK)
                .body(result);
    }

}
