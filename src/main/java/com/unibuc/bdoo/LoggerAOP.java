package com.unibuc.bdoo;


import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggerAOP {

    private Logger logger = LoggerFactory.getLogger(LoggerAOP.class);

    @Around("execution(* com.unibuc.bdoo..*(..))")
    public Object logAllMethodCalls(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        String methodSignature = proceedingJoinPoint.getTarget().getClass().getSimpleName() + "." + proceedingJoinPoint.getSignature().getName();
        logger.info(">>> Calling " + methodSignature);
        Object result = proceedingJoinPoint.proceed();
        logger.info("<<< Exiting " + methodSignature);
        return result;
    }
}
