package come.dawnlight.chronicle_dawnlight.pojo.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionPO {
    private String id;               // 交易记录唯一标识
    private String userId;           // 关联用户表的主键
    private BigDecimal amount;       // 交易金额（正为收入，负为支出）
    private Long categoryId;         // 关联交易分类表
    private String type;             // 交易类型，收入或支出
    private String description;      // 交易描述
    private LocalDateTime date;      // 交易时间
    private LocalDateTime createdAt; // 创建时间
    private LocalDateTime updatedAt; // 更新时间
}
