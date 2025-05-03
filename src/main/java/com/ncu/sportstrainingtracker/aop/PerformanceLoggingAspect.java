package com.ncu.sportstrainingtracker.aop;

import com.ncu.sportstrainingtracker.entity.PerformanceRecord;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class PerformanceLoggingAspect {

    // 1. Log before adding a performance record
    @Before("execution(* com.ncu.sportstrainingtracker.service.PerformanceRecordService.createPerformanceRecord(..))")
    public void logBeforeAddingRecord(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        Long athleteId = (Long) args[0];
        PerformanceRecord record = (PerformanceRecord) args[1];
        log.info("Incoming record for Athlete ID {} - Metric: {}, Value: {}", athleteId, record.getMetric(), record.getValue());
    }

    // 2. Log after successfully adding a performance record
    @AfterReturning(
            pointcut = "execution(* com.ncu.sportstrainingtracker.service.PerformanceRecordService.createPerformanceRecord(..))",
            returning = "result"
    )
    public void logAfterCreatingRecord(JoinPoint joinPoint, Object result) {
        PerformanceRecord saved = (PerformanceRecord) result;
        log.info("New record added: Athlete ID {}, Metric: {}, Value: {}, Remarks: {}",
                saved.getAthlete().getId(), saved.getMetric(), saved.getValue(), saved.getRemarks());
    }

    // 3. Log exceptions thrown anywhere in the service layer
    @AfterThrowing(pointcut = "execution(* com.ncu.sportstrainingtracker.service..*(..))", throwing = "ex")
    public void logServiceExceptions(Exception ex) {
        log.error("Exception in service layer: {}", ex.getMessage());
    }

    // 4. Measure and log execution time
    @Around("execution(* com.ncu.sportstrainingtracker.service.PerformanceRecordService.createPerformanceRecord(..))")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        long timeTaken = System.currentTimeMillis() - start;
        log.info("Execution time for createPerformanceRecord: {} ms", timeTaken);
        return result;
    }
}
