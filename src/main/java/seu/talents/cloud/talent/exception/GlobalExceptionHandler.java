package seu.talents.cloud.talent.exception;


import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import seu.talents.cloud.talent.common.CONST;
import seu.talents.cloud.talent.exception.BizException;
import seu.talents.cloud.talent.model.dto.Response;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(Throwable.class)
    @ResponseBody
    public Response handleException(Throwable e) {
        // 打印异常堆栈信息
        log.error(e.getMessage(), e);
        return new Response(CONST.FAIL_CODE, e.getMessage(), e.getStackTrace().toString());
    }

    @ExceptionHandler
    public Response bizException(BizException e) {
        log.warn("业务异常: " + e.getCauseReason() + "; ", e);
        return Response.error(e.getCauseCode(), e.getCauseReason());
    }
}
