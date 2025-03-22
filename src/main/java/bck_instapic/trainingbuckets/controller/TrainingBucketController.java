package bck_instapic.trainingbuckets.controller;

import bck_instapic.authentication.UserDetails.CustomUserDetails;
import bck_instapic.trainingbuckets.controller.response.PresignedUrlResponse;
import bck_instapic.user.model.User;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/v1/training-buckets")
public class TrainingBucketController {
    private final TrainingBucketDelegate trainingBucketDelegate;

    public TrainingBucketController(TrainingBucketDelegate trainingBucketDelegate) {
        this.trainingBucketDelegate = trainingBucketDelegate;
    }

    @GetMapping("/presigned-url")
    public PresignedUrlResponse createPresignedUrl(@AuthenticationPrincipal CustomUserDetails authenticatedUser) {
        UUID userId = authenticatedUser.getUser().id();
        return trainingBucketDelegate.createPresignedUrl(userId);
    }
}
