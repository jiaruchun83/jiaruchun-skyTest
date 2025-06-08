package jiaruchun.cangqiongservice.controller.user;


import jiaruchun.cangqiongservice.service.admin.CategoryService;
import jiaruchun.pojo.Result;
import jiaruchun.pojo.admin.entity.Category;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/user/category")
public class TypeController {

    @Autowired
    private CategoryService categoryService;

    /*条件查询*/
    @GetMapping("/list")
    public Result type(@RequestParam(required = false) Integer type){
        List<Category> categories = categoryService.ByType(type);
        return Result.success(categories);
    }
}
