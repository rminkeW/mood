package com.wmk.mood.controller;//package com.wmk.moodmelodymessenger.controller;
//
//import dev.langchain4j.model.chat.ChatLanguageModel;
//import org.springframework.web.bind.annotation.*;
//
//import javax.annotation.Resource;
//
//@RestController
////@RequestMapping("/chat")
////public class ChatController {
////
////    @Resource
////    private ChatService chatService;
////
////    @Resource
////    private UserService userService;
////
////    @PostMapping("/send")
////    public BaseResponse<String> sendMessage(@RequestBody ChatRequest chatRequest, HttpServletRequest request) {
////        User loginUser = userService.getLoginUser(request);
////        String response = chatService.generateResponse(chatRequest, loginUser);
////        return ResultUtils.success(response);
////    }
////}
//
//public class ChatController {
//
//    @Resource
//    private ChatLanguageModel chatLanguageModel;
//
//    @GetMapping("/chat")
//    public String model(@RequestParam(value = "message", defaultValue = "Hello") String message) {
//        return chatLanguageModel.chat(message);
//    }
//}