package bck_rag_chat.configuration.OpenAi;

import bck_rag_chat.chat.service.ChatService;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAIConfig {
    private static final Logger logger = LoggerFactory.getLogger(OpenAIConfig.class);


    @Getter
    @Value("${openai.api.url}")
    private String apiUrl;

    @Value("${OPENAI_API_KEY}")
    private String apiKey;

    public String getApiKey() {
        if (apiKey == null || apiKey.isEmpty()) {
            logger.error("OpenAI API key is not set.");
        } else {
            logger.info("OpenAI API key is set.");
        }
        System.out.println(apiKey);
        return apiKey;
    }
}
