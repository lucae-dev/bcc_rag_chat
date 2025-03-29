package bck_instapic.replicate.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ReplicateConfiguration {

    @Value("${replicate.base.url}")
    private String baseUrl;

    @Value("${replicate.api.key}")
    private String apiKey;

    @Bean
    public ReplicateProperties getReplicateProperties() {
        return new ReplicatePropertiesBuilder()
                .withBaseUrl(baseUrl)
                .withApiKey(apiKey)
                .build();
    }
}
