package bck_rag_chat.chat.service;

import bck_rag_chat.chat.controller.request.ChatRequest;
import bck_rag_chat.chat.service.client.OpenAiClient;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OpenAiChatService {
    private final OpenAiClient client;
    private final VectorStore vectorStore;

    public OpenAiChatService(OpenAiClient openAiClient, VectorStore vectorStore) {
        this.client = openAiClient;
        this.vectorStore = vectorStore;
    }

    public Flux<String> getRealTimeChatResponse(ChatRequest chatRequest) throws JsonProcessingException {
        validateChatRequest(chatRequest);
        String relevantContext = retrieveRelevantContext(chatRequest.getMessage());
        return client.sendMessageStreaming(chatRequest.getMessage(), "", relevantContext);
    }

    private void validateChatRequest(ChatRequest chatRequest) {
        if (chatRequest == null || chatRequest.getMessage() == null || chatRequest.getMessage().isEmpty() ||chatRequest.getMessage().length() > 2000) {
            throw new IllegalArgumentException("Invalid chat request");
        }
    }

    private String retrieveRelevantContext(String userInput) {
        List<Document> similarDocuments = Optional.of(userInput)
                .map(input -> SearchRequest.query(input).withTopK(2))
                .map(vectorStore::similaritySearch)
                .orElseGet(Collections::emptyList);

        return similarDocuments.stream()
                .map(Document::getContent)
                .collect(Collectors.joining());
    }
}
