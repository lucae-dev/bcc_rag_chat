package bck_rag_chat.service;


import bck_rag_chat.configuration.OpenAIConfig;
import bck_rag_chat.controller.request.ChatRequest;
import bck_rag_chat.controller.request.ChatResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.*;

@Service
public class ChatService {
    private static final Logger logger = LoggerFactory.getLogger(ChatService.class);

    private final OpenAIConfig openAIConfig;
    private final RestTemplate restTemplate;


    @Autowired
    public ChatService(OpenAIConfig openAIConfig, RestTemplate restTemplate) {
        this.openAIConfig = openAIConfig;
        this.restTemplate = restTemplate;
    }

    public ChatResponse getChatResponse(ChatRequest chatRequest) {
        try {
            String apiUrl = openAIConfig.getApiUrl();
            String apiKey = openAIConfig.getApiKey();

            // Construct messages
            List<Map<String, String>> messages = new ArrayList<>();

            Map<String, String> systemMessage = new HashMap<>();
            systemMessage.put("role", "system");
            systemMessage.put("content", "You are a helpful assistant.");
            messages.add(systemMessage);

            Map<String, String> userMessage = new HashMap<>();
            userMessage.put("role", "user");
            userMessage.put("content", chatRequest.getMessage());
            messages.add(userMessage);

            // Build the request body
            Map<String, Object> requestBody = new HashMap<>();
            requestBody.put("model", "gpt-3.5-turbo"); // or 'gpt-4' if you have access
            requestBody.put("messages", messages);

            // Set up headers
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setBearerAuth(apiKey);
            System.out.println(headers);

            HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(requestBody, headers);

            // Make the POST request
            ResponseEntity<String> responseEntity = restTemplate.postForEntity(apiUrl, requestEntity, String.class);

            // Check response status
            if (responseEntity.getStatusCode() == HttpStatus.OK) {
                String responseBody = responseEntity.getBody();

                // Parse the response
                ObjectMapper objectMapper = new ObjectMapper();
                Map<String, Object> responseMap = objectMapper.readValue(responseBody, Map.class);
                List<Map<String, Object>> choices = (List<Map<String, Object>>) responseMap.get("choices");
                Map<String, Object> message = (Map<String, Object>) choices.get(0).get("message");
                String reply = (String) message.get("content");

                return new ChatResponse(reply.trim());
            } else {
                logger.error("Error: Received status code {}", responseEntity.getStatusCode());
                return new ChatResponse("Sorry, I am unable to process your request at the moment.");
            }

        } catch (Exception e) {
            logger.error("Error communicating with OpenAI API", e);
            return new ChatResponse("Sorry, I am unable to process your request at the moment.");
        }
    }
}