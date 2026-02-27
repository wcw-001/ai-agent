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

public class QueryTingWuUsage {
    private static final String BASE_API_URL =
            "https://dashscope.aliyuncs.com/api/v1/services/aigc/multimodal-generation/generation";
    private static final String MODEL_NAME = "tingwu-service-insights";
    private static final String TASK_GET_TASK = "getTask";

    /**
     * 构建查询任务参数
     * @param dataId 任务ID
     * @return 查询参数Map
     */
    public static Map<String, Object> buildGetTaskParams(String dataId) {
        Map<String, Object> params = new HashMap<>();
        params.put("task", TASK_GET_TASK);
        params.put("dataId", dataId);
        return params;
    }

    /**
     * 查询通用服务洞察任务状态和结果
     * @param apiKey API密钥
     * @param workspace 工作空间ID
     * @param dataId 任务ID
     * @return 查询结果
     */
    public static DashScopeResult queryTaskStatus(String apiKey, String workspace, String dataId)
            throws ApiException, NoApiKeyException, InputRequiredException {

        if (apiKey == null || apiKey.isBlank()) {
            throw new InputRequiredException("apiKey 不能为空");
        }
        if (dataId == null || dataId.isBlank()) {
            throw new InputRequiredException("dataId 不能为空");
        }

        // 创建听悟服务实例
        TingWu tingwu = new TingWu(Protocol.HTTP.getValue(), BASE_API_URL);

        TingWuParam.TingWuParamBuilder paramBuilder = TingWuParam.builder()
                .model(MODEL_NAME)
                .input(buildGetTaskParams(dataId))
                .apiKey(apiKey);
        if (workspace != null && !workspace.isBlank()) {
            paramBuilder.workspace(workspace);
        }
        TingWuParam param = paramBuilder.build();

        // 执行查询
        return tingwu.call(param);
    }

    /**
     * 解析任务状态
     * @param result 查询结果
     * @return 任务状态信息
     */
    public static String parseTaskStatus(DashScopeResult result) {
        try {
            if (result == null) {
                return "查询结果为空";
            }
            // DashScopeResult 通过异常处理错误，这里直接解析输出

            JsonObject output = JsonUtils.toJsonObject(result.getOutput());
            if (output == null || !output.has("status")) {
                return "响应中缺少 status 字段";
            }
            String status = output.get("status").getAsString();

            switch (status) {
                case "0":
                    return "任务成功完成";
                case "1":
                    return "任务进行中";
                case "2":
                    String errorCode = output.has("errorCode") ? output.get("errorCode").getAsString() : "未知错误";
                    String errorMessage = output.has("errorMessage") ? output.get("errorMessage").getAsString() : "无错误信息";
                    return "任务失败 - 错误码: " + errorCode + ", 错误信息: " + errorMessage;
                default:
                    return "未知状态: " + status;
            }
        } catch (Exception e) {
            return "解析状态失败: " + e.getMessage();
        }
    }

    /**
     * 获取转录结果
     * @param result 查询结果
     * @return 转录结果内容
     */
    public static String getTranscriptionResult(DashScopeResult result) {
        try {
            JsonObject output = JsonUtils.toJsonObject(result.getOutput());
            return output.has("transcriptionPath")
                    ? "转录结果已生成，可通过URL访问: " + output.get("transcriptionPath").getAsString()
                    : "暂无转录结果";
        } catch (Exception e) {
            return "获取转录结果失败: " + e.getMessage();
        }
    }

    /**
     * 获取服务洞察结果
     * @param result 查询结果
     * @return 服务洞察结果内容
     */
    public static String getGeneralServiceInsightsResult(DashScopeResult result) {
        try {
            JsonObject output = JsonUtils.toJsonObject(result.getOutput());
            return output.has("generalServiceInsightsPath")
                    ? "服务洞察结果已生成，可通过URL访问: " + output.get("generalServiceInsightsPath").getAsString()
                    : "暂无服务洞察结果";
        } catch (Exception e) {
            return "获取服务洞察结果失败: " + e.getMessage();
        }
    }

    /**
     * 获取销售洞察结果
     * @param result 查询结果
     * @return 销售洞察结果内容
     */
    public static String getGeneralSaleInsightsResult(DashScopeResult result) {
        try {
            JsonObject output = JsonUtils.toJsonObject(result.getOutput());
            return output.has("generalSaleInsightsPath")
                    ? "销售洞察结果已生成，可通过URL访问: " + output.get("generalSaleInsightsPath").getAsString()
                    : "暂无销售洞察结果";
        } catch (Exception e) {
            return "获取销售洞察结果失败: " + e.getMessage();
        }
    }

    public static void main(String[] args) {
        try {
            // 配置参数
            String apiKey = "sk-affc42369fa14b65b28b84469c3a56a7";
            String workspace = "ws-g5f0e7av9t38ob5p";
            String dataId = "l9AnwLMDhS0D";

            // 查询任务状态
            DashScopeResult result = queryTaskStatus(apiKey, workspace, dataId);

            // 输出完整结果
            System.out.println("查询结果:");
            System.out.println(JsonUtils.toJson(result));

            // 解析并输出状态信息
//            System.out.println("\n任务状态: " + parseTaskStatus(result));
//
//            // 直接打印结果
//            System.out.println("\n" + getTranscriptionResult(result));
//            System.out.println(getGeneralServiceInsightsResult(result));
//            System.out.println(getGeneralSaleInsightsResult(result));

        } catch (ApiException | NoApiKeyException | InputRequiredException e) {
            System.err.println("查询失败: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("程序执行出错: " + e.getMessage());
        }
    }
}
