package com.dawnlight.chronicle_dawnlight.pojo.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FilePO {
    private Integer id;           // 文件ID
    private String userId;        // 用户ID，char(36)
    private String fileName;      // 文件名
    private Long fileSize;        // 文件大小（字节）
    private String filePath;      // 文件路径
    private Integer folderId;     // 文件所属文件夹ID，NULL代表根目录
    private LocalDateTime uploadTime; // 上传时间
    private String description;   // 文件描述
}
