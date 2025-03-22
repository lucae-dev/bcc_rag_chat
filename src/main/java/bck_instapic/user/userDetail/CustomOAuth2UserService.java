package bck_instapic.user.userDetail;

import bck_instapic.user.model.User;
import bck_instapic.user.model.UserBuilder;
import bck_instapic.user.repository.UserRepository;
import bck_instapic.user.userDetail.userInfo.GoogleOAuth2UserInfo;
import bck_instapic.user.userDetail.userInfo.OAuth2UserInfo;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Map;

@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final UserRepository userRepository;
    private final OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate;

    public CustomOAuth2UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.delegate = new DefaultOAuth2UserService();;
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest)
            throws OAuth2AuthenticationException {
        OAuth2User oauth2User = delegate.loadUser(userRequest);

        // Get client registration ID (e.g., "google")
        String registrationId = userRequest.getClientRegistration().getRegistrationId();

        // Get the attribute name that will be used to get user info (e.g., "sub" for Google)
        String userNameAttributeName = userRequest.getClientRegistration()
                .getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();

        // Extract user attributes
        Map<String, Object> attributes = oauth2User.getAttributes();

        // Map attributes to a user object
        OAuth2UserInfo userInfo;

        if ("google".equals(registrationId)) {
            userInfo = new GoogleOAuth2UserInfo(attributes);
        } else {
            throw new OAuth2AuthenticationException("Login with " + registrationId + " is not supported.");
        }

        String email = userInfo.getEmail();
        if (email == null) {
            throw new OAuth2AuthenticationException("Email not found from OAuth2 provider");
        }

        // Fetch or create user
        User user = processOAuth2User(userInfo);

        // Return an OAuth2User with authorities
        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")),
                attributes,
                userNameAttributeName);
    }

    private User processOAuth2User(OAuth2UserInfo userInfo) {
        String email = userInfo.getEmail();
        String providerId = userInfo.getId();

        User user = userRepository.findByEmail(email);

        if (user == null) {
            // Register new user
            user = new UserBuilder()
                    .withEmail(email)
                    .withGoogleId(providerId)
                    .build();
            userRepository.insert(user);
        } else if (user.getGoogleId() == null) {
            // Link existing user with Google ID
            user.setGoogleId(providerId);
            userRepository.update(user);
        }

        return user;
    }
}
