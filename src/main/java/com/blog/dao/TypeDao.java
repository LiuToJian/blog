package com.blog.dao;

import com.blog.entity.Type;
import com.blog.entity.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


/*操作的对象和主键的类型*/
public interface TypeDao extends JpaRepository<Type,Integer> {
    Type findByName(String name);
@Query("select t from Type t")
    List<Type> findTop(Pageable pageable);
}
