package com.blog.entity;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "t_type")
public class Type {
    @Id
    @GeneratedValue
    private Integer id;
    /*验证这个字段
    * 只能作用在string上，不能为null，而且调用trim方法后，长度必须大于0
    * */
    @NotBlank(message = "分类名称不能为空")
    private String name;
    @OneToMany(mappedBy = "type")
    private List<Blog> blogs = new ArrayList<>();


}
