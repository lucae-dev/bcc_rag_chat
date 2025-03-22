package bck_instapic.trainingbuckets.service;

import bck_instapic.BaseTest;
import bck_instapic.s3.service.S3Service;
import bck_instapic.trainingbuckets.configuration.TrainingBucketProperties;
import bck_instapic.trainingbuckets.controller.response.PresignedUrlResponse;
import bck_instapic.trainingbuckets.model.TrainingBucket;
import bck_instapic.trainingbuckets.repository.TrainingBucketRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.net.URL;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TrainingBucketServiceTest extends BaseTest {
    @Mock private TrainingBucketProperties trainingBucketProperties;
    @Mock private TrainingBucketRepository trainingBucketRepository;
    @Mock private S3Service s3Service;

    @Spy
    @InjectMocks
    private TrainingBucketService trainingBucketService;

    @Test
    void createPreSigned_shouldCreatePresigned() {
        // given
        UUID userId = UUID.randomUUID();

        TrainingBucket randomTrainingBucket = random(TrainingBucket.class);
        when(trainingBucketRepository.insert(any())).thenReturn(randomTrainingBucket);

        String randomBucketName = randomString();
        when(trainingBucketProperties.trainingBucketName()).thenReturn(randomBucketName);

        URL randomURl = random(URL.class);
        when(s3Service.generatePUTPresignedUrl(any(), any(), anyLong())).thenReturn(randomURl);

        // when
        PresignedUrlResponse response = trainingBucketService.createPresignedUrl(userId);

        // then
        assertNotNull(response);
        assertEquals(randomTrainingBucket.getId(), response.bucketId());
        assertEquals(randomURl.toString(), response.presignedUrl());
    }
}