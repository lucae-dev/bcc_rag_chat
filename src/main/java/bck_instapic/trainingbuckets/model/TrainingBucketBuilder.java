package bck_instapic.trainingbuckets.model;

import java.time.Instant;
import java.util.UUID;

public final class TrainingBucketBuilder {
    private UUID id;
    private Instant insertDate;
    private Instant updateDate;
    private long version;
    private long orderUnique;
    private UUID userId;
    private String s3Path;
    private String status;

    public TrainingBucketBuilder withId(UUID id) {
        this.id = id;
        return this;
    }

    public TrainingBucketBuilder withInsertDate(Instant insertDate) {
        this.insertDate = insertDate;
        return this;
    }

    public TrainingBucketBuilder withUpdateDate(Instant updateDate) {
        this.updateDate = updateDate;
        return this;
    }

    public TrainingBucketBuilder withVersion(long version) {
        this.version = version;
        return this;
    }

    public TrainingBucketBuilder withOrderUnique(long orderUnique) {
        this.orderUnique = orderUnique;
        return this;
    }

    public TrainingBucketBuilder withUserId(UUID userId) {
        this.userId = userId;
        return this;
    }

    public TrainingBucketBuilder withS3Path(String s3Path) {
        this.s3Path = s3Path;
        return this;
    }

    public TrainingBucketBuilder withStatus(String status) {
        this.status = status;
        return this;
    }

    public TrainingBucket build() {
        TrainingBucket trainingBucket = new TrainingBucket(userId, s3Path, status);
        trainingBucket.setId(id);
        trainingBucket.setInsertDate(insertDate);
        trainingBucket.setUpdateDate(updateDate);
        trainingBucket.setVersion(version);
        trainingBucket.setOrderUnique(orderUnique);
        return trainingBucket;
    }
}
