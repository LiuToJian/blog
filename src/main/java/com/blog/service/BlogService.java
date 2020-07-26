package com.blog.service;

import com.blog.dao.BlogDao;
import com.blog.entity.Blog;
import com.blog.entity.Type;
import com.blog.util.MarkDownUtil;
import com.blog.util.MyBeanUtils;
import com.blog.vo.BlogQuery;
import javassist.NotFoundException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class BlogService implements BlogServiceDao {
    @Autowired
    private BlogDao blogDao;

    @Override
    public Blog getBlog(Integer id) {
        return blogDao.getOne(id);
    }

    @Override
    public List<Blog> listRecommendBlogTop(Integer size) {

        return blogDao.findTop(PageRequest.of(0,size,Sort.by(Sort.Direction.DESC,"updateTime")));
    }

    @Override
    public Page<Blog> listBlog(Pageable pageable, BlogQuery blog) {
        return blogDao.findAll(new Specification<Blog>() {
            @Override
            public Predicate toPredicate(Root<Blog> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
               List<Predicate> predicates=new ArrayList<>();
               if(!"".equals(blog.getTitle())&&blog.getTitle()!=null){
                   predicates.add(cb.like(root.<String>get("title"),"%"+blog.getTitle()+"%"));
               }
               if(blog.getTypeId()!=null){
                   predicates.add(cb.equal(root.<Type>get("type").get("id"),blog.getTypeId()));
               }
               if(blog.getRecommend()!=null&& blog.getRecommend().equals("true")){
                   predicates.add(cb.equal(root.<Boolean>get("recommend"),blog.getRecommend()));
               }
               cq.where(predicates.toArray(new Predicate[predicates.size()]));
               return null;
            }
        },pageable);
    }

    @Override
    public Page<Blog> listBlog(Pageable pageable) {
        return blogDao.findAll(pageable);
    }

    @Override
    public Page<Blog> listBlog(String query, Pageable pageable) {
        return blogDao.findByQuery(query, pageable);
    }

    @Override
    @Transactional
    public Blog saveBlog(Blog blog) {
        if(blog.getId()==null){
            blog.setCreateTime(new Date());
            blog.setUpdateTime(new Date());
            blog.setViews(0);
        }else {
            blog.setUpdateTime(new Date());
        }
        return blogDao.save(blog);
    }
    @Transactional
    @Override
    public Blog updateBlog(Integer id, Blog blog) throws NotFoundException {
        Blog b = blogDao.getOne(id);
        if (b == null) {
            throw new NotFoundException("该博客不存在");
        }
        BeanUtils.copyProperties(blog,b, MyBeanUtils.getNullPropertyNames(blog));
        b.setUpdateTime(new Date());
        return blogDao.save(b);
    }
    @Transactional
    @Override
    public void deleteBlog(Integer id) {
        blogDao.deleteById(id);
    }

    @Transactional
    @Override
    public Blog getAndConvert(Integer id) throws NotFoundException{
        Blog blog=blogDao.getOne(id);
        if(blog==null){
            throw new NotFoundException("该博客不存在");
        }
        Blog b=new Blog();
        BeanUtils.copyProperties(blog,b);
        String content=b.getContent();
        b.setContent(MarkDownUtil.markdownToHtmlExtensions(content));
        return b;
    }


}
