package com.blog.vo;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class BlogQuery {
    private String title;
    private Integer typeId;
    private Boolean recommend;
}
