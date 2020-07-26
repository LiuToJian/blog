package com.blog.dao;

import com.blog.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;


/*操作的对象和主键的类型*/
public interface UserDao extends JpaRepository<User,Integer> {
User findByUsernameAndPassword(String username,String password);
}
