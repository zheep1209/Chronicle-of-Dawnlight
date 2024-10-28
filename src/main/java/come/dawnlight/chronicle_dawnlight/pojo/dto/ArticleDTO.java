package come.dawnlight.chronicle_dawnlight.pojo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ArticleDTO {
    private String title;
    private String content;
    private Boolean isPrivate;
    private Long categoryId;        // 分类ID
    private LocalDateTime createdAt; // 创建时间
    private LocalDateTime updatedAt; // 更新时间
    // Getters and Setters
}
