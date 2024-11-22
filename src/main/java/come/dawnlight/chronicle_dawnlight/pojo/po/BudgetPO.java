package come.dawnlight.chronicle_dawnlight.pojo.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BudgetPO {
    private Long id;                // 预算记录唯一标识
    private String userId;          // 关联用户表的主键
    private Long categoryId;        // 关联交易分类表
    private BigDecimal budgetAmount; // 预算金额
    private LocalDateTime startDate; // 预算开始日期
    private LocalDateTime endDate;   // 预算结束日期
    private LocalDateTime createdAt; // 创建时间
    private LocalDateTime updatedAt; // 更新时间
}
