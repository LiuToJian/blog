package com.blog.service;

import com.blog.entity.User;
import javassist.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserServiceDao {
    User checkUser(String username,String password);
    User saveUser(User user);
    User getUser(Integer id);
    Page<User> listUser(Pageable pageable);
    User updateUser(Integer id,User user) throws NotFoundException;
    void deleteUser(Integer id);
    User getUserByName(String username);

}
