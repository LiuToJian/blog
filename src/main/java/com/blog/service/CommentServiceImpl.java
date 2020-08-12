package com.blog.service;

import com.blog.dao.CommentDao;
import com.blog.entity.Comment;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentServiceDao {
    @Autowired
    CommentDao commentDao;

    @Override
    public List<Comment> listCommentByBlogId(Integer blogId) {
        Sort sort = Sort.by("createTime");
        //拿到所有的评论数据
        List<Comment> comments = commentDao.getByBlogIdAndParentCommentNull(blogId, sort);


        return eachComment(comments);
    }

    @Override
    public Comment saveComment(Comment comment) {
        Integer parentCommentId = comment.getParentComment().getId();
        if (parentCommentId != -1) {
            comment.setParentComment(commentDao.getOne(parentCommentId));
        } else {
            comment.setParentComment(null);
        }
        comment.setCreateTime(new Date());
        return commentDao.save(comment);
    }
    /*存放迭代找出的所有子代的集合*/
    private List<Comment> tempReplys = new ArrayList<>();
    /*循环每个顶级的评论节点*/
    private List<Comment> eachComment(List<Comment> comments) {
        List<Comment> commentView = new ArrayList<>();
        for (Comment comment : comments) {
            Comment c = new Comment();
            BeanUtils.copyProperties(comment, c);
            commentView.add(c);
        }
        /*合并评论的各层子代到第一级子代集合中*/
        combineChildren(commentView);
        return commentView;
    }

    private void combineChildren(List<Comment> comments) {
        for (Comment comment : comments) {
            List<Comment> replys1 = comment.getReplyComment();
            for (Comment reply1 : replys1) {
                /*循环迭代，找出子代，存档在tempReplys中*/
                recursively(reply1);
            }
            /*修改顶级节点的reply集合为迭代处理后的集合*/
            comment.setReplyComment(tempReplys);
            /*清楚临时存放区*/
            tempReplys = new ArrayList<>();
        }
    }




    /**
     * 递归迭代
     *
     * @param comment 被迭代的对象
     */
    private void recursively(Comment comment) {
        tempReplys.add(comment);
        if (comment.getReplyComment().size() > 0) {
            List<Comment> replys = comment.getReplyComment();
            for (Comment reply : replys) {
                tempReplys.add(reply);
                if (reply.getReplyComment().size() > 0) {
                    recursively(reply);
                }
            }
        }
    }
}
