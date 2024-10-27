package come.dawnlight.chronicle_dawnlight.controller;

import come.dawnlight.chronicle_dawnlight.common.Result;
import come.dawnlight.chronicle_dawnlight.common.exception.BaseException;
import come.dawnlight.chronicle_dawnlight.common.utils.GetCaptcha;
import come.dawnlight.chronicle_dawnlight.common.utils.HttpClientUtils;
import come.dawnlight.chronicle_dawnlight.common.utils.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@Slf4j
public class CaptchaController {
    @Autowired
    private  GetCaptcha getCaptcha;
    @Autowired
    private RedisUtil redisUtil;

    /**
     * 获取邮箱验证码
     * @param email
     * @return
     */
    @GetMapping("/getEmail")
    public Result getEmail(@RequestParam String email) throws IOException {
//
        String code = GetCaptcha.generateSixDigitCode();
        redisUtil.set("code", code, 5);

        String html = "<body style='margin: 0;padding: 0;'><div style='height: 100vh ;width: 100%;background:url(https://zheep-demo.oss-cn-beijing.aliyuncs.com/PEbg.jpg)no-repeat;background-size: cover;background-position: 50% 0;z-index: -1;'><div style='display: flex;justify-content: center;align-items: center;flex-direction: column;;background-color: rgba(0, 0, 0, 0.6);width: 100%;height: 100vh;z-index: 2000;'><div style='width: 100px;height: 100px;border-radius: 50%;margin-bottom: 200px;box-shadow: 0 0 10px 2px pink;background:url(https://zheep-demo.oss-cn-beijing.aliyuncs.com/avatar.png);background-size:100px 100px;background-position: 50% 50%;'></div><div style='color: white;font-size: 24px;margin-bottom: 20px;background:none;'>曦光录</div><div style='color: white;font-size: 48px;border-bottom: 1px solid #fff;background:none;'>" + code + "</div></div></div></body>";
        String url = "https://luckycola.com.cn/tools/customMail";
        Map<String, Object> jsonMap = new HashMap<>();
        jsonMap.put("ColaKey", "IEnd3i1gGqbBDc1729523817436kyVsWtr7Np");
        jsonMap.put("tomail", email);
        jsonMap.put("fromTitle", "曦光录");
        jsonMap.put("subject", "您的 zheep 帐户：来自网页或移动设备的访问");
        jsonMap.put("content", html);
        jsonMap.put("isTextContent", false);
        jsonMap.put("smtpCode", "wbzulaoqweopcaci");
        jsonMap.put("smtpEmail", "zheep1209@qq.com");
        jsonMap.put("smtpCodeType", "qq");
        String result = HttpClientUtils.post(url, jsonMap);
        JSONObject jsonObj = new JSONObject(result);
        if (jsonObj.getInt("code") == 0) {
            return Result.success();
        }
        return Result.error(jsonObj.getString("msg"));
    }
}
