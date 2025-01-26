package bck_instapic.configuration.system;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SystemPropertiesConfiguration {

    @Value("${aws.default.region:eu-central-1}")
    String defaultRegion;

    @Value("${system.environment}")
    String environment;

    @Bean
    SystemProperties systemProperties() {
        return new SystemPropertiesBuilder()
                .withAwsRegion(defaultRegion)
                .withEnvironment(environment)
                .build();
    }
}
