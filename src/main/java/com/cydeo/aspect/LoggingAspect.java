package com.cydeo.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.keycloak.adapters.springsecurity.account.SimpleKeycloakAccount;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j // this is the most usable in the market
public class LoggingAspect {

   // Logger logger = LoggerFactory.getLogger(LoggingAspect.class); this is same as->@Slf4j from Lombok

    private String getUsername() { //If you put these 3 lines

       Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // you are going to be able to get the logged-in users' username from okay
       SimpleKeycloakAccount userDetails = (SimpleKeycloakAccount) authentication.getDetails();
       return userDetails.getKeycloakSecurityContext().getToken().getPreferredUsername();
   }/*
        Because i'm going to use this same getUserName() in different
point cuts or not Okay, so that's why i'm going to create one method
         */

    @Pointcut("execution(* com.cydeo.controller.ProjectController.*(..)) || execution(* com.cydeo.controller.TaskController.*(..))")
    public void anyProjectAndTaskControllerPC() {}

    @Before("anyProjectAndTaskControllerPC()")
    public void beforeAnyProjectAndTaskControllerAdvice(JoinPoint joinPoint) {
       /*
       I want to see this information. Okay.Who is trying to do this operation?
        This is the first thing I want to see. And second thing is. what is this operation? Okay.
        /*
        I want to see
which method is running at the time and which user is trying to run that method. Okay.
and let's give the information.
         */
        log.info("Before -> Method: {}, User: {}"
                , joinPoint.getSignature().toShortString()
                , getUsername());
        /*
        Because i'm going to use this same getUserName() in different
point cuts or not Okay, so that's why i'm going to create one method
         */
    }

    @AfterReturning(pointcut = "anyProjectAndTaskControllerPC()", returning = "results")
    public void afterReturningAnyProjectAndTaskControllerAdvice(JoinPoint joinPoint, Object results) {
        log.info("After Returning -> Method: {}, User: {}, Results: {}"
                , joinPoint.getSignature().toShortString()
                , getUsername()
                , results.toString());
    }

    @AfterThrowing(pointcut = "anyProjectAndTaskControllerPC()", throwing = "exception")
    public void afterReturningAnyProjectAndTaskControllerAdvice(JoinPoint joinPoint, Exception exception) {
        log.info("After Returning -> Method: {}, User: {}, Results: {}"
                , joinPoint.getSignature().toShortString()
                , getUsername()
                , exception.getMessage());
    }

}
