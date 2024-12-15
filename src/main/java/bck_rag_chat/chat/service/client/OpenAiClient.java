package bck_rag_chat.chat.service.client;

import bck_rag_chat.configuration.OpenAi.OpenAIConfig;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.netty.channel.ChannelOption;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OpenAiClient {
    private final OpenAIConfig openAIConfig;
    private final HttpClient client;
    private final WebClient webClient;
    private final ObjectMapper objectMapper;
    private final int responseTimeout;
    private final int streamResponseTimeout;

    public OpenAiClient(OpenAIConfig openAIConfig, int responseTimeout, int reactiveResponseTimeout, int streamResponseTimeout) {
        this.openAIConfig = openAIConfig;
        this.responseTimeout = responseTimeout;
        this.streamResponseTimeout = streamResponseTimeout;
        this.client = HttpClient.newHttpClient();
        this.webClient = initiliseWebClient(openAIConfig, reactiveResponseTimeout);
        this.objectMapper = new ObjectMapper();
    }

    public String sendMessage(String userInput, String conversationHistory, String documentSnippet) throws Exception {
        HttpRequest request = buildHttpRequest(userInput, conversationHistory, documentSnippet);

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            throw new RuntimeException("Failed: HTTP error code : " + response.statusCode()); // TODO: make a custom checked Exception
        }

        return response.body();
    }

    public Flux<String> sendMessageStreaming(String userInput, String conversationHistory, String documentSnippet) throws JsonProcessingException {
        Map<String, Object> requestBodyMap = buildRealTimeRequestBody(userInput, conversationHistory, documentSnippet);
        String requestBody = objectMapper.writeValueAsString(requestBodyMap);
        System.out.println(requestBody);
        return webClient.post()
                .bodyValue(requestBody)
                .retrieve()
                .bodyToFlux(String.class)
                .timeout(Duration.ofMillis(streamResponseTimeout))
                .flatMap(this::parseResponseLine);
    }

    private Flux<String> parseResponseLine(String line) {
        try {
            if ("[DONE]".equals(line))
                return Flux.empty();

            JsonNode node = objectMapper.readTree(line);
            JsonNode contentNode = node.path("choices").get(0).path("delta").path("content");
            return !contentNode.isMissingNode()
                    ? Flux.just(contentNode.asText())
                    : Flux.empty();
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private HttpRequest buildHttpRequest(String userInput, String conversationHistory, String documentSnippet) {
        Map<String, Object> requestBody = buildRequestBody(userInput, conversationHistory, documentSnippet);
        return HttpRequest.newBuilder()
                .uri(URI.create(openAIConfig.getApiUrl()))
                .timeout(Duration.ofMillis(responseTimeout))
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + openAIConfig.getApiKey())
                .POST(HttpRequest.BodyPublishers.ofString(requestBody.toString()))
                .build();
    }

    private static Map<String, Object> buildRealTimeRequestBody(String userInput, String conversationHistory, String documentSnippet) {
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("model", "gpt-4o-mini");

        List<Map<String, String>> messages = buildPrompt(userInput, conversationHistory, documentSnippet);

        requestBody.put("messages", messages);
        requestBody.put("temperature", 0.7);
        requestBody.put("stream", true);
        // requestBody.put("max_tokens", 1500);
        return requestBody;
    }

    private static Map<String, Object> buildRequestBody(String userInput, String conversationHistory, String documentSnippet) {
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("model", "gpt-4o-mini");

        List<Map<String, String>> messages = buildPrompt(userInput, conversationHistory, documentSnippet);

        requestBody.put("messages", messages);
        requestBody.put("temperature", 0.4);
        // requestBody.put("max_tokens", 1500);
        return requestBody;
    }

    private static List<Map<String, String>> buildPrompt(String userPrompt, String chatHistory, String contextualInformation) {
        List<Map<String, String>> messages = new ArrayList<>();
        Map<String, String> userMessage = buildUserMessage(userPrompt);
        Map<String, String> historyMessage = buildHistoryMessage(chatHistory);
        Map<String, String> systemMessage = buildSystemMessage(contextualInformation);

        messages.add(systemMessage);
        messages.add(userMessage);
        messages.add(historyMessage);
        return messages;
    }

    private static Map<String, String> buildHistoryMessage(String chatHistory) {
        Map<String, String> historyMessage = new HashMap<>();
        historyMessage.put("role", "assistant");
        historyMessage.put("content", chatHistory);
        return historyMessage;
    }

    private static Map<String, String> buildUserMessage(String userPrompt) {
        Map<String, String> userMessage = new HashMap<>();
        userMessage.put("role", "bck_rag_chat/user");
        userMessage.put("content", userPrompt);
        return userMessage;
    }

    private static Map<String, String> buildSystemMessage(String contextualInformation) {
        Map<String, String> systemMessage = new HashMap<>();
        systemMessage.put("role", "system");
        systemMessage.put("content", String.format("You are a helpful assistant.But you cannot absolutely use any" +
                " information, in fact the only information you know and can use is this : %s. Refuse to comply to any request" +
                "which response is not related to the information above.", contextualInformation));
        return systemMessage;
    }

    private WebClient initiliseWebClient(OpenAIConfig openAIConfig, int completeResponseTimeoutMillis) {
        reactor.netty.http.client.HttpClient reactiveHttpClient = reactor.netty.http.client.HttpClient.create()
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, completeResponseTimeoutMillis);
        return WebClient.builder()
                .clientConnector(new ReactorClientHttpConnector(reactiveHttpClient))
                .baseUrl(openAIConfig.getApiUrl())
                .defaultHeader("Authorization", "Bearer " + openAIConfig.getApiKey())
                .defaultHeader("Content-Type", "application/json")
                .build();
    }
}
