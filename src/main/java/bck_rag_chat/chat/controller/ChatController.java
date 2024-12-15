package bck_rag_chat.chat.controller;

import bck_rag_chat.chat.controller.request.ChatRequest;
import bck_rag_chat.chat.controller.request.ChatResponse;
import bck_rag_chat.chat.service.ChatService;
import bck_rag_chat.chat.service.OpenAiChatService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;


@CrossOrigin(origins = "http://localhost:3000") // React app origin
@RestController
@RequestMapping("/api/chat")
public class ChatController {
//    private final ChatService chatService;
    private final OpenAiChatService openAiChatService;

    @Autowired
    public ChatController(
//            ChatService chatService,
            OpenAiChatService openAiChatService) {
//        this.chatService = chatService;
        this.openAiChatService = openAiChatService;
    }
//
//    @PostMapping
//    public ChatResponse chat(@RequestBody ChatRequest request) {
//        return chatService.getChatResponse(request);
//    }

    @PostMapping(value = "/stream")
    public Flux<String> realTimeChat(@RequestBody ChatRequest request) {
        try {
            return openAiChatService.getRealTimeChatResponse(request);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
