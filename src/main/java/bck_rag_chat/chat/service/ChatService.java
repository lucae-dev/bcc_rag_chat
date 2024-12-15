package bck_rag_chat.chat.service;


import bck_rag_chat.configuration.OpenAi.OpenAIConfig;
import bck_rag_chat.chat.controller.request.ChatRequest;
import bck_rag_chat.chat.controller.request.ChatResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChatService {
    private static final Logger logger = LoggerFactory.getLogger(ChatService.class);

    private final OpenAIConfig openAIConfig;
    private final RestTemplate restTemplate;
    private final ChatClient chatClient;

    public ChatService(OpenAIConfig openAIConfig, RestTemplate restTemplate, ChatClient.Builder chatClientBuilder) {
        this.openAIConfig = openAIConfig;
        this.restTemplate = restTemplate;
        this.chatClient = chatClientBuilder.build();
    }

//    public ChatResponse getChatResponse1(ChatRequest chatRequest) {
//        String responseMessage = chatClient.prompt()
//                .user(chatRequest.getMessage())
//                .call()
//                .content();
//        return new ChatResponse(responseMessage);
//    }

    public ChatResponse getChatResponse(ChatRequest chatRequest) {
        try {
            String apiUrl = openAIConfig.getApiUrl();
            String apiKey = openAIConfig.getApiKey();

            // Construct messages
            List<Map<String, String>> messages = buildPrompt(chatRequest);

            // Build the request body
            Map<String, Object> requestBody = new HashMap<>();
            requestBody.put("model", "gpt-4o-mini"); // or 'gpt-4' if you have access
            requestBody.put("messages", messages);

            // Set up headers
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setBearerAuth(apiKey);

            HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(requestBody, headers);
            //System.out.println(requestEntity);

            // Make the POST request
            ResponseEntity<String> responseEntity = restTemplate.postForEntity(apiUrl, requestEntity, String.class);

            // Check response status
            if (responseEntity.getStatusCode() == HttpStatus.OK) {
                String responseBody = responseEntity.getBody();

                System.out.println(responseEntity);
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

    private static List<Map<String, String>> buildPrompt(ChatRequest chatRequest) {
        List<Map<String, String>> messages = new ArrayList<>();
        Map<String, String> systemMessage = buildSystemMessage(chatRequest.getMessage());
        Map<String, String> userMessage = buildUserMessage(chatRequest);
        Map<String, String> historyMessage = buildHistoryMessage(chatRequest);

        messages.add(systemMessage);
        messages.add(userMessage);
        messages.add(historyMessage);
        return messages;
    }

    private static Map<String, String> buildHistoryMessage(ChatRequest chatRequest) {
        Map<String, String> historyMessage = new HashMap<>();
        historyMessage.put("role", "assistant");
        historyMessage.put("content", chatRequest.getMessage());
        return historyMessage;
    }

    private static Map<String, String> buildUserMessage(ChatRequest chatRequest) {
        Map<String, String> userMessage = new HashMap<>();
        userMessage.put("role", "bck_rag_chat/user");
        userMessage.put("content", chatRequest.getMessage());
        return userMessage;
    }

    private static Map<String, String> buildSystemMessage(String message) {
        Map<String, String> systemMessage = new HashMap<>();
        String contextInfo = ""; // retrieve info with RAG
        systemMessage.put("role", "system");
        systemMessage.put("content", String.format("You are a helpful assistant. With these information: %s", contextInfo));
        return systemMessage;
    }
}