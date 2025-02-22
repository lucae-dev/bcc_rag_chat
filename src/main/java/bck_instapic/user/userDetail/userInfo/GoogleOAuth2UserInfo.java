package bck_instapic.user.userDetail.userInfo;

import java.util.Map;

public class GoogleOAuth2UserInfo implements OAuth2UserInfo {
    private Map<String, Object> attributes;

    public GoogleOAuth2UserInfo(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    @Override
    public String getId() {
        return (String) attributes.get("sub");
    }

    @Override
    public String getEmail() {
        return (String) attributes.get("email");
    }

}
