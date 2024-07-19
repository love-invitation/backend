package jun.invitation.auth.jwt.api;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jun.invitation.auth.jwt.service.JwtService;
import jun.invitation.global.dto.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class JwtController {

    private final JwtService jwtService;

    @GetMapping("/auth/logout")
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
