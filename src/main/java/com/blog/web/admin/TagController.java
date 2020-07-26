package com.blog.web.admin;

import com.blog.entity.Tag;
import com.blog.service.TagService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/supper")
public class TagController {

   @Autowired
    TagService tagService;

    /*自动封装*/
    /*把查出来的数据进行ID倒序排序*/
    @GetMapping("/tags")
    public String tags(@PageableDefault(size = 4, sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable, Model model) {
        model.addAttribute("page", tagService.listTag(pageable));
        return "admin/tags";
    }

    @GetMapping("/tags/input")
    public String input(Model model) {
        model.addAttribute("tag", new Tag());
        return "admin/tags-input";
    }

    /*BindingResult result：可以接收验证的结果*/
    @PostMapping("/tags")
    public String post(@Valid Tag tag, BindingResult result, RedirectAttributes attributes) {
        /*校验数据库中是否已存在相同的类型名称*/
        if (tagService.getTagByName(tag.getName()) != null) {
            result.rejectValue("name", "nameError", "该标签名称已存在！");
        }
        if (result.hasErrors()) {
            return "admin/tags-input";
        }
        Tag t = tagService.saveTag(tag);
        if (t == null) {
            attributes.addFlashAttribute("message", "新增失败");
        } else {
            attributes.addFlashAttribute("message", "新增成功");
        }
        return "redirect:/supper/tags";
    }

    @GetMapping("/tags/{id}/input")
    public String editInput(@PathVariable Integer id, Model model) {
        model.addAttribute("tag", tagService.getTag(id));
        return "admin/tags-input";
    }

    /*BindingResult result：可以接收验证的结果*/
    @PostMapping("/tags/{id}")
    public String editPost(@Valid Tag tag, BindingResult result, @PathVariable(value = "id") Integer id, RedirectAttributes attributes) throws NotFoundException, ChangeSetPersister.NotFoundException {
        /*校验数据库中是否已存在相同的类型名称*/
        if (tagService.getTagByName(tag.getName()) != null) {
            result.rejectValue("name", "nameError", "该分类名称已存在！");
        }
        if (result.hasErrors()) {
            return "admin/tags-input";
        }
        Tag t = tagService.updateTag(id, tag);
        if (t == null) {
            attributes.addFlashAttribute("message", "更新失败");
        } else {
            attributes.addFlashAttribute("message", "更新成功");
        }
        return "redirect:/supper/tags";
    }


    @GetMapping("/tags/{id}/delete")
    public String delete(@PathVariable(value = "id") Integer id,RedirectAttributes a){
        tagService.deleteTag(id);
        a.addFlashAttribute("message", "删除成功");
        return "redirect:/supper/tags";
    }
}
