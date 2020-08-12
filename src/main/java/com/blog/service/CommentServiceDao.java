package com.blog.service;

import com.blog.entity.Comment;

import java.util.List;

public interface CommentServiceDao {
    List<Comment> listCommentByBlogId(Integer blogId);
    Comment saveComment(Comment comment);
}
