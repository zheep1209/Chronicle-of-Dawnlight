package com.dawnlight.chronicle_dawnlight.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionVO {
    private String id;              // 交易记录唯一标识
    private BigDecimal amount;      // 交易金额（正为收入，负为支出）
    private String type;            // 交易类型，收入或支出           （数据库用ID查找
    private String category;        // 分类名                      （数据库用ID查找
    private String description;     // 交易描述
    private LocalDateTime date;     // 时间
}
