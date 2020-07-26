package com.blog.aspect;

import com.blog.util.LogBean;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * 日志记录切面
 */
@Aspect
@Component
public class LogAspect {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    LogBean logBean;

    /*规定该切面需要拦截哪些包下的类*/
    /*任何类 路径 所有类 所有方法 所有参数*/
    @Pointcut("execution(* com.blog.web..*.*(..))")
    public void log() {

    }

    /*传入切面*/
    @Before("log()")
    public void doBefore(JoinPoint joinPoint) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        logBean.setIp(request.getRemoteAddr());
        logBean.setUrl(request.getRequestURI());
        /*获取方法返回值类型和方法名*/
        logBean.setClassMethod(joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
        logBean.setArgs(joinPoint.getArgs());
   logger.info("对象的值为："+logBean);
    }

    @After("log()")
    public void doAfter() {
        System.out.println(("==========doAfter========="));
    }

    /*拦截返回的内容*/
    @AfterReturning(returning = "result", pointcut = "log()")
    public void doAfterReturn(Object result) {
        logger.info("result:{}", result);
    }
}
