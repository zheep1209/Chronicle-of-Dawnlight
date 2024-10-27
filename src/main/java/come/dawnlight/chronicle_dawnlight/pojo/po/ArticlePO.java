package come.dawnlight.chronicle_dawnlight.pojo.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ArticlePO {
    private Long id;                // 文章ID，主键，自增
    private String userId;          // 用户ID，与用户表关联
    private String title;           // 文章标题
    private String content;         // 文章内容
    private Boolean isPrivate;      // 是否为私人文章
    private LocalDateTime createdAt; // 创建时间
    private LocalDateTime updatedAt; // 更新时间
}
