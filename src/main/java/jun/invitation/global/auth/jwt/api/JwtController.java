package jun.invitation.global.auth.jwt.api;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jun.invitation.global.auth.jwt.service.JwtService;
import jun.invitation.global.dto.ResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class JwtController {

    private final JwtService jwtService;

    @PostMapping("api/v1/logout")
    public ResponseEntity<ResponseDto> logout(HttpServletRequest request, HttpServletResponse response) {

        Cookie[] cookies = request.getCookies();
        Cookie cookie = jwtService.logout(cookies);

        response.addCookie(cookie);

        ResponseDto<Object> responseDto = ResponseDto.builder()
                .status(200)
                .build();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(responseDto);
    }
}
