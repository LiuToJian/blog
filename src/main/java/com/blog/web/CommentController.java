package com.blog.web;

import com.blog.entity.Comment;
import com.blog.entity.User;
import com.blog.service.BlogService;
import com.blog.service.CommentServiceDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;

@Controller
public class CommentController {
    @Autowired
    CommentServiceDao commentServiceDao;
    @Autowired
    BlogService blogService;
    @Value("${comment.avatar}")
    String avatar;

    @GetMapping("/comments/{blogId}")
    public String commentList(@PathVariable Integer blogId, Model model) {
        model.addAttribute("comments", commentServiceDao.listCommentByBlogId(blogId));
        return "blog::commentList";
    }

    @PostMapping("/comments")
    public String post(Comment comment, HttpSession session) {
        Integer blogId = comment.getBlog().getId();
        comment.setBlog(blogService.getBlog(blogId));
        User user = (User) session.getAttribute("user");
        if (user != null) {
            comment.setAvatar(user.getAvatar());
            comment.setAdminComment(true);
           /* comment.setNickname(user.getNickname());*/
        } else {
            comment.setAvatar(avatar);
        }
        commentServiceDao.saveComment(comment);
        return "redirect:/comments/" + blogId;
    }
}
