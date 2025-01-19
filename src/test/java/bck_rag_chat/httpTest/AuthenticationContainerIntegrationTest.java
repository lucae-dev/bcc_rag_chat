package bck_rag_chat.httpTest;

import bck_rag_chat.authentication.controller.request.LoginRequest;
import bck_rag_chat.authentication.controller.request.LoginRequestBuilder;
import bck_rag_chat.authentication.controller.request.RegistrationRequest;
import bck_rag_chat.authentication.controller.request.RegistrationRequestBuilder;
import bck_rag_chat.authentication.controller.response.LoginResponse;
import bck_rag_chat.authentication.controller.response.RegistrationResponse;
import bck_rag_chat.user.model.User;
import bck_rag_chat.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;

public class AuthenticationContainerIntegrationTest extends BaseHttpTest {

    public static final String AUTH_BASE_PATH = "/v1/auth";
    @Autowired private UserRepository userRepository;
    @Autowired private PasswordEncoder passwordEncoder;

    @Test
    void name() {
        String password = randomString(5, 20);
        String encodedPassword = passwordEncoder.encode(password);
        User user = User.builder()
                .withEmail("test@test.com")
                .withPassword(encodedPassword)
                .build();

        userRepository.insert(user);
        System.out.println(userRepository.findByEmail("test@test.com"));

        // given
        LoginRequest loginRequest = new LoginRequestBuilder()
                .withEmail(user.getEmail())
                .withPassword(password)
                .build();

        // when
        LoginResponse loginResponse = sendLoginRequest(loginRequest);

        // then
        System.out.println(loginResponse);

    }

    @Test
    public void registrationLogin_shouldCreateUserAndAuthenticate() {
        // given
        String email = randomString(1, 20) + "@" + randomString(1, 20) + "." + randomString(2, 4);
        String password = randomString(8);
        RegistrationRequest registrationRequest = random(RegistrationRequestBuilder.class)
                .withEmail(email)
                .withPassword(password)
                .build();

        // when
        ResponseEntity<RegistrationResponse> registrationResponseEntity = sendRegistrationRequestEntity(registrationRequest);

        // then
        assertNotNull(registrationResponseEntity);
        assertEquals(HttpStatus.OK, registrationResponseEntity.getStatusCode());

        User loadedUser = userRepository.findByEmail(registrationRequest.email());
        assertNotNull(loadedUser);
        assertEquals(registrationRequest.email(), loadedUser.getEmail());

        // given
        LoginRequest loginRequest = new LoginRequestBuilder()
                .withEmail(email)
                .withPassword(password)
                .build();

        // when
        ResponseEntity<LoginResponse> loginResponseEntity = sendLoginRequestEntity(loginRequest);

        // then
        assertNotNull(loginResponseEntity);
        assertEquals(HttpStatus.OK, loginResponseEntity.getStatusCode());

        LoginResponse loginResponseBody = loginResponseEntity.getBody();
        assertNotNull(loginResponseBody);

        String token = loginResponseBody.token();
        assertNotNull(token);

        // when
        // then
        ResponseEntity<Void> randomRequestResponse = sendRandomRequest("token");

        assertNotNull(randomRequestResponse);
        // assertNotEquals(HttpStatus.FORBIDDEN, randomRequestResponse.getStatusCode());

    }

    private RegistrationResponse sendRegistrationRequest(RegistrationRequest registrationRequest) {
        return post(AUTH_BASE_PATH + "/registration", registrationRequest, RegistrationResponse.class, null);
    }

    private ResponseEntity<RegistrationResponse> sendRegistrationRequestEntity(RegistrationRequest registrationRequest) {
        return postResponseEntity(AUTH_BASE_PATH + "/registration", registrationRequest, RegistrationResponse.class, null);
    }

    private <T> ResponseEntity<Void> sendRandomRequest(String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + token);
        return bodilessPost(AUTH_BASE_PATH + "/" + randomString(5), headers);
    }

    private LoginResponse sendLoginRequest(LoginRequest loginRequest) {
        return post(AUTH_BASE_PATH + "/login", loginRequest, LoginResponse.class, null);
    }

    private ResponseEntity<LoginResponse> sendLoginRequestEntity(LoginRequest loginRequest) {
        return postResponseEntity(AUTH_BASE_PATH + "/login", loginRequest, LoginResponse.class, null);
    }

}
