package com.dawnlight.chronicle_dawnlight.pojo.po;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CategoryPO {
    private Long id;
    private String name;
    private String userId;  // 关联用户ID
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
