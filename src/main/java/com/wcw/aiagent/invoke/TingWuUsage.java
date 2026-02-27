package com.wcw.aiagent.invoke;

import com.alibaba.dashscope.common.DashScopeResult;
import com.alibaba.dashscope.exception.ApiException;
import com.alibaba.dashscope.exception.InputRequiredException;
import com.alibaba.dashscope.exception.NoApiKeyException;
import com.alibaba.dashscope.multimodal.tingwu.TingWu;
import com.alibaba.dashscope.multimodal.tingwu.TingWuParam;
import com.alibaba.dashscope.protocol.Protocol;
import com.alibaba.dashscope.utils.JsonUtils;
import com.google.gson.JsonObject;

import java.util.HashMap;
import java.util.Map;

public class TingWuUsage {
    /**
     * 每行必须按照如下格式：
     *
     * ${发言人名称}: ${发言人内容}
     */
    private static final String  TEXT = """
            张三: 早上好！今天感觉精神特别好呢。
            李四: 早安！确实是个美好的一天开始。
            张三: 昨天那个电影你看了吗？剧情真的很精彩。
            李四: 看了看了！特别是结尾那个反转，完全没想到。
            张三: 对对对，导演的功力真的很强。
            李四: 说到电影，这周末有部新片上映，要不要一起去看？
            张三: 好主意！是什么类型的电影？
            李四: 是科幻片，据说特效做得很棒。
            张三: 那太好了，我一直很喜欢科幻题材。
            李四: 那就这么说定了，周六晚上七点电影院见。
            张三: 没问题，期待和你一起观影！
            """;
    public static Map<String, Object> buildCreateTask(String appId) {
        Map<String, Object> params = new HashMap<>();
        params.put("appId", appId);
        // 请输入音视频文件地址、对话文本或任务ID（三选一）
        // params.put("fileUrl", "音视频文件地址");
        params.put("text", TEXT);
        // params.put("dataId", "任务ID");
        params.put("task", "createTask");
        //设置其他参数
        return params;
    }

    public static Map<String, Object> buildGetTask(String dataId) {
        Map<String, Object> params = new HashMap<>();
        params.put("dataId", dataId);
        params.put("task", "getTask");
        return params;
    }

    public static void main(String[] args) {
        try {
            String baseApiUrl = "https://dashscope.aliyuncs.com/api/v1/services/aigc/multimodal-generation/generation";
            String apiKey = "sk-affc42369fa14b65b28b84469c3a56a7";
            // 创建任务
            TingWu tingwu = new TingWu(Protocol.HTTP.getValue(),baseApiUrl);
            TingWuParam createTaskParam = TingWuParam.builder()
                    // 自动替换为当前业务空间Id
                    .workspace("ws-g5f0e7av9t38ob5p")
                    // 自动替换为当前model
                    .model("tingwu-service-insights")
                    // 自动替换成当前appId
                    .input(buildCreateTask("tw_zGwIdVVcnJML"))
                    // 设置parameters参数
                    .parameters(new HashMap<>())
                    .apiKey(apiKey)
                    .build();
            DashScopeResult createTaskResult = tingwu.call(createTaskParam);
            System.out.println(JsonUtils.toJson(createTaskResult));
            JsonObject jsonResult = JsonUtils.toJsonObject(createTaskResult.getOutput());

            // 查询任务
            TingWuParam getTaskParam = TingWuParam.builder()
                    // 自动替换为当前业务空间Id
                    .workspace("ws-g5f0e7av9t38ob5p")
                    // 自动替换为当前model
                    .model("tingwu-service-insights")
                    // 从创建任务的response中获得dataId
                    .input(buildGetTask(jsonResult.get("dataId").getAsString()))
                    // 请填写你的apiKey
                    .apiKey(apiKey)
                    .build();
            DashScopeResult getTaskResult = tingwu.call(getTaskParam);
            System.out.println(JsonUtils.toJson(getTaskResult));
        } catch (ApiException | NoApiKeyException | InputRequiredException e) {
            System.out.println(e.getMessage());
        }
        System.exit(0);
    }



}
