package bck_instapic.trainingbuckets.controller.response;

import java.util.UUID;

public final class PresignedUrlResponseBuilder {
    private UUID bucketId;
    private String url;

    public PresignedUrlResponseBuilder withBucketId(UUID bucketId) {
        this.bucketId = bucketId;
        return this;
    }

    public PresignedUrlResponseBuilder withUrl(String url) {
        this.url = url;
        return this;
    }

    public PresignedUrlResponse build() {
        return new PresignedUrlResponse(bucketId, url);
    }
}
