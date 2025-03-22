package bck_instapic;

import groovyjarjarantlr4.v4.runtime.misc.NotNull;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.containers.localstack.LocalStackContainer;
import org.testcontainers.utility.DockerImageName;

public class TestContainerInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    private static final DockerImageName POSTGRES_IMAGE = DockerImageName
            .parse("ankane/pgvector:latest")
            .asCompatibleSubstituteFor("postgres");

    private static final DockerImageName LOCALSTACK_IMAGE = DockerImageName.parse("localstack/localstack:latest");

    private static final PostgreSQLContainer<?> postgreSQLContainer;
    private static final LocalStackContainer localStackContainer;

    static {
        // Initialize and start PostgreSQL container
        postgreSQLContainer = new PostgreSQLContainer<>(POSTGRES_IMAGE)
                .withDatabaseName("testdb")
                .withUsername("root")
                .withPassword("test");
        postgreSQLContainer.start();

        // Initialize and start LocalStack container (for S3)
        localStackContainer = new LocalStackContainer(LOCALSTACK_IMAGE)
                .withServices(LocalStackContainer.Service.S3)
                .withEnv("DEFAULT_REGION", "eu-central-1");
        localStackContainer.start();
    }

    @Override
    public void initialize(@NotNull ConfigurableApplicationContext applicationContext) {
        // We grab the S3 endpoint from LocalStack
        String s3Endpoint = localStackContainer
                .getEndpoint()
                .toString();

        // Retrieve the LocalStack credentials
        String accessKey = localStackContainer.getAccessKey();
        String secretKey = localStackContainer.getSecretKey();
        String region    = localStackContainer.getRegion();

        TestPropertyValues.of(
                // PostgreSQL properties
                "spring.datasource.primary.jdbc-url=" + postgreSQLContainer.getJdbcUrl(),
                "spring.datasource.primary.username=" + postgreSQLContainer.getUsername(),
                "spring.datasource.primary.password=" + postgreSQLContainer.getPassword(),
                "spring.datasource.primary.driver-class-name=" + postgreSQLContainer.getDriverClassName(),

                // AWS / LocalStack properties
                "aws.endpoint.override=" + s3Endpoint,
                "aws.accessKeyId=" + accessKey,
                "aws.secretAccessKey=" + secretKey,
                "aws.region=" + region
        ).applyTo(applicationContext.getEnvironment());
    }
}