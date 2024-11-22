package come.dawnlight.chronicle_dawnlight.pojo.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionCategoryPO {
    private Long id;                // 交易分类唯一标识
    private String name;            // 交易分类名称
    private String userId;          // 关联用户ID，与用户表的主键类型一致
    private LocalDateTime createdAt; // 创建时间
    private LocalDateTime updatedAt; // 更新时间
}
