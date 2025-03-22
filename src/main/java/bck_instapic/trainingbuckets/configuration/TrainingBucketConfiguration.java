package bck_instapic.trainingbuckets.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TrainingBucketConfiguration {

    @Value("${trainings.bucket.name}")
    String trainingsBucketName;

    @Bean
    public TrainingBucketProperties trainingBucketProperties() {
        return new TrainingBucketPropertiesBuilder()
                .withTrainingBucketName(trainingsBucketName)
                .build();
    }
}
