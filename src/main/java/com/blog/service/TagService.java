package com.blog.service;

import com.blog.dao.TagDao;
import com.blog.entity.Tag;
import javassist.NotFoundException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class TagService implements TagServiceDao {
    @Autowired
    TagDao tagDao;

    @Override
    public Tag saveTag(Tag tag) {
        return tagDao.save(tag);
    }

    @Override
    public List<Tag> listTagTop(Integer size) {

        return tagDao.findTop( PageRequest.of(0,size,Sort.by(Sort.Direction.DESC,"blogs.size")));
    }

    @Override
    public List<Tag> listTags(String Ids) {
       return tagDao.findAllById(convertToList(Ids));
    }
/*将一个字符串类型的数组转换成一个list集合*/
    private List<Integer> convertToList(String ids){
        List<Integer> list=new ArrayList<>();
        if(!"".equals(ids)&&ids!=null){
            String[] idarray=ids.split(",");
            for(int i=0;i<idarray.length;i++){
                list.add(new Integer(idarray[i]));
            }
        }
        return list;
    }

    @Override
    public Tag getTag(Integer id) {
        return tagDao.getOne(id);
    }

    @Override
    public Page<Tag> listTag(Pageable pageable) {
        return tagDao.findAll(pageable);
    }

    @Override
    public List<Tag> listTag() {
        return tagDao.findAll();
    }

    @Override
    public Tag updateTag(Integer id, Tag tag) throws NotFoundException {
        Tag t = tagDao.getOne(id);
        if (t == null) {
            throw new NotFoundException("不存在该类型");
        }
        BeanUtils.copyProperties(tag, t);
        return tagDao.save(t);
    }

    @Override
    public void deleteTag(Integer id) {
        tagDao.deleteById(id);
    }

    @Override
    public Tag getTagByName(String name) {
        return tagDao.findByName(name);
    }
}
