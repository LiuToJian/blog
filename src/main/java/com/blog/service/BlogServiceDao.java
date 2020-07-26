package com.blog.service;

import com.blog.entity.Blog;
import com.blog.vo.BlogQuery;
import javassist.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BlogServiceDao {
    Blog getBlog(Integer id);
    List<Blog> listRecommendBlogTop(Integer size);
    Page<Blog>listBlog(Pageable pageable, BlogQuery blog);
    Page<Blog>listBlog(Pageable pageable);
    Page<Blog>listBlog(String query,Pageable pageable);
    Blog saveBlog(Blog blog);
    Blog updateBlog(Integer id,Blog blog) throws NotFoundException;
    void deleteBlog(Integer id);

    Blog getAndConvert(Integer id) throws NotFoundException;
}
