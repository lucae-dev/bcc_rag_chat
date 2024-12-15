package bck_rag_chat;

import groovyjarjarantlr4.v4.runtime.misc.NotNull;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;

public class TestContainerInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
    private static final DockerImageName POSTGRES_IMAGE = DockerImageName.parse("ankane/pgvector:latest").asCompatibleSubstituteFor("postgres");
    private static final PostgreSQLContainer<?> postgreSQLContainer;

    static {
        postgreSQLContainer = new PostgreSQLContainer<>(POSTGRES_IMAGE)
                .withDatabaseName("testdb")
                .withUsername("test")
                .withPassword("test");
        postgreSQLContainer.start();
    }

    @Override
    public void initialize(@NotNull ConfigurableApplicationContext applicationContext) {
        TestPropertyValues.of(
                "spring.datasource.primary.jdbc-url=" + postgreSQLContainer.getJdbcUrl(),
        "spring.datasource.primary.email=" + postgreSQLContainer.getUsername(),
        "spring.datasource.primary.password=" + postgreSQLContainer.getPassword(),
        "spring.datasource.primary.driver-class-name=" + postgreSQLContainer.getDriverClassName()
        ).applyTo(applicationContext.getEnvironment());
    }
}
