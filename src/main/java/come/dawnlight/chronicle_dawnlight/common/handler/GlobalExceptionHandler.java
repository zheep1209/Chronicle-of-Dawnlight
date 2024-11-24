package come.dawnlight.chronicle_dawnlight.common.handler;

import come.dawnlight.chronicle_dawnlight.common.Result;
import come.dawnlight.chronicle_dawnlight.common.exception.BaseException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.IOException;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler
    public Result<String> exceptionHandler(BaseException ex) {
        log.error("异常信息：{}", ex.getMessage());
        return Result.error(ex.getMessage());
    }
    @ExceptionHandler
    public Result<String> exceptionDBHandler(DataIntegrityViolationException ex) {
        log.error("异常信息：{}", ex.getMessage());
//        if (ex.getCause() instanceof BadSqlGrammarException) {
//            // 处理语法错误
//            return Result.error("SQL语法错误");
//        } else {
//            // 处理唯一约束冲突
//            return Result.error("交易类别名称已存在");
//        }
        return Result.error(ex.getMessage());
    }

    @ExceptionHandler
    public Result exceptionHandler(Exception ex) {
        if (ex.getMessage().contains("6379")){
            log.error("Redis未启动");
            return Result.error("Redis未启动");
        }
        log.error("异常信息：{}", ex.getMessage());
        return Result.error(ex.getMessage());
    }
}
