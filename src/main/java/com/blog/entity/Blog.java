package com.blog.entity;

import lombok.Data;
import org.thymeleaf.expression.Ids;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "t_blog")
public class Blog {
    @Id
    @GeneratedValue
    private Integer id;
    private String title;
    private String content;
    private String firstPicture;
    private String flag;
    private Integer views;
    /*是否开启赞赏*/
    private Boolean appreciation;
    @Transient
    private String tagids;
    private Boolean shareStatement;
    private Boolean commentTabled;
    private Boolean published;
    private Boolean recommend;
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateTime;
    @ManyToOne
    private Type type;

    private String description;


    /*级联新增*/
    @ManyToMany(cascade = {CascadeType.PERSIST})
    private List<Tag> tags = new ArrayList<>();

    @ManyToOne
    private User user;

    @OneToMany(mappedBy = "blog")
    private List<Comment> comments=new ArrayList<>();


    public void init(){
        this.tagids=tagsToIds(this.getTags());
    }

    private String tagsToIds(List<Tag> tags){
        if(!tags.isEmpty()){
            StringBuffer ids=new StringBuffer();
            boolean flag=false;
            for(Tag tag:tags){
                if(flag){
                    ids.append(",");
                }else{
                    flag=true;
                }
                ids.append(tag.getId());
            }
            return ids.toString();
        }else {
            return tagids;
        }
    }
}
