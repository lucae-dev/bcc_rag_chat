package bck_rag_chat.s3.config;

import bck_rag_chat.configuration.system.SystemProperties;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;

import java.net.URI;

@Configuration
public class S3Config {

    private final SystemProperties systemProperties;

    @Value("${aws.accessKeyId:minioadmin}")
    private String accessKeyId;

    @Value("${aws.secretAccessKey:minioadmin}")
    private String secretAccessKey;

    @Value("${aws.region:us-east-1}")
    private String awsRegion;

    @Value("${aws.enpoint.overrride}")
    private String awsEndpointOverride;

    public S3Config(SystemProperties systemProperties) {
        this.systemProperties = systemProperties;
    }

    private AwsCredentialsProvider getAwsCredentialsProvider() {
        StaticCredentialsProvider credentialsProvider = StaticCredentialsProvider.create(
                AwsBasicCredentials.create(accessKeyId, secretAccessKey)
        );

        AwsCredentialsProvider awsCredentialsProvider = "PROD".equals(systemProperties.environment())
                ? DefaultCredentialsProvider.create()
                : credentialsProvider;
        return awsCredentialsProvider;
    }

    @Bean
    public S3Client s3Client() {
        return S3Client.builder()
                .endpointOverride(awsEndpointOverride != null ? URI.create(awsEndpointOverride) : null)
                .region(Region.of(awsRegion))
                .credentialsProvider(getAwsCredentialsProvider())
                .build();
    }

    @Bean
    public S3Presigner s3Presigner() {
        return S3Presigner.builder()
                .endpointOverride(awsEndpointOverride != null ? URI.create(awsEndpointOverride) : null)
                .region(Region.of(systemProperties.awsRegion()))
                .credentialsProvider(getAwsCredentialsProvider())
                .build();
    }
}
