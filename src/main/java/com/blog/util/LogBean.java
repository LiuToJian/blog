package com.blog.util;

import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class LogBean {
private String url;
private String ip;
private String classMethod;
private Object[] args;
}
