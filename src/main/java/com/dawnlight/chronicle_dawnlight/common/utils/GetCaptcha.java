package com.dawnlight.chronicle_dawnlight.common.utils;

import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class GetCaptcha {
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    /**
     * 生成一个六位的随机验证码，包含大写字母和数字。
     * @return 六位的随机验证码字符串。
     */
    public static String generateSixDigitCode() {
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 6; i++) {
            // 从CHARACTERS中随机选择一个字符
            int index = random.nextInt(CHARACTERS.length());
            char randomChar = CHARACTERS.charAt(index);
            sb.append(randomChar);
        }
        return sb.toString();
    }
}
