package com.wmk.mood.controller;

import com.wmk.mood.common.BaseResponse;
import com.wmk.mood.common.ResultUtils;
import com.wmk.mood.model.response.ChatResponse;
import dev.langchain4j.http.client.spring.restclient.SpringRestClientBuilder;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class ChatController {
    ChatLanguageModel chatLanguageModel;
    public ChatController(ChatLanguageModel chatLanguageModel) {
        this.chatLanguageModel = chatLanguageModel;
    }

    @PostMapping("/chat")
    public BaseResponse<String> chat(@RequestBody Map<String, String> request) {

        String message = request.get("message");
        chatLanguageModel = OpenAiChatModel.builder()
                .httpClientBuilder(new SpringRestClientBuilder())
                .maxTokens(1000)
                .apiKey("sk-d420897495334e3e8447dca802944b2e")
                .baseUrl("https://api.deepseek.com")
                .modelName("deepseek-chat")
                .build();
        String responseMessage = chatLanguageModel.chat(message);
        return ResultUtils.success(responseMessage);
    }
}