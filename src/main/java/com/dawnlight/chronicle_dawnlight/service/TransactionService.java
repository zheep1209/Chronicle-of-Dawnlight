package com.dawnlight.chronicle_dawnlight.service;

import com.dawnlight.chronicle_dawnlight.common.Result;
import com.dawnlight.chronicle_dawnlight.pojo.dto.TransactionDTO;
import com.dawnlight.chronicle_dawnlight.pojo.po.TransactionPO;
import com.dawnlight.chronicle_dawnlight.pojo.vo.TransactionVO;

import java.text.ParseException;
import java.time.LocalDate;
import java.util.Map;

public interface TransactionService {
    Result<String> create(TransactionDTO transactionDTO);

    Result<String> update(TransactionPO transactionPO);

    Result<TransactionVO> get(String id);

    Result<String> delete(String id);

    // 按天查询每日汇总
    Map<String, Object> getDailySummary(LocalDate targetDate);

    // 按月查询每月汇总
    Map<String, Object> getMonthlySummary(String targetMonth) throws ParseException;

    // 按年查询每年汇总
    Map<String, Object> getYearlySummary(int year) throws ParseException;
}
