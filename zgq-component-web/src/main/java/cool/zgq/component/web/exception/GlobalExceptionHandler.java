package cool.zgq.component.web.exception;

import cool.zgq.component.common.common.BaseResponse;
import cool.zgq.component.common.common.ErrorCode;
import cool.zgq.component.common.common.ResultUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


/**
 * @Author GQ <href="http://www.zgq.cool"/>
 * @Date 2023 07/24 12:20
 * @Description 全局异常处理
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public BaseResponse runTimeExceptionHandler(RuntimeException e){
        log.error("runTimeException:"+e.getMessage(),e);
        return ResultUtils.error(ErrorCode.SYSTEM_ERROR, e.getMessage(), "系统异常");
    }

    @ExceptionHandler(BusinessException.class)
    public BaseResponse businessExceotionHandler(BusinessException e){
        log.error("businessException:"+e.getMessage(),e);
        return ResultUtils.error(e.getCode(),e.getMessage(),e.getDescription());
    }
}
