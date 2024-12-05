package com.dawnlight.chronicle_dawnlight.controller;

import com.dawnlight.chronicle_dawnlight.common.utils.RedisUtil;
import com.dawnlight.chronicle_dawnlight.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.dawnlight.chronicle_dawnlight.common.utils.GetCaptcha.generateSixDigitCode;

/**
 * 测试邮件发送
 *
 * @author qzz
 */
@RestController
@RequestMapping("/mail")
public class SendMailController {

    @Autowired
    private MailService mailService;
    @Autowired
    private RedisUtil redisUtil;


    /**
     * 发送文本邮件
     *
     * @param to
     * @param subject
     * @param text
     */
    @RequestMapping("/sendTextMail")
    public void sendTextMail(String to, String subject, String text) {
        mailService.sendTextMailMessage(to, subject, text);
    }

    /**
     * 发送HTML邮件
     *
     * @param to
     * @param subject
     * @param content
     */
    @GetMapping("/sendHtmlMailMessage")
    public void sendHtmlMailMessage(String to, String subject, String content) {
        mailService.sendHtmlMailMessage(to, subject, content);
    }

    /**
     * 发送HTML邮箱验证码
     *
     * @param to 邮箱
     */
    @RequestMapping("/sendMailCaptcha")
    public void sendHtmlMailMessage(String to) {
        String subject = "曦光录";
        //生成验证码
        String code = generateSixDigitCode();
        redisUtil.set(to + "code", code, 1);
        String content = "<body style='margin: 0;padding: 0;'><div style='height: 100vh ;width: 100%;background:url(https://zheep-demo.oss-cn-beijing.aliyuncs.com/PEbg.jpg)no-repeat;background-size: cover;background-position: 50% 0;z-index: -1;'><div style='display: flex;justify-content: center;align-items: center;flex-direction: column;;background-color: rgba(0, 0, 0, 0.6);width: 100%;height: 100vh;z-index: 2000;'><div style='width: 100px;height: 100px;border-radius: 50%;margin-bottom: 200px;box-shadow: 0 0 10px 2px pink;background:url(https://zheep-demo.oss-cn-beijing.aliyuncs.com/avatar.png);background-size:100px 100px;background-position: 50% 50%;'></div><div style='color: white;font-size: 24px;margin-bottom: 20px;background:none;'>曦光录</div><div style='color: white;font-size: 48px;border-bottom: 1px solid #fff;background:none;'>" + code + "</div></div></div></body>";
        mailService.sendHtmlMailMessage(to, subject, content);
    }
}

