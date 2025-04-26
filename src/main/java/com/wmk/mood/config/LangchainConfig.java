package com.wmk.mood.config;

import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.openai.OpenAiChatModel;

public class LangchainConfig
{
    public ChatLanguageModel chatLanguageModel(){
        return OpenAiChatModel.builder()
                .maxTokens(1000)
                .apiKey("2c8417d8-934e-4004-b50b-5b3fc7f39990")
                .baseUrl("https://ark.cn-beijing.volces.com/api/v3/chat/completions")
                .modelName("doubao-1-5-lite-32k-250115")
                .temperature(0d)
                .maxRetries(3)
                .build();
    }
}
