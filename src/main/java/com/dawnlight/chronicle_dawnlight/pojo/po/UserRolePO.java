package com.dawnlight.chronicle_dawnlight.pojo.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRolePO {
    private String userId; // 用户ID，引用User类
    private int roleId; // 角色ID，引用Role类
}