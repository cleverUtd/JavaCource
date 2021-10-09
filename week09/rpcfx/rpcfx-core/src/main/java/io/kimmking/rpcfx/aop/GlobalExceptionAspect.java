package io.kimmking.rpcfx.aop;

import io.kimmking.rpcfx.api.RpcfxResponse;
import io.kimmking.rpcfx.exception.RpcfxException;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Component
@Aspect
@Slf4j
public class GlobalExceptionAspect {

    @Around("@annotation(io.kimmking.rpcfx.aop.ExceptionHandler)")
    public Object invoke(final ProceedingJoinPoint pjp) {
        final RpcfxResponse response = new RpcfxResponse();
        try {
            log.info("[GlobalExceptionAspect] {}", pjp.toLongString());
            return pjp.proceed();
        } catch (final Throwable throwable) {
            throwable.printStackTrace();
            final RpcfxException rpcfxException = new RpcfxException("RpcfxInvoker#invoke", throwable);
            response.setException(rpcfxException);
            response.setStatus(false);
        }
        return response;
    }
}
