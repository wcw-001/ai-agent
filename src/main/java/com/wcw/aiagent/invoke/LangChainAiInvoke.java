package com.wcw.aiagent.invoke;

import dev.langchain4j.community.model.dashscope.QwenChatModel;
import dev.langchain4j.model.chat.ChatModel;

public class LangChainAiInvoke {
    public static void main(String[] args) {
        ChatModel qwenModel = QwenChatModel.builder()
                .apiKey(TestApiKey.API_KEY)
                .modelName("qwen-max")
                .build();
        String result = qwenModel.chat("我是程序员阿狗");
        System.out.println(result);
    }
}
