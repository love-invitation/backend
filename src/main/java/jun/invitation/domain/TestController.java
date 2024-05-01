package jun.invitation.domain;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Arrays;
import java.util.List;

@Controller @Slf4j
public class TestController {

    @GetMapping("/")
    public String test() {

        return "loginForm";
    }

    @GetMapping("/home")
    public String tes1t(HttpServletRequest request) {


        // 쿠키 정보 출력
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            Arrays.stream(cookies).forEach(cookie -> {
                log.info("Cookie Name: " + cookie.getName());
                log.info("Cookie Value: " + cookie.getValue());
            });
        }
        return "index";
    }
}
