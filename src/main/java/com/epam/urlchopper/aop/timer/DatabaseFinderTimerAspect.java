package com.epam.urlchopper.aop.timer;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Timer aspect to measure the execution time of the repository finder methods.
 */
@Component
@Aspect
public class DatabaseFinderTimerAspect {

    private Logger logger = LoggerFactory.getLogger(DatabaseFinderTimerAspect.class);

    /**
     * measures execution time.
     * @param joinPoint
     * @return
     */
    @Around("finderMethods()")
    public Object measureExecution(ProceedingJoinPoint joinPoint) {
        Object ret = null;
        try {
            long start = System.currentTimeMillis();
            Object[] args = joinPoint.getArgs();
            ret = joinPoint.proceed(args);
            long end = System.currentTimeMillis();
            logger.info(joinPoint.getSignature() + " ellapsed time [ms]:" + (end - start));
        } catch (Throwable e) {
            logger.error(e.getMessage());
        }
        return ret;
    }

    /**
     * Pointcut advises finder methods in repositories.
     */
    @Pointcut("execution(* com.epam.urlchopper.repository..*.find*(java.lang.String))")
    public void finderMethods() {
    }

}
