package com.blog.service;

import com.blog.entity.Type;
import javassist.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TypeServiceDao {
Type saveType(Type type);
Type getType(Integer id);
Page<Type> listType(Pageable pageable);
List<Type> listType();
List<Type>listType(Integer size);
Type updateType(Integer id,Type type) throws NotFoundException;
void deleteType(Integer id);
Type getTypeByName(String username);
}
