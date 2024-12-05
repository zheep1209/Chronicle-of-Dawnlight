package com.dawnlight.chronicle_dawnlight.pojo.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FolderPO {
    private Integer id;        // 文件夹ID
    private String userId;     // 用户ID，char(36)
    private Integer parentId;  // 父文件夹ID
    private String name;       // 文件夹名称
    private LocalDateTime createdAt;  // 文件夹创建时间
}
