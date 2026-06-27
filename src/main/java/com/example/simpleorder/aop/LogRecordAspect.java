package com.example.simpleorder.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Aspect
@Slf4j
@Component
public class LogRecordAspect {
    @Around("@annotation(logRecord)")
    public Object around(ProceedingJoinPoint joinPoint, LogRecord logRecord) throws Throwable {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        String className = methodSignature.getDeclaringType().getSimpleName();
        String methodName = methodSignature.getMethod().getName();
        Object result;
        Object[] args = joinPoint.getArgs();
        // Slf4j 日志
        log.info("aop日志:[开始]{}.{},参数{}", className, methodName, args);
        // 记录开始时间，用于计算执行时间
        Long startTime = System.currentTimeMillis();

        try{
            result = joinPoint.proceed();
        }
        catch (Throwable throwable){
            log.error("aop日志:[异常]{}.{},抛出信息{}", className, methodName, throwable.getMessage());
            throw throwable;
        }
        Long executeTime = System.currentTimeMillis() - startTime;
        log.info("aop日志:[结束]{}.{},耗时{}，返回{}", className, methodName, executeTime, result);
        return result;
    }
}
