package com.wcw.aiagent.app;

import org.junit.jupiter.api.Test;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONArray;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.FormatType;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.http.ProtocolType;
import com.aliyuncs.profile.DefaultProfile;
public class    ServiceInspectionTest {

    @Test
    public void testServiceInspection() throws ClientException {
        CommonRequest request = createCommonRequest("tingwu.cn-beijing.aliyuncs.com", "2023-09-30", ProtocolType.HTTPS, MethodType.PUT, "/openapi/tingwu/v2/tasks");
        request.putQueryParameter("type", "offline");

        JSONObject root = new JSONObject();
        root.put("AppKey", "输入您在听悟管控台创建的Appkey");

        JSONObject input = new JSONObject();
        input.fluentPut("FileUrl", "输入待测试的音频url链接")
                .fluentPut("SourceLanguage", "cn")
                .fluentPut("TaskKey", "task" + System.currentTimeMillis());
        root.put("Input", input);

        JSONObject parameters = new JSONObject();
        parameters.put("ServiceInspectionEnabled", true);
        JSONObject serviceInspection = new JSONObject();
        serviceInspection.fluentPut("SceneIntroduction", "长安深蓝汽车门店线下销售场景")
                .fluentPut("InspectionIntroduction", "请检测对话中汽车销售人员表现是否接待热情、态度良好")
                .fluentPut("InspectionContents", new JSONArray()
                        .fluentAdd(new JSONObject().fluentPut("Title", "到店迎接-欢迎语").fluentPut("Content", "销售在开场白的时候主动向客户打招呼进行欢迎"))
                        .fluentAdd(new JSONObject().fluentPut("Title", "离店送别-客户留资").fluentPut("Content", "销售邀请客户留下微信、电话号码、名片等联系方式"))
                        .fluentAdd(new JSONObject().fluentPut("Title", "到店迎接-饮品提供").fluentPut("Content", "销售在接待客户的时候主动询问是否需要饮料（如咖啡、橙汁、水、茶等）、点心、零食、水果等。"))
                );
        parameters.put("ServiceInspection", serviceInspection);
        root.put("Parameters", parameters);
        System.out.println(root.toJSONString());
        request.setHttpContent(root.toJSONString().getBytes(), "utf-8", FormatType.JSON);

        // TODO 请通过环境变量设置您的AccessKeyId、AccessKeySecret
        DefaultProfile profile = DefaultProfile.getProfile("cn-beijing", System.getenv("ALIBABA_CLOUD_ACCESS_KEY_ID"), System.getenv("ALIBABA_CLOUD_ACCESS_KEY_SECRET"));
        IAcsClient client = new DefaultAcsClient(profile);
        CommonResponse response = client.getCommonResponse(request);
        System.out.println(response.getData());
    }

    public static CommonRequest createCommonRequest(String domain, String version, ProtocolType protocolType, MethodType method, String uri) {
        // 创建API请求并设置参数
        CommonRequest request = new CommonRequest();
        request.setSysDomain(domain);
        request.setSysVersion(version);
        request.setSysProtocol(protocolType);
        request.setSysMethod(method);
        request.setSysUriPattern(uri);
        request.setHttpContentType(FormatType.JSON);
        return request;
    }
}
