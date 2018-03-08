package com.kgisl.logback;

import java.util.Arrays;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.ProceedingJoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


@Aspect
@Component
public class TodoAspect {
    //private static final Logger LOGGER = LoggerFactory.getLogger(TodoAspect.class);
    Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Pointcut("execution(* com.kgisl.logback..*.*.*(..))")
    public void controller() {
    }

    @Pointcut("execution(* com.kgisl.logback..*.*.*(..))")
    protected void allMethod() {
    }

    @Before("execution(* com.kgisl.logback..*.*.*(..))")
    public void beforelog(JoinPoint point) {
        //LOGGER.debug("Class Name :  " + point.getSignature().getDeclaringTypeName()+" || "+" Method :  " + point.getSignature().getName()+" || "+" Arguments :  " + Arrays.toString(point.getArgs()));
        LOGGER.debug("Class Name :  " + point.getSignature().getDeclaringTypeName());
        LOGGER.info("Entering in Method :  " + point.getSignature().getName());
        LOGGER.warn("Argumentsttt :  " + Arrays.toString(point.getArgs()));
    }

    @AfterReturning(pointcut = "within(@org.springframework.stereotype.* *)", returning = "result")
    public void logAfterReturning(JoinPoint joinPoint, Object result) {
        LOGGER.info(" ###### Returning for class : {} ; Method : {} ", joinPoint.getTarget().getClass().getName(),
                joinPoint.getSignature().getName());
        if (result != null) {
            LOGGER.info(" ###### with value : {}", result.toString());
        } else {
            LOGGER.info(" ###### with null as return value.");
        }
    }

    @AfterThrowing(pointcut = "within(@org.springframework.stereotype.* *)", throwing = "exception")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable exception) {
        LOGGER.error("An exception has been thrown in " + joinPoint.getSignature().getName() + " ()");
        LOGGER.error("Cause : " + exception);
    }

    @Around("execution(* com.kgisl.logback..*.*.*(..))")
	public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {

		System.out.println("***********++++++++++----");
		//try {
			String className = joinPoint.getSignature().getDeclaringTypeName();
			String methodName = joinPoint.getSignature().getName();
			Object result = joinPoint.proceed();
		 
			LOGGER.debug("********" + className + "++++++++++++++" + methodName  );
		
			return result;
	/*	} catch (IllegalArgumentException e) {
			LOGGER.error("Illegal argument " + Arrays.toString(joinPoint.getArgs()) + " in "
					+ joinPoint.getSignature().getName() + "()");
			throw e;
		}*/
	}

  

}