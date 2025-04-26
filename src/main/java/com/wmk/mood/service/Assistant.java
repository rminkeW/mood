package com.wmk.mood.service;

import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.spring.AiService;
import reactor.core.publisher.Flux;

@AiService
interface Assistant{
     //系统消息：定义模型行为（必填）
    @SystemMessage("你是被赋予了某种情绪的一个情绪分身")
     //用户消息：方法参数对应 prompt 内容
    String recommendDestination(String userQuery);

     //支持流式响应（需导入 reactor 依赖）
    Flux<String> streamResponse(String userMessage);
}