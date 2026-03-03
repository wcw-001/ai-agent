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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TingWuUsage {
    /**
     * 每行必须按照如下格式：
     *
     * ${发言人名称}: ${发言人内容}
     */
//    private static final String  TEXT = """
//            张三: 早上好！今天感觉精神特别好呢。
//            李四: 早安！确实是个美好的一天开始。
//            张三: 昨天那个电影你看了吗？剧情真的很精彩。
//            李四: 看了看了！特别是结尾那个反转，完全没想到。
//            张三: 对对对，导演的功力真的很强。
//            李四: 说到电影，这周末有部新片上映，要不要一起去看？
//            张三: 好主意！是什么类型的电影？
//            李四: 是科幻片，据说特效做得很棒。
//            张三: 那太好了，我一直很喜欢科幻题材。
//            李四: 那就这么说定了，周六晚上七点电影院见。
//            张三: 没问题，期待和你一起观影！
//            """;
    private static final String  TEXT = """
            客服：您好，欢迎光临本店，请问有什么可以帮您？
            用户：你好，我昨天买的那个衣服，什么时候能发货啊？
            客服：您好，您先别着急，我这边帮您查询一下。请问您方便提供一下订单号吗？
            用户：订单号是 202500123456。
            客服：好的，我查到了，您的订单今天下午就会安排发货，预计明天就能有物流信息。
            用户：这么慢啊，我以为今天上午就能发。
            客服：非常抱歉让您久等了，我们仓库正在按订单顺序处理，您的包裹已经在打包中了，一完成就会立刻发出。
            用户：那如果收到不合适能退换吗？
            客服：可以的，我们支持七天无理由退换货，只要不影响二次销售，都可以正常办理。
            用户：行，那我知道了。
            客服：好的，感谢您的理解，后续有任何问题随时联系我们，祝您购物愉快，再见。
            """;

    private static final String URL = "https://fancydog.oss-rg-china-mainland.aliyuncs.com/2026%E5%B9%B402%E6%9C%8827%E6%97%A5%2016%E7%82%B928%E5%88%86.mp3";
    public static Map<String, Object> buildCreateTask(String appId) {
        Map<String, Object> params = new HashMap<>();
        params.put("appId", appId);
        // 请输入音视频文件地址、对话文本或任务ID（三选一）
        params.put("fileUrl", URL);
        //params.put("text", TEXT);
        // params.put("dataId", "任务ID");
        params.put("task", "createTask");
        //设置其他参数
        return params;
    }
    /**
     * 构建电商客服场景的insightsContents参数
     */
    public static Map<String, Object> buildEcommerceInsightsParams() {
        // 外层parameters
        Map<String, Object> parameters = new HashMap<>();
        //parameters.put("IdentityRecognitionEnabled", true);

        // 1. 说话人分离参数（必开）
//        Map<String, Object> speakerDiarization = new HashMap<>();
//        speakerDiarization.put("enabled", true);
//        speakerDiarization.put("speakerCount", 2); // 客服场景固定2人
//        parameters.put("speakerDiarization", speakerDiarization);

        // 2. 身份识别参数（必开）
        //Map<String, Object> identityRecognition = new HashMap<>();
        //identityRecognition.put("SceneIntroduction", "电商客服场景");

        // 身份内容定义
//        List<Map<String, Object>> identityContents = new ArrayList<>();

        // 客服身份定义
//        Map<String, Object> agentIdentity = new HashMap<>();
//        agentIdentity.put("name", "客服");
//        agentIdentity.put("description", "提供服务、解答问题的一方");
//        identityContents.add(agentIdentity);
//
//        // 客户身份定义
//        Map<String, Object> customerIdentity = new HashMap<>();
//        customerIdentity.put("name", "客户");
//        customerIdentity.put("description", "咨询问题、表达诉求的一方");
//        identityContents.add(customerIdentity);

        //identityRecognition.put("identityContents", identityContents);
        //parameters.put("identityRecognition", identityRecognition);

        // serviceInsights核心对象
        Map<String, Object> serviceInsights = new HashMap<>();

        // 核心：insightsContents洞察维度列表
        List<Map<String, Object>> insightsContents = new ArrayList<>();

        // 1. 洞察项1：客服使用规范开场白
//        Map<String, Object> greetingItem = new HashMap<>();
//        greetingItem.put("title", "规范开场白");
//        greetingItem.put("content", "客服在对话开头使用了友好且规范的开场白，如“您好，欢迎光临本店，请问有什么可以帮您？”，而非无问候直接回应");
//        greetingItem.put("score", "20"); // 命中加20分
//        insightsContents.add(greetingItem);
//
//        // 2. 洞察项2：客服解答发货时间问题
//        Map<String, Object> deliveryAnswerItem = new HashMap<>();
//        deliveryAnswerItem.put("title", "发货时间解答");
//        deliveryAnswerItem.put("content", "客服明确告知用户订单发货时间（如“今天下午安排发货，预计明天有物流信息”），而非模糊回应或未解答");
//        deliveryAnswerItem.put("score", "30");
//        insightsContents.add(deliveryAnswerItem);
//
//        // 3. 洞察项3：客服主动致歉（用户不满时）
//        Map<String, Object> apologyItem = new HashMap<>();
//        apologyItem.put("title", "主动致歉");
//        apologyItem.put("content", "用户表达不满（如“这么慢啊”）后，客服主动致歉，如“非常抱歉让您久等了”，而非无回应或辩解");
//        apologyItem.put("score", "15");
//        insightsContents.add(apologyItem);
//
//        // 4. 洞察项4：客服说明退换货政策
//        Map<String, Object> returnPolicyItem = new HashMap<>();
//        returnPolicyItem.put("title", "退换货政策说明");
//        returnPolicyItem.put("content", "用户询问退换货问题后，客服明确告知“七天无理由退换货”及条件（不影响二次销售），解答完整");
//        returnPolicyItem.put("score", "25");
//        insightsContents.add(returnPolicyItem);
//
//        // 5. 洞察项5：客服使用规范结束语
//        Map<String, Object> closingItem = new HashMap<>();
//        closingItem.put("title", "规范结束语");
//        closingItem.put("content", "对话结束时客服使用友好结束语，如“感谢您的理解，祝您购物愉快，再见”，而非无结束语直接结束");
//        closingItem.put("score", "10");
//        insightsContents.add(closingItem);
//
//        // 6. 洞察项6：客服服务态度友好（无负面用语）
//        Map<String, Object> attitudeItem = new HashMap<>();
//        attitudeItem.put("title", "服务态度友好");
//        attitudeItem.put("content", "客服全程使用礼貌用语（如“您”“抱歉”“感谢”），无不耐烦、敷衍、生硬等负面用语");
//        attitudeItem.put("score", "40");
//        insightsContents.add(attitudeItem);

        // 语速洞察项的 Map 初始化（与致歉项格式完全一致）
        Map<String, Object> speedItem = new HashMap<>();
        // 洞察项标题
        speedItem.put("title", "长时间静音");
        // 洞察项详细描述（贴合客服沟通场景，清晰说明评分标准）
        speedItem.put("content", "客服沉默，长时间静音");
        // 洞察项分值（参考致歉项的分值量级，设置为15分）
        speedItem.put("score", "15");
        insightsContents.add(speedItem);

        //语速太快
        Map<String, Object> speedItem2 = new HashMap<>();
        speedItem2.put("title", "语速太快");
        speedItem2.put("content", "客服语速 > 4.17 字/秒，判定过快");
        speedItem2.put("score", "15");
        insightsContents.add(speedItem2);

        //语速合适
        Map<String, Object> speedItem3 = new HashMap<>();
        speedItem3.put("title", "语速正常");
        speedItem3.put("content", "客服语速 3.33～4.17 字/秒，判定正常");
        speedItem3.put("score", "15");
        insightsContents.add(speedItem3);

        //语速太慢
        Map<String, Object> speedItem4 = new HashMap<>();
        speedItem4.put("title", "语速太慢");
        speedItem4.put("content", "客服语速 < 3.33 字/秒，判定过慢");
        speedItem4.put("score", "15");
        insightsContents.add(speedItem4);

        // 组装参数
        serviceInsights.put("insightsContents", insightsContents);
        parameters.put("serviceInsights", serviceInsights);


        return parameters;
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
            String workspace = "ws-g5f0e7av9t38ob5p";
            String appId = "tw_zGwIdVVcnJML";

            // 选择场景：true为电商客服场景，false为汽车销售场景
            boolean useEcommerceScenario = true;

            // 1. 构建洞察参数
            Map<String, Object> insightsParams =  buildEcommerceInsightsParams();

            System.out.println("使用场景: " + (useEcommerceScenario ? "电商客服" : "汽车销售"));
            System.out.println("参数配置: " + JsonUtils.toJson(insightsParams));

            // 创建任务
            TingWu tingwu = new TingWu(Protocol.HTTP.getValue(), baseApiUrl);
            TingWuParam createTaskParam = TingWuParam.builder()
                    .workspace(workspace)
                    .model("tingwu-service-insights")
                    .input(buildCreateTask(appId))
                    .parameters(insightsParams)
                    .apiKey(apiKey)
                    .build();
            DashScopeResult createTaskResult = tingwu.call(createTaskParam);
            System.out.println("\n创建任务结果:");
            System.out.println(JsonUtils.toJson(createTaskResult));

            JsonObject jsonResult = JsonUtils.toJsonObject(createTaskResult.getOutput());

            if (jsonResult.has("dataId")) {
                String dataId = jsonResult.get("dataId").getAsString();

                // 等待任务完成后再查询结果
                Thread.sleep(3000);

                // 查询任务
                TingWuParam getTaskParam = TingWuParam.builder()
                        .workspace(workspace)
                        .model("tingwu-service-insights")
                        .input(buildGetTask(dataId))
                        .apiKey(apiKey)
                        .build();
                DashScopeResult getTaskResult = tingwu.call(getTaskParam);
                System.out.println("\n查询任务结果:");
                System.out.println(JsonUtils.toJson(getTaskResult));
            }

        } catch (ApiException | NoApiKeyException | InputRequiredException e) {
            System.err.println("API调用失败: " + e.getMessage());
        } catch (InterruptedException e) {
            System.err.println("线程中断: " + e.getMessage());
            Thread.currentThread().interrupt();
        } catch (Exception e) {
            System.err.println("程序执行出错: " + e.getMessage());
        }
        System.exit(0);
    }



}
