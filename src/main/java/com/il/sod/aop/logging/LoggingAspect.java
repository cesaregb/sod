package com.il.sod.aop.logging;

import java.util.Arrays;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class LoggingAspect {
	private final Logger LOGGER = LoggerFactory.getLogger(LoggingAspect.class);
	
	@Pointcut("within(com.il.sod.rest.api.impl..*) || within(com.il.sod.services..*)")
	public void loggingPointcut() {
		System.out.println("====== Within the pointcut");
	}

	@AfterThrowing(pointcut = "loggingPointcut()", throwing = "e")
	public void logAfterThrowing(JoinPoint joinPoint, Throwable e) {
		LOGGER.error("Exception in {}.{}() with cause = {}", 
				joinPoint.getSignature().getDeclaringTypeName(),
				joinPoint.getSignature().getName(), 
				e.getCause());
	}

	@After("execution(* com.il.sod.rest.api.impl.Health.*(..))")
	public void log(JoinPoint point) {
		LOGGER.info("====>" + point.getSignature().getName() + " called...");
	}
	
	@Around("loggingPointcut()")
	public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
		System.out.println("****** Into the logAround method!!!!!");
		
		LOGGER.info("Enter: {}.{}() with argument[s] = {}", 
				joinPoint.getSignature().getDeclaringTypeName(),
				joinPoint.getSignature().getName(), 
				Arrays.toString(joinPoint.getArgs()));
		if (LOGGER.isDebugEnabled()) {
		}
		try {
			Object result = joinPoint.proceed();
			if (LOGGER.isDebugEnabled()) {
				LOGGER.info("Exit: {}.{}() with result = {}", 
						joinPoint.getSignature().getDeclaringTypeName(),
						joinPoint.getSignature().getName(), 
						result);
			}
			return result;
		} catch (IllegalArgumentException e) {
			LOGGER.error("Illegal argument: {} in {}.{}()", 
					Arrays.toString(joinPoint.getArgs()),
					joinPoint.getSignature().getDeclaringTypeName(), 
					joinPoint.getSignature().getName());

			throw e;
		}
	}

}