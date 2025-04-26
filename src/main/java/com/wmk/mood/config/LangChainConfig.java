package com.wmk.mood.config;

import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import org.apache.tomcat.util.bcel.classfile.Constant;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static com.wmk.mood.constant.API.*;

@Configuration
public class LangChainConfig {

    @Bean
    public ChatLanguageModel chatLanguageModel() {
         //从环境变量中获取 OpenAI API 密钥
        String apiKey =API_KEY;

        //String apiKey = System.getenv("OPENAI_API_KEY");
        if (apiKey == null || apiKey.isEmpty()) {
            throw new RuntimeException("OPENAI_API_KEY environment variable is not set.");
        }
        OpenAiChatModel DOUmodel = OpenAiChatModel.builder()
                .apiKey(API_KEY)
                .baseUrl("https:ark.cn-beijing.volces.com/api/v3")
                .modelName("doubao-1-5-lite-32k-250115")
                .build();
        OpenAiChatModel model = OpenAiChatModel.builder()
                .baseUrl("http:langchain4j.dev/demo/openai/v1")
                .apiKey("demo")
                .modelName("gpt-4o-mini")
                .build();
//        OpenAiChatModel model = OpenAiChatModel.builder()
//                .apiKey(apiKey2)
//                .modelName("gpt-4o")
//                .build();
        return model;
    }
}