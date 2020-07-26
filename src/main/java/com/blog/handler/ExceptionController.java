package com.blog.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.nio.channels.SeekableByteChannel;

/**
 * 统一异常处理控制类
 *
 * @ControllerAdvice：会拦截所有的请求
 */
@ControllerAdvice
public class ExceptionController {
    /*使用日志记录*/
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @ExceptionHandler(Exception.class)
    public ModelAndView exceptionHandler(HttpServletRequest request, Exception e) {
       /*记录错误，{}有占位符的作用*/
        logger.error("Request URL : {},Exception : {}",request.getRequestURI(),e);
       ModelAndView mv=new ModelAndView();
       /*添加异常路径*/
       mv.addObject("url",request.getRequestURI());
       /*添加异常信息*/
       mv.addObject("exception",e);
      /*设置跳转页面*/
       mv.setViewName("error/error");
      return mv;
    }
}
