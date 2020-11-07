package com.sanitas.calculator.util.aspect;

import com.sanitas.calculator.util.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.EmptyStackException;

import static com.sanitas.calculator.util.Constants.*;
import static java.util.Arrays.asList;
import static java.util.stream.Collectors.joining;

/**
 * @autor FabianSR
 */
@Component
@Aspect
public class AspectControlLogger {

    @Pointcut("execution(* com.sanitas.calculator.service.impl.*.*(..))")
    public void serviceClassMethods() {
    }

    @Pointcut("execution(public * com.sanitas.calculator.controller.*.*(..))")
    public void controllerClassMethods() {
    }

    /**
     * Add here other factories
     */
    @Pointcut("execution(* com.sanitas.calculator.model.domain.integer.*.*(..)) ||" +
            "execution(* com.sanitas.calculator.model.domain.bigdecimalexmaple.*.*(..))")
    public void fabricationMethods() {
    }

    @Around("serviceClassMethods() || controllerClassMethods()")
    public Object controlEntryAndExistBusinessMethods(final ProceedingJoinPoint joinPoint) {
        Object response;
        try {
            response = printLog("business method : %s; input : %s ; response: %s", joinPoint);
        } catch (NumberFormatException | EmptyStackException e) {
            Logger.getLoggerInstance().getTracer().trace(e);
            response = e instanceof NumberFormatException ? NOT_NUMERIC_OPERAND : EXPRESSION_IS_NOT_COMPLETE;
        } catch (Throwable t) {
            Logger.getLoggerInstance().getTracer().trace(t);;
            response = ERROR;
        }
        return response;
    }

    @Around("fabricationMethods()")
    public Object printFabricationMethdos(final ProceedingJoinPoint joinPoint) throws Throwable {
        return printLog("fabrication method: %s input token : %s ; instance created: %s", joinPoint);
    }

    private Object printLog(final String message, final ProceedingJoinPoint joinPoint) throws Throwable {
        final Object response = joinPoint.proceed();
        Logger.getLoggerInstance().getTracer().trace(String.format(message, joinPoint.getSignature().getName(), asList(joinPoint.getArgs()).stream().map(Object::toString).collect(joining()), response));
        return response;
    }
}