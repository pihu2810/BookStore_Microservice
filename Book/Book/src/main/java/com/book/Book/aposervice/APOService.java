package com.book.Book.aposervice;

import com.book.Book.exception.BookStoreException;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;


@Aspect
@Component
@Slf4j
public class APOService {
    //logs method before hitting API
    @Before("execution(* com.book.Book.controller.BookController.*(..))")     //point-cut expression
    public void logBeforeV1(JoinPoint joinPoint) {
        log.info("Initiating API : " + joinPoint.getSignature().getName() + " ");
    }

    //logs method After hitting API
    @After("execution(* com.book.Book.controller.BookController.*(..))")     //point-cut expression
    public void logAfter(JoinPoint joinPoint) {
        log.info("API successfully Executed : " + joinPoint.getSignature().getName() + " ");
    }
    //logs parameters After hitting API
    @After("execution(* com.book.Book.controller.BookController.*(..))")
    public void logAfterAndSaveArgs(JoinPoint joinPoint) {
        log.info("After : " + Arrays.toString(joinPoint.getArgs()));
    }

    //ExceptionHandling
    @Around(value = "execution(* com.book.Book.controller.BookController.*(..))")
    public Object customerexception(ProceedingJoinPoint joinPoint) throws Throwable {
        try {
            Object obj = joinPoint.proceed();
            return obj;
        } catch (BookStoreException exception) {
            log.info(" TaskException StatusCode  {}", exception.getHttpStatus().value());
            log.info("TaskException Message {}", exception.getMessage());
            throw new ResponseStatusException(exception.getHttpStatus(), exception.getMessage());
        }
    }
    //    TimeTaken
    @Around(value = "execution(* com.book.Book.controller.BookController.*(..))")
    public Object timeTracker(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime=System.currentTimeMillis();
        Object obj=joinPoint.proceed();
        long timeTaken=System.currentTimeMillis()-startTime;
        log.info("Time taken by {} is {}",joinPoint,timeTaken);
        return obj;
    }

}
