package com.example.travelactivityapp.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

@Aspect
@Slf4j
public class LoggingAspect {

    @Before("execution(* com.example.travelactivityapp..*.*(..))")
    public void logMethodInvocation(JoinPoint joinPoint) {
        log.info("Method invoked: " + joinPoint.getSignature().toShortString());
    }

    @AfterReturning(pointcut = "execution(* com.example.travelactivityapp..*.*(..))", returning = "result")
    public void logSuccessfulExecution(JoinPoint joinPoint, Object result) {
        log.info("Method executed successfully: " + joinPoint.getSignature().toShortString());
    }

    @AfterThrowing(pointcut = "execution(* com.example.travelactivityapp..*.*(..))", throwing = "exception")
    public void logException(JoinPoint joinPoint, Exception exception) {
        log.error("Exception in method: " + joinPoint.getSignature().toShortString() + " - " + exception.getMessage());
    }
}