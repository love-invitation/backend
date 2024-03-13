package jun.invitation.auth.oauth.userInfo;

import java.util.Map;

public class KakaoUserInfo implements OAuth2UserInfo {
    private Map<String, Object> attributes;

    public KakaoUserInfo(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    @Override
    public String getProviderId() {
        return attributes.get("id").toString();
    }

    @Override
    public String getName() {
        return (String) attributes.get("nickname");
    }

    @Override
    public String getEmail() {
        return null;
    }

    @Override
    public String getProvider() {
        return "kakao";
    }
}
