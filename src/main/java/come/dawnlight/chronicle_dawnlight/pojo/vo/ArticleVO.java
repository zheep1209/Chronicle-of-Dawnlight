package come.dawnlight.chronicle_dawnlight.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArticleVO {
    private String userId;              // 用户ID，与用户表关联
    private String username;            // 用户名
    private String avatar;
    private Integer articleCount;
    private Integer categoryCount;
    private String title;               // 文章标题
    private String content;             // 文章内容
    private LocalDateTime createdAt;    // 创建时间
    private LocalDateTime updatedAt;    // 更新时间
}
