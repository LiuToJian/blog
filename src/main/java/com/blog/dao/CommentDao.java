package com.blog.dao;

import com.blog.entity.Comment;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentDao  extends JpaRepository<Comment,Integer> {
   /* List<Comment> findByBlogId(Integer id, Sort sort);*/
    List<Comment> getByBlogIdAndParentCommentNull(Integer id, Sort sort);
}
