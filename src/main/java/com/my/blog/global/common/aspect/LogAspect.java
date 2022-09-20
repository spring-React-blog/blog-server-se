package com.my.blog.global.common.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

@Slf4j
@Aspect
public class LogAspect {
    @Before("@annotation(com.my.blog.global.common.aspect.Trace)")
    public void doTrace(JoinPoint joinPoint){
        Object[] args = joinPoint.getArgs();
        log.info("[trace] {} , arg = {} " , joinPoint.getSignature(), args);
    }
}
