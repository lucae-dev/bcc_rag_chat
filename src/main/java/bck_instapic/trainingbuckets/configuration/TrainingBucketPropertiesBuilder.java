package bck_instapic.trainingbuckets.configuration;

public final class TrainingBucketPropertiesBuilder {
    private String trainingBucketName;

    public TrainingBucketPropertiesBuilder withTrainingBucketName(String trainingBucketName) {
        this.trainingBucketName = trainingBucketName;
        return this;
    }

    public TrainingBucketProperties build() {
        return new TrainingBucketProperties(trainingBucketName);
    }
}
