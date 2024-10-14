package bck_rag_chat.configuration.OpenAi;

import bck_rag_chat.service.chat.client.OpenAiClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAiClientConfig {
    @Value(("${open.ai.client.response.timeout}"))
    private int responseTimeout;

    @Value(("${open.ai.client.reactive.response.timeout}"))
    private int reactiveTimeout;

    @Value(("${open.ai.client.stream.response.timeout}"))
    private int streamTimeout;

    private final OpenAIConfig openAIConfig;

    public OpenAiClientConfig(OpenAIConfig openAIConfig) {
        this.openAIConfig = openAIConfig;
    }

    @Bean
    public OpenAiClient getOpenAiClient() {
        return new OpenAiClient(openAIConfig, responseTimeout, reactiveTimeout, streamTimeout);
    }
}
