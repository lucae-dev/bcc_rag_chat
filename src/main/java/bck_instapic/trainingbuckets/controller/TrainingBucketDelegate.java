package bck_instapic.trainingbuckets.controller;

import bck_instapic.trainingbuckets.controller.response.PresignedUrlResponse;
import bck_instapic.trainingbuckets.service.TrainingBucketService;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class TrainingBucketDelegate {
    private final TrainingBucketService trainingBucketService;

    public TrainingBucketDelegate(TrainingBucketService trainingBucketService) {
        this.trainingBucketService = trainingBucketService;
    }

    public PresignedUrlResponse createPresignedUrl(UUID userId) {
        return trainingBucketService.createPresignedUrl(userId);
    }
}
