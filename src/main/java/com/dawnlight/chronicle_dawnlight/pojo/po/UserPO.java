package com.dawnlight.chronicle_dawnlight.pojo.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class UserPO {
    private String id; // 用户ID，使用UUID
    private String username; // 用户名
    private String password; // 加密后的密码
    private String email; // 邮箱，唯一
    private String avatar; // 头像URL
    private int status; // 用户状态，1为激活，0为禁用
    private Integer role; // 用户角色，引用Role类
    private LocalDateTime createdAt; // 创建时间
    private LocalDateTime updatedAt; // 更新时间
}
