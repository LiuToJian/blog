package com.blog.service;

import com.blog.entity.Tag;
import javassist.NotFoundException;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TagServiceDao {
    Tag saveTag(Tag tag);
List<Tag> listTagTop(Integer size);
    List<Tag> listTags(String Ids);

    Tag getTag(Integer id);

    Page<Tag> listTag(Pageable pageable);

    List<Tag> listTag();

    Tag updateTag(Integer id, Tag tag) throws ChangeSetPersister.NotFoundException, NotFoundException;

    void deleteTag(Integer id);

    Tag getTagByName(String name);
}
