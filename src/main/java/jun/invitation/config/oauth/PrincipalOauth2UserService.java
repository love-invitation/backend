package jun.invitation.config.oauth;

import jun.invitation.config.CustomBCryptPasswordEncoder;
import jun.invitation.config.auth.PrincipalDetails;
import jun.invitation.config.oauth.provider.NaverUserInfo;
import jun.invitation.config.oauth.provider.OAuth2UserInfo;
import jun.invitation.domain.Role;
import jun.invitation.domain.User;
import jun.invitation.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
public class PrincipalOauth2UserService extends DefaultOAuth2UserService {

    @Autowired
    private CustomBCryptPasswordEncoder customBCryptPasswordEncoder;

    /**
     * 구글로부터 받은 userRequest 데이터에 대한 후처리하는 함수
     */
    @Autowired
    private UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);

        return processOAuth2User(userRequest, oAuth2User);
    }

    private OAuth2User processOAuth2User(OAuth2UserRequest userRequest, OAuth2User oAuth2User) {
        // Attribute를 파싱해서 공통 객체로 묶는다. 관리가 편함.
        OAuth2UserInfo oAuth2UserInfo = null;

        if (userRequest.getClientRegistration().getRegistrationId().equals("naver")) {
            log.info(" == Naver Login Requst ==");
            log.info("response = {}", (Map) oAuth2User.getAttributes().get("response"));
            oAuth2UserInfo = new NaverUserInfo((Map) oAuth2User.getAttributes().get("response"));
        } else {
            System.out.println("네이버만 지원합니다!");
        }

        Optional<User> userOptional =
                userRepository.findByProviderAndProviderId(oAuth2UserInfo.getProvider(), oAuth2UserInfo.getProviderId());

        User user;
        if (userOptional.isPresent()) {
            user = userOptional.get();
            // user가 존재하면 update 해주기
            user.setEmail(oAuth2UserInfo.getEmail());
            userRepository.save(user);
        } else {
            // user의 패스워드가 null이기 때문에 OAuth 유저는 일반적인 로그인을 할 수 없음.
            user = User.builder()
                    .username(oAuth2UserInfo.getProvider() + "_" + oAuth2UserInfo.getProviderId())
                    .email(oAuth2UserInfo.getEmail())
                    .role("ROLE_USER")
                    .provider(oAuth2UserInfo.getProvider())
                    .providerId(oAuth2UserInfo.getProviderId())
                    .build();
            userRepository.save(user);
        }

        return new PrincipalDetails(user, oAuth2User.getAttributes());

    }
}
