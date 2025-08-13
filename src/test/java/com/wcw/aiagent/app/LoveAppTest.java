package com.wcw.aiagent.app;

import jakarta.annotation.Resource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

@SpringBootTest
class LoveAppTest {

    @Resource
    private LoveApp loveApp;

    @Test
    void chat() {
        String chatId = UUID.randomUUID().toString();
        //第一轮
        String question = "你好，我是阿狗";
        String answer = loveApp.chat(question, chatId);
        Assertions.assertNotNull(answer);
        //第二轮
        question = "我想另一个人（阿猫）喜欢上我";
        answer = loveApp.chat(question, chatId);
        Assertions.assertNotNull(answer);
        //第三轮
        question = "我想让谁喜欢我？刚刚跟你说过";
        answer = loveApp.chat(question, chatId);
        Assertions.assertNotNull(answer);
    }
}
