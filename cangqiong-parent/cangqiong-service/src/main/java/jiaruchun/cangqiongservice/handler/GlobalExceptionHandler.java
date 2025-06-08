package jiaruchun.cangqiongservice.handler;

import jiaruchun.common.exce.BaseException;
import jiaruchun.pojo.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler
    public Result exceptionHandler(BaseException ex) {
        log.error("异常信息：{}", ex.getMessage());
        return Result.error(ex.getMessage());
    }

    @ExceptionHandler
    public Result exceptionHandler(Exception ex) {
        StackTraceElement[] stackTrace = ex.getStackTrace();
        String errorClassName = "未知类";
        if (stackTrace.length > 0) {
            errorClassName = stackTrace[0].getClassName();
        }
        log.error("未定义异常信息：{}，错误所在类：{}", ex.getMessage(), errorClassName);
        return Result.error("操作失败");
    }
}