package com.blog.web.admin;

import com.blog.dao.TypeDao;
import com.blog.entity.Type;
import com.blog.service.TypeService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
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
public class TypeController {

    @Autowired
    TypeService typeService;

    /*自动封装*/
    /*把查出来的数据进行ID倒序排序*/
    @GetMapping("/types")
    public String types(@PageableDefault(size = 4, sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable, Model model) {
        model.addAttribute("page", typeService.listType(pageable));
        return "admin/types";
    }

    @GetMapping("/input")
    public String input(Model model) {
        model.addAttribute("type", new Type());
        return "admin/types-input";
    }

    /*BindingResult result：可以接收验证的结果*/
    @PostMapping("/types")
    public String post(@Valid Type type, BindingResult result, RedirectAttributes attributes) {
        /*校验数据库中是否已存在相同的类型名称*/
        if (typeService.getTypeByName(type.getName()) != null) {
            result.rejectValue("name", "nameError", "该分类名称已存在！");
        }
        if (result.hasErrors()) {
            return "admin/types-input";
        }
        Type t = typeService.saveType(type);
        if (t == null) {
            attributes.addFlashAttribute("message", "新增失败");
        } else {
            attributes.addFlashAttribute("message", "新增成功");
        }
        return "redirect:/supper/types";
    }

    @GetMapping("/types/{id}/input")
    public String editInput(@PathVariable Integer id, Model model) {
        model.addAttribute("type", typeService.getType(id));
        return "admin/types-input";
    }

    /*BindingResult result：可以接收验证的结果*/
    @PostMapping("/types/{id}")
    public String editPost(@Valid Type type, BindingResult result, @PathVariable(value = "id") Integer id, RedirectAttributes attributes) throws NotFoundException {
        /*校验数据库中是否已存在相同的类型名称*/
        if (typeService.getTypeByName(type.getName()) != null) {
            result.rejectValue("name", "nameError", "该分类名称已存在！");
        }
        if (result.hasErrors()) {
            return "admin/types-input";
        }
        Type t = typeService.updateType(id, type);
        if (t == null) {
            attributes.addFlashAttribute("message", "更新失败");
        } else {
            attributes.addFlashAttribute("message", "更新成功");
        }
        return "redirect:/supper/types";
    }


    @GetMapping("/types/{id}/delete")
    public String delete(@PathVariable(value = "id") Integer id,RedirectAttributes a){
        typeService.deleteType(id);
        a.addFlashAttribute("message", "删除成功");
        return "redirect:/supper/types";
    }
}
