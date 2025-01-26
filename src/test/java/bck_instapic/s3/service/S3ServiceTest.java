package bck_instapic.s3.service;

import bck_instapic.BaseContainerIntegrationTest;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.*;
import org.springframework.util.StreamUtils;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class S3ServiceTest extends BaseContainerIntegrationTest {

    @Autowired
    private S3Service s3Service;

    private final RestTemplate restTemplate = new RestTemplate();

    @Test
    void createBucket() {
        // given
        String bucketName = randomString(3, 63);

        // when
        s3Service.createBucket(bucketName);

        // then
        List<String> bucketsList = s3Service.listBuckets();
        assertTrue(bucketsList.contains(bucketName));

        // when
        s3Service.deleteBucket(bucketName);

        // then
        List<String> bucketsListAfterDelete = s3Service.listBuckets();
        assertFalse(bucketsListAfterDelete.contains(bucketName));
    }

    @Test
    @Disabled // presigned url  probably not supported by minio (or needs adjustment in the test)
    void testUploadAndDownloadUsingPresignedUrls() throws IOException {
        // Given
        String bucketName = "your-test-bucket";
        s3Service.createBucket(bucketName);
        String key = "test-folder/" + UUID.randomUUID() + "/testfile.txt"; // Unique key to avoid collisions
        long expirationInSeconds = 3600;

        // Generate Presigned URL for Upload (PUT)
        String uploadPresignedUrl = generatePresignedUploadUrl(bucketName, key, expirationInSeconds);
        System.out.println(uploadPresignedUrl);
        assertNotNull(uploadPresignedUrl, "Upload presigned URL should not be null");

        // Load file from resources
        ClassPathResource resource = new ClassPathResource("imageTest.jpg");
        byte[] fileContent = StreamUtils.copyToByteArray(resource.getInputStream());

        // Prepare HTTP PUT request for uploading the file
        HttpHeaders uploadHeaders = new HttpHeaders();
        uploadHeaders.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        HttpEntity<byte[]> uploadEntity = new HttpEntity<>(fileContent, uploadHeaders);

        // When: Upload the file using the presigned URL
        ResponseEntity<Void> uploadResponse = restTemplate.exchange(
                uploadPresignedUrl,
                HttpMethod.PUT,
                uploadEntity,
                Void.class
        );

        // Then: Verify upload was successful (HTTP 200 or 204)
        assertTrue(
                uploadResponse.getStatusCode() == HttpStatus.OK ||
                        uploadResponse.getStatusCode() == HttpStatus.NO_CONTENT,
                "File upload failed with status: " + uploadResponse.getStatusCode()
        );

        // Generate Presigned URL for Download (GET)
        String downloadPresignedUrl = generatePresignedUploadUrl(bucketName, key, expirationInSeconds);
        assertNotNull(downloadPresignedUrl, "Download presigned URL should not be null");

        // When: Download the file using the presigned URL
        ResponseEntity<byte[]> downloadResponse = restTemplate.exchange(
                downloadPresignedUrl,
                HttpMethod.GET,
                null,
                byte[].class
        );

        // Then: Verify download was successful and content matches
        assertEquals(HttpStatus.OK, downloadResponse.getStatusCode(), "File download failed");

        byte[] downloadedContent = downloadResponse.getBody();
        assertNotNull(downloadedContent, "Downloaded content should not be null");
        assertArrayEquals(fileContent, downloadedContent, "Downloaded file content does not match the uploaded content");
    }

    private String generatePresignedUploadUrl(String bucketName, String key, long expirationInSeconds) {
        try {
            // Assuming S3Service has a method to generate upload presigned URLs
            return s3Service.generatePresignedUrl(bucketName, key, expirationInSeconds).toString();
        } catch (Exception e) {
            fail("Failed to generate upload presigned URL: " + e.getMessage());
            return null;
        }
    }
}