package bck_rag_chat;

import bck_rag_chat.chat.service.client.OpenAiClient;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class IntegrationTestMockContext {
    @MockBean private OpenAiClient openAiClient;
}
