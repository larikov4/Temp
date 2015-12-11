package com.epam.hw1.service.aspect;

import com.google.common.base.Joiner;
import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

/**
 * Logging methods invoking (method name, arguments and exec time).
 *
 * @author Yevhen_Larikov
 */
@Component
@Aspect
public class MethodLogger {
    private static Logger LOG = Logger.getLogger(MethodLogger.class);

    /**
     * Logging info about methods invocations in Dao and Controllers.
     *
     * @param point the join point
     * @return result of method invocation
     * @throws Throwable any Throwable that method will throw
     */
    @Around("execution(* com.epam.hw1.dao.*.*(..)) || execution(* com.epam.hw1.web.controller.*.*(..))")
    public Object logAroundMethod(ProceedingJoinPoint point) throws Throwable {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        Object result = point.proceed();
        stopWatch.stop();

        if (LOG.isDebugEnabled()) {
            LOG.debug(getMethodName(point)
                    .append(" execution time: ")
                    .append(stopWatch.getTotalTimeMillis())
                    .append(" ms"));
        }
        return result;
    }

    private StringBuffer getMethodName(ProceedingJoinPoint point) {
        StringBuffer logMessage = new StringBuffer();
        return logMessage.append(point.getTarget().getClass().getName())
                .append(".")
                .append(point.getSignature().getName())
                .append("(")
                .append(Joiner.on(",").useForNull("null").join(point.getArgs()))
                .append(")");
    }
}