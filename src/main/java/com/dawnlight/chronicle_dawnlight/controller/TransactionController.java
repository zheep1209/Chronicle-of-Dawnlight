package com.dawnlight.chronicle_dawnlight.controller;

import com.dawnlight.chronicle_dawnlight.common.Result;
import com.dawnlight.chronicle_dawnlight.pojo.dto.TransactionDTO;
import com.dawnlight.chronicle_dawnlight.pojo.po.TransactionPO;
import com.dawnlight.chronicle_dawnlight.pojo.vo.TransactionVO;
import com.dawnlight.chronicle_dawnlight.service.TransactionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.time.LocalDate;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/transaction")
public class TransactionController {
    @Autowired
    private TransactionService transactionService;

    /**
     * 创建交易记录
     *
     * @param transactionDTO 交易记录
     * @return Result
     */
    @PostMapping("/create")
    public Result<String> createTransaction(@RequestBody TransactionDTO transactionDTO) {
        log.info("创建交易记录,{}", transactionDTO);
        return transactionService.create(transactionDTO);
    }

    /**
     * @param transactionPO 交易记录
     * @return Result
     */
    @PutMapping("/update")
    public Result<String> updateTransaction(@RequestBody TransactionPO transactionPO) {
        return transactionService.update(transactionPO);
    }

    @GetMapping("/get")
    public Result<TransactionVO> getTransaction(@RequestParam(value = "id") String id) {
        return transactionService.get(id);
    }

    @DeleteMapping("/delete")
    public Result<String> deleteTransaction(@RequestParam(value = "id") String id) {
        return transactionService.delete(id);
    }

    // 获取指定日期的每日汇总
    @GetMapping("/daily")
    public Result<Map<String, Object>> getDailySummary(@RequestParam("date") String date) {
        log.info("每日总汇");
        LocalDate targetDate = LocalDate.parse(date);
        Map<String, Object> dailySummary = transactionService.getDailySummary(targetDate);
        log.info("awa{}", dailySummary);
        return Result.success(dailySummary);
    }

    // 获取指定月份的每月汇总

    /**
     * 每天的数据需要返回每天的总收入和总支出，以及收支差，为了方便前端发起请求，需要带上当天的日期，当点击当天的方块后，选择模式应该
     * 切换为1，让当前页面切换到每日记录，并且发起请求，获取当日的所有收支记录
     *
     * @param month
     * @return
     * @throws ParseException
     */
    @GetMapping("/monthly")
    public Result<Map<String, Object>> getMonthlySummary(@RequestParam("month") String month) throws ParseException {
        log.info("传入的月份参数: {}", month);
        Map<String, Object> monthlySummary = transactionService.getMonthlySummary(month);
        return Result.success(monthlySummary);
    }

    // 获取指定年份的每年汇总

    /**
     * 获取每年数据需要返回每个月的数据，如果当月没有输入数据，那么就不返回这个月的数据，月数据为每天的收支差，为了获取当月每天的颜色
     * 每个月需要带上当前的年份和每个月份的数据便于前端向后端发起请求
     *
     * @param year
     * @return
     */
    @GetMapping("/yearly")
    public Result<Map<String, Object>> getYearlySummary(@RequestParam("year") int year) throws ParseException {
        Map<String, Object> yearlySummary = transactionService.getYearlySummary(year);
        return Result.success(yearlySummary);
    }
}
