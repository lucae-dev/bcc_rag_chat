package bck_instapic.trainingbuckets.service;

import bck_instapic.s3.service.S3Service;
import bck_instapic.trainingbuckets.configuration.TrainingBucketProperties;
import bck_instapic.trainingbuckets.controller.response.PresignedUrlResponse;
import bck_instapic.trainingbuckets.controller.response.PresignedUrlResponseBuilder;
import bck_instapic.trainingbuckets.model.TrainingBucket;
import bck_instapic.trainingbuckets.model.TrainingBucketBuilder;
import bck_instapic.trainingbuckets.repository.TrainingBucketRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.PresignedPutObjectRequest;

import java.net.URL;
import java.time.Instant;
import java.util.UUID;

@Service
@Transactional
public class TrainingBucketService {

    private final TrainingBucketProperties trainingBucketProperties;
    private final TrainingBucketRepository trainingBucketRepository;
    private final S3Service s3Service;

    public TrainingBucketService(TrainingBucketProperties trainingBucketProperties, TrainingBucketRepository trainingBucketRepository,
                                 S3Service s3Service) {
        this.trainingBucketProperties = trainingBucketProperties;
        this.trainingBucketRepository = trainingBucketRepository;
        this.s3Service = s3Service;
    }

    public PresignedUrlResponse processNewTrainingBucket(UUID userId) {
        // TODO: validate user can request a new training (for example by checking if the training models are already the max)
        return createPresignedUrl(userId);
    }

    @Transactional
    public PresignedUrlResponse createPresignedUrl(UUID userId) {
        TrainingBucket bucket = createTrainingBucketEntity(userId);

        URL presignedUrl = generatePresignedUrl(bucket);

        return new PresignedUrlResponseBuilder()
                .withBucketId(bucket.getId())
                .withUrl(presignedUrl.toString())
                .build();
    }

    private URL generatePresignedUrl(TrainingBucket bucket) {
        long expirationInSeconds = 15L * 60L;
        return s3Service.generatePUTPresignedUrl(trainingBucketProperties.trainingBucketName(), bucket.getS3Path(), expirationInSeconds);
    }

    private TrainingBucket createTrainingBucketEntity(UUID userId) {
        TrainingBucket bucket = new TrainingBucketBuilder()
                .withId(UUID.randomUUID())
                .withUserId(userId)
                .withS3Path(generateUniqueS3Path(userId))
                .withStatus("CREATED")
                .withInsertDate(Instant.now())
                .withVersion(1L)
                .build();

        return trainingBucketRepository.insert(bucket);
    }

    private String generateUniqueS3Path(UUID userId) {
        return "users/" + userId + "/images/" + UUID.randomUUID() + ".jpg";
    }
}