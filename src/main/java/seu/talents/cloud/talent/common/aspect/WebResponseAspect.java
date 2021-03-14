package seu.talents.cloud.talent.common.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import seu.talents.cloud.talent.model.dto.Response;

@Component
@Aspect
public class WebResponseAspect {
    @Pointcut("@within(seu.talents.cloud.talent.common.annotation.WebResponse)")
    private void responseCut() { }

    @Around("responseCut()")
    public Object responseCut(ProceedingJoinPoint pjp)throws Throwable {
        Object obj = pjp.proceed();
        if(obj instanceof Response){
            return obj;
        }
        return Response.success(obj);

    }
}
