package com.aop;

import com.alibaba.fastjson.JSON;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * @author rtw
 * @since 2020/1/3
 */
@Aspect
@Component
public class LoggerAdvice {
    // 定义一个切面，也就是何处进行切面的切入，一下是表示注解了LoggerManage的方法都会进入切面
    @Pointcut(value = "@annotation(com.aop.LoggerManage)")
    public void pointCut(){}

    // value=“。。。”和直接使用切面异曲同工，不过我们直接@annotation(loggerManage)的话，就可以在切面的时候拿到注解数据啦。
    @Before(value = "pointCut() && @annotation(loggerManage)")
    public void addBeforeLogger(JoinPoint joinPoint, LoggerManage loggerManage) {
        System.out.println("GOGO");
    }

    @Around(value = "pointCut() && @annotation(loggerManage)")
    public void around(ProceedingJoinPoint pjp, LoggerManage loggerManage) throws  Throwable{
        System.out.println("[Aspect1] around advise 1");
        Object obj = pjp.proceed();
        System.out.println("[Aspect1] around advise2" + JSON.toJSONString(obj));
    }

    @AfterThrowing(value = "pointCut() && @annotation(loggerManage)")
    public void afterThrowing(JoinPoint joinPoint, LoggerManage loggerManage) {
        System.out.println("[Aspect1] afterThrowing advise");
    }


    // 单纯的和切面进行关联，
//    @Before(value = "pointCut()")
//    public void addBeforeLogger(JoinPoint joinPoint) {
//        System.out.println("GOGO2");
//    }
}
