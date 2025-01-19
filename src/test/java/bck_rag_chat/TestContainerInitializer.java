package bck_rag_chat;

import groovyjarjarantlr4.v4.runtime.misc.NotNull;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;

public class TestContainerInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
    private static final DockerImageName POSTGRES_IMAGE = DockerImageName.parse("ankane/pgvector:latest")
            .asCompatibleSubstituteFor("postgres");
    private static final DockerImageName MINIO_IMAGE = DockerImageName.parse("minio/minio:latest");

    private static final PostgreSQLContainer<?> postgreSQLContainer;
    private static final GenericContainer<?> minioContainer;

    static {
        // Initialize and start PostgreSQL container
        postgreSQLContainer = new PostgreSQLContainer<>(POSTGRES_IMAGE)
                .withDatabaseName("testdb")
                .withUsername("root")
                .withPassword("test");
        postgreSQLContainer.start();

        // Initialize and start MinIO container
        minioContainer = new GenericContainer<>(MINIO_IMAGE)
                .withEnv("MINIO_ACCESS_KEY", "minioadmin")
                .withEnv("MINIO_SECRET_KEY", "minioadmin")
                .withCommand("server", "/data")
                .withExposedPorts(9000)
                //.waitingFor(Wait.forLogMessage(".*APIv2.*ready.*", 1))
        ;
        minioContainer.start();
    }

    @Override
    public void initialize(@NotNull ConfigurableApplicationContext applicationContext) {
        String minioUrl = String.format("http://%s:%d",
                "127.0.0.1",
                minioContainer.getMappedPort(9000));

        TestPropertyValues.of(
                // PostgreSQL properties
                "spring.datasource.primary.jdbc-url=" + postgreSQLContainer.getJdbcUrl(),
                "spring.datasource.primary.email=" + postgreSQLContainer.getUsername(),
                "spring.datasource.primary.password=" + postgreSQLContainer.getPassword(),
                "spring.datasource.primary.driver-class-name=" + postgreSQLContainer.getDriverClassName(),

                // MinIO properties
                "aws.enpoint.overrride=" + minioUrl,
                "aws.accessKeyId=minioadmin",
                "aws.secretAccessKey=minioadmin"
        ).applyTo(applicationContext.getEnvironment());
    }


}