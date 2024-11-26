package come.dawnlight.chronicle_dawnlight.mapper;

import come.dawnlight.chronicle_dawnlight.pojo.po.TransactionPO;
import come.dawnlight.chronicle_dawnlight.pojo.vo.TransactionVO;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Mapper
public interface TransactionMapper {
    int create(TransactionPO transactionPO);

    int update(@Param("transactionPO") TransactionPO transactionPO);

    TransactionVO get(String id, String string);

    int delete(String id);

    //获取当日全部交易数据
    @MapKey("id")
    List<Map<String, Object>> getTransactionsDay(@Param("userId") String userId, @Param("date") LocalDate targetDate);

    //获取当日全部总收支和收支差
    Object getTotalIncomeDay(@Param("userId") String userId, @Param("date") LocalDate targetDate);

    //根据分类获取当日全部收入
    @MapKey("id")
    List<Map<String, Object>> getDailyBreakdownExpense(@Param("userId") String userId, @Param("date") LocalDate date);
    //根据分类获取当日全部支出
    @MapKey("id")
    List<Map<String, Object>> getDailyBreakdownIncome(@Param("userId") String userId, @Param("date") LocalDate date);

    //获取当月全部交易数据
    @MapKey("id")
    List<Map<String, Object>> selectMonthlySummary(@Param("userId") String userId, @Param("month") Date targetMonth);

    //获取当月全部总收支和收支差
    Map<String, Object> getTotalIncomeMonth(@Param("userId") String userId, @Param("month") Date targetMonth);

    //根据分类获取当月全部支出
    @MapKey("name")
    List<Map<String, Object>> getMonthlyBreakdownExpense(@Param("userId") String userId, @Param("month") Date targetMonth);
    //根据分类获取当月全部收入
    @MapKey("name")
    List<Map<String, Object>> getMonthlyBreakdownIncome(@Param("userId") String userId, @Param("month") Date targetMonth);

    //返回的每月的总收支
    @MapKey("id")
    List<Map<String, Object>> totalMonthlyIncomeExpenditure(@Param("userId") String userId, @Param("year") int year);

    //获取当年全部收支和收支差
    Map<String, Object> getTotalIncomeYear(@Param("userId") String userId, @Param("year") int year);
    //根据分类获取当年全部支出
    @MapKey("name")
    List<Map<String, Object>> getYearlyBreakdownExpense(@Param("userId") String userId, @Param("year") int year);
    //根据分类获取当年全部收入
    @MapKey("name")
    List<Map<String, Object>> getYearlyBreakdownIncome(@Param("userId") String userId, @Param("year") int year);

}


