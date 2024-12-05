package com.dawnlight.chronicle_dawnlight.service.impl;

import com.dawnlight.chronicle_dawnlight.common.Result;
import com.dawnlight.chronicle_dawnlight.common.utils.BaseContext;
import com.dawnlight.chronicle_dawnlight.mapper.TransactionMapper;
import com.dawnlight.chronicle_dawnlight.pojo.dto.TransactionDTO;
import com.dawnlight.chronicle_dawnlight.pojo.po.TransactionPO;
import com.dawnlight.chronicle_dawnlight.pojo.vo.TransactionVO;
import com.dawnlight.chronicle_dawnlight.service.TransactionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

@Service
public class TransactionServiceImpl implements TransactionService {
    private static final Logger log = LoggerFactory.getLogger(TransactionServiceImpl.class);
    @Autowired
    private TransactionMapper transactionMapper;

    @Override
    public Result<String> create(TransactionDTO transactionDTO) {
        TransactionPO transactionPO = new TransactionPO();
        transactionPO.setId(UUID.randomUUID().toString());
        transactionPO.setUserId(BaseContext.getCurrentThreadId().toString());
        transactionPO.setAmount(Objects.equals(transactionDTO.getType(), "expense") ? transactionDTO.getAmount().negate() : transactionDTO.getAmount());
        transactionPO.setCategoryId(transactionDTO.getCategoryId());
        transactionPO.setType(transactionDTO.getType());
        transactionPO.setDescription(transactionDTO.getDescription());
        transactionPO.setDate(transactionDTO.getDate());
        int result = transactionMapper.create(transactionPO);
        if (result == 0) return Result.error("添加失败");
        return Result.success("添加成功");
    }

    @Override
    public Result<String> update(TransactionPO transactionPO) {
        if (Objects.equals(transactionPO.getType(), "expense") && transactionPO.getAmount().compareTo(BigDecimal.ZERO) > 0) {
            transactionPO.setAmount(transactionPO.getAmount().negate());
        }
        int result = transactionMapper.update(transactionPO);
        return Result.success("修改成功");
    }

    @Override
    public Result<TransactionVO> get(String id) {
        TransactionVO transactionVO = transactionMapper.get(id, BaseContext.getCurrentThreadId().toString());
        if (transactionVO == null) {
            return Result.error("找不到该交易，或你不是当前交易的用户");
        }
        return Result.success(transactionVO);
    }

    @Override
    public Result<String> delete(String id) {
        int result = transactionMapper.delete(id);
        return result == 0 ? Result.error("删除失败") : Result.success("删除成功");
    }

    @Override
    public Map<String, Object> getDailySummary(LocalDate targetDate) {
        Map<String, Object> result = new HashMap<>();
        Object totals = transactionMapper.getTotalIncomeDay(BaseContext.getCurrentThreadId().toString(), targetDate);
        result.put("total", totals);
        List<Map<String, Object>> transactions = transactionMapper.getTransactionsDay(BaseContext.getCurrentThreadId().toString(), targetDate);
        result.put("trList", transactions);
        Map<String, Object> breakdown = new HashMap<>();
        List<Map<String, Object>> expense = transactionMapper.getDailyBreakdownExpense(BaseContext.getCurrentThreadId().toString(), targetDate);
        breakdown.put("expense", expense);
        List<Map<String, Object>> income = transactionMapper.getDailyBreakdownIncome(BaseContext.getCurrentThreadId().toString(), targetDate);
        breakdown.put("income", income);
        result.put("breakdown", breakdown);
        return result;
    }

    @Override
    public Map<String, Object> getMonthlySummary(String targetMonth) throws ParseException {
        String targetDate = targetMonth + "-01";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = sdf.parse(targetDate);
        Map<String, Object> result = new HashMap<>();
        Map<String, Object> totals = transactionMapper.getTotalIncomeMonth(BaseContext.getCurrentThreadId().toString(), date);
        result.put("total", totals);
        List<Map<String, Object>> transactions = transactionMapper.selectMonthlySummary(BaseContext.getCurrentThreadId().toString(), date);
        result.put("trList", transactions);
        Map<String, Object> breakdown = new HashMap<>();
        List<Map<String, Object>> expense = transactionMapper.getMonthlyBreakdownExpense(BaseContext.getCurrentThreadId().toString(), date);
        breakdown.put("expense", expense);
        List<Map<String, Object>> income = transactionMapper.getMonthlyBreakdownIncome(BaseContext.getCurrentThreadId().toString(), date);
        breakdown.put("income", income);
        result.put("breakdown", breakdown);
        return result;
    }

    @Override
    public Map<String, Object> getYearlySummary(int year) throws ParseException {
        Map<String, Object> result = new HashMap<>();
        //获取当年全部收支和收支差
        Map<String, Object> totalIncomeYear = transactionMapper.getTotalIncomeYear(BaseContext.getCurrentThreadId().toString(), year);
        result.put("total", totalIncomeYear);
        //每月的收支差
        List<Map<String, Object>> monthsTotal = transactionMapper.totalMonthlyIncomeExpenditure(BaseContext.getCurrentThreadId().toString(), year);
        result.put("monthsTotal", monthsTotal);

        // 初始化日期格式化器和存储结果的集合
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        List<List<Map<String, Object>>> transactions = new ArrayList<>();

        // 获取当前年份
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);

        // 循环生成每月的日期并查询数据
        for (int i = 1; i <= 12; i++) {
            // 格式化月份为两位数，例如 "01"
            String targetDate = currentYear + "-" + String.format("%02d", i) + "-01";

            // 将字符串解析为日期对象
            Date date = sdf.parse(targetDate);

            // 调用数据库查询方法，假设 BaseConxtext 提供用户 ID
            List<Map<String, Object>> maps = transactionMapper.selectMonthlySummary(BaseContext.getCurrentThreadId().toString(), date);

            // 将结果添加到总集合
            transactions.add(maps);
            result.put("months", transactions);
        }
        Map<String, Object> breakdown = new HashMap<>();
        List<Map<String, Object>> expense = transactionMapper.getYearlyBreakdownExpense(BaseContext.getCurrentThreadId().toString(), year);
        breakdown.put("expense", expense);
        List<Map<String, Object>> income = transactionMapper.getYearlyBreakdownIncome(BaseContext.getCurrentThreadId().toString(), year);
        breakdown.put("income", income);
        result.put("breakdown", breakdown);
        return result;
    }
}