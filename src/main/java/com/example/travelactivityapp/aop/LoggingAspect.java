package com.example.travelactivityapp.aop;

/* This class serves as a service layer that handles business logic
* related to activities with this app. Specifically, it handles:
* - managing activities for CRUD methods
* - data interaction with the DB via IActivityRepository to perform CRUD on Activity entity
* - transaction management ensuring that DB ops are handled within transaction context
* to maintain data integrity and consistency  */

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

@Aspect // Declares this class as an aspect for AOP
@Slf4j // Creates a log object for logging messages.
public class LoggingAspect {

    // @B defines method to run before execution of methods matching given pointcut expression
    @Before("execution(* com.example.travelactivityapp..*.*(..))")
    public void logMethodInvocation(JoinPoint joinPoint) { // Method logs invocation of a method
        log.info("Method invoked: " + joinPoint.getSignature().toShortString()); // Provides short description of method signature.
    }

    // @AR triggered after a method successfully returns. Result is captured in 'result'
    @AfterReturning(pointcut = "execution(* com.example.travelactivityapp..*.*(..))", returning = "result")
    public void logSuccessfulExecution(JoinPoint joinPoint, Object result) {
        log.info("Method executed successfully: " + joinPoint.getSignature().toShortString());
    }

    // @AT triggered if method throws exception. Logs occurrence of exception, including method sig and exception msg.
    @AfterThrowing(pointcut = "execution(* com.example.travelactivityapp..*.*(..))", throwing = "exception")
    public void logException(JoinPoint joinPoint, Exception exception) {
        log.error("Exception in method: " + joinPoint.getSignature().toShortString() + " - " + exception.getMessage());
    }
}