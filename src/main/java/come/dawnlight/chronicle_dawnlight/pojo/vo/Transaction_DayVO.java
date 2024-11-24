package come.dawnlight.chronicle_dawnlight.pojo.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
@Data
public class Transaction_DayVO {
    private Long id;
    private Long userId;
    private BigDecimal amount;
    private Long categoryId;
    private String type;
    private String description;
    private LocalDateTime date;
    private BigDecimal totalIncome;
    private BigDecimal totalExpense;
}
