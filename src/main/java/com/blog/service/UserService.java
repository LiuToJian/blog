package com.blog.service;

import com.blog.dao.UserDao;
import com.blog.entity.User;
import com.blog.util.MD5Util;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserServiceDao{
    @Autowired
    UserDao userDao;

    @Override
    public User checkUser(String username, String password) {
        return userDao.findByUsernameAndPassword(username, MD5Util.code(password));
    }

    @Override
    public User saveUser(User user) {
        return null;
    }

    @Override
    public User getUser(Integer id) {
        return null;
    }

    @Override
    public Page<User> listUser(Pageable pageable) {
        return null;
    }

    @Override
    public User updateUser(Integer id, User user) throws NotFoundException {
        return null;
    }

    @Override
    public void deleteUser(Integer id) {

    }

    @Override
    public User getUserByName(String username) {
        return null;
    }
}
