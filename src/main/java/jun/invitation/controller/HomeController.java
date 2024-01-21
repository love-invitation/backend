package jun.invitation.controller;

import jun.invitation.config.auth.PrincipalDetails;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@Controller
@Slf4j
public class HomeController {

    @GetMapping("/login")
    public String home() {

      log.info("HOME");

        return "loginForm";
    }

    @GetMapping("/user")
    public @ResponseBody String user() {

        log.info("USER");

        return "USER";
    }

    @GetMapping("/main")
    public @ResponseBody String main(@AuthenticationPrincipal PrincipalDetails principalDetails) {

        log.info("principalDetails.getUser()={}", principalDetails.getUser());

        return "Main";
    }
}
