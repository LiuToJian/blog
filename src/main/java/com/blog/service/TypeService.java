package com.blog.service;

import com.blog.dao.TypeDao;
import com.blog.entity.Type;
import javassist.NotFoundException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TypeService implements TypeServiceDao {
    @Autowired
    TypeDao typeDao;

    @Override
    @Transactional
    public Type saveType(Type type) {
        return typeDao.save(type);
    }

    @Override
    @Transactional
    public Type getType(Integer id) {
        return typeDao.getOne(id);
    }

    @Override
    @Transactional
    public Page<Type> listType(Pageable pageable) {
        return typeDao.findAll(pageable);
    }

    @Override
    public List<Type> listType() {
        return typeDao.findAll();
    }

    @Override
    public List<Type> listType(Integer size) {
        return typeDao.findTop( PageRequest.of(0,size,Sort.by(Sort.Direction.DESC,"blogs.size")));
    }

    @Override
    @Transactional
    public Type updateType(Integer id, Type type) throws NotFoundException {
        Type t = typeDao.getOne(id);
        if (t == null) {
            throw new NotFoundException("不存在该类型");
        }
        BeanUtils.copyProperties(type, t);
        return typeDao.save(t);
    }

    @Override
    @Transactional
    public void deleteType(Integer id) {
        typeDao.deleteById(id);
    }

    @Override
    @Transactional
    public Type getTypeByName(String username) {
        return typeDao.findByName(username);
    }
}
