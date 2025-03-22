package bck_instapic.httpTest;

import bck_instapic.Jwt.JwtTokenProvider;
import bck_instapic.authentication.controller.request.LoginRequest;
import bck_instapic.authentication.controller.response.LoginResponse;
import bck_instapic.s3.service.S3Service;
import bck_instapic.trainingbuckets.configuration.TrainingBucketProperties;
import bck_instapic.trainingbuckets.controller.response.PresignedUrlResponse;
import bck_instapic.trainingbuckets.model.TrainingBucket;
import bck_instapic.trainingbuckets.repository.TrainingBucketRepository;
import bck_instapic.user.model.User;
import bck_instapic.user.model.UserBuilder;
import bck_instapic.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class TrainingBucketControllerContainerIntegrationTest extends BaseHttpTest {

    @Autowired private TrainingBucketRepository trainingBucketRepository;
    @Autowired private UserRepository userRepository;
    @Autowired private JwtTokenProvider jwtTokenProvider;
    @Autowired private PasswordEncoder passwordEncoder;
    @Autowired private TrainingBucketProperties trainingBucketProperties;
    @Autowired private S3Service s3Service;

    // Typically you'd have a method to generate a JWT for a test user
    private String userToken;
    private UUID userId;

    @BeforeEach
    void setupTestData() {
        s3Service.createBucket(trainingBucketProperties.trainingBucketName());
    }

    @Test
    void createPresignedUrl_shouldCreateBucketRecordAndReturnUrl() throws IOException {
        // given
        // 1) Insert a test user into the DB
        String rawPassword = randomString(10);
        User testUser = random(UserBuilder.class)
                .withEmail("random@email.com")
                .withPassword(passwordEncoder.encode(rawPassword))
                .build();
        userRepository.insert(testUser);

        // 2) Generate a JWT for that user
        userId = testUser.getId();
        LoginResponse loginResult = post("v1/auth/login", new LoginRequest(testUser.getEmail(), rawPassword), LoginResponse.class, new HttpHeaders());
        userToken = loginResult.jwtToken();
        System.out.println(userToken);
        String endpoint = "/v1/training-buckets/presigned-url";

        // when
        PresignedUrlResponse response = get(
                endpoint,
                PresignedUrlResponse.class,
                authHeaders(userToken)        // sets Authorization: Bearer ...
        );

        // then
        assertNotNull(response);
        assertNotNull(response.bucketId());
        assertNotNull(response.presignedUrl());
        System.out.println("Presigned URL: " + response.presignedUrl());

        // verify record in DB
        TrainingBucket savedBucket = trainingBucketRepository.findById(response.bucketId());
        assertNotNull(savedBucket);
        assertEquals("CREATED", savedBucket.getStatus());
        assertEquals(userId, savedBucket.getUserId());

        // then
        String fileContent = "Hello from the test!";
        URL presignedUrl = new URL(response.presignedUrl());

        // 1) Upload using PUT
        HttpURLConnection putConnection = (HttpURLConnection) presignedUrl.openConnection();
        putConnection.setDoOutput(true);
        putConnection.setRequestMethod("PUT");
        putConnection.setRequestProperty("Content-Type", "text/plain");
        try (OutputStream os = putConnection.getOutputStream()) {
            os.write(fileContent.getBytes(StandardCharsets.UTF_8));
        }
        int putStatusCode = putConnection.getResponseCode();
        assertEquals(200, putStatusCode, "Presigned URL PUT should return 200 OK.");
        try (InputStream is = s3Service.downloadObject(trainingBucketProperties.trainingBucketName(), savedBucket.getS3Path())) {
            String downloadedText = new String(is.readAllBytes(), StandardCharsets.UTF_8);
            assertEquals(fileContent, downloadedText, "Downloaded text should match uploaded text.");
        }
    }

    private HttpHeaders authHeaders(String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + token);
        return headers;
    }
}
