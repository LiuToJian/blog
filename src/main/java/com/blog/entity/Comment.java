package com.blog.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "t_comment")
public class Comment {
    @Id
    @GeneratedValue
    private Integer id;
    private String nickname;
    private String email;
    private String avatar;
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;
    @ManyToOne
    private Blog blog;
    @OneToMany(mappedBy = "replyComment")
    private List<Comment>  patentComment= new ArrayList<>();
    @ManyToOne
    private Comment replyComment;
}
