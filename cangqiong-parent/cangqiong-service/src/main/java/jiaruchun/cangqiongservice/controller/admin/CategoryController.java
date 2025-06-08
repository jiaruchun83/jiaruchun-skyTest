package jiaruchun.cangqiongservice.controller.admin;

import jiaruchun.cangqiongservice.service.admin.CategoryService;
import jiaruchun.pojo.admin.entity.Category;
import jiaruchun.pojo.PageResult;
import jiaruchun.pojo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping
    public Result addCategory(@RequestBody Category category){
        categoryService.addCategory(category);
        return Result.success();
    }

    @PostMapping("/status/{status}")
    public Result upStatus(@PathVariable Integer status,Long id){
        categoryService.upStatus(status,id);
        return Result.success();
    }

    @GetMapping("/page")
    public Result reAll(@RequestParam(required = false, value = "name") String name,
                        @RequestParam(required = false , value = "type") Integer type,
                        @RequestParam(defaultValue = "1") Integer page,
                        @RequestParam(defaultValue = "10") Integer pageSize){
        PageResult<Category> categoryPageResult = categoryService.reAll(name, type, page, pageSize);
        return Result.success(categoryPageResult);
    }

    @PutMapping
    public Result upCategory(@RequestBody Category category){
        categoryService.upCategory(category);
        return Result.success();
    }

    @DeleteMapping
    public Result delete(Long id){
        categoryService.delete(id);
        return Result.success();
    }

    @GetMapping("/list")
    public Result ByType(@RequestParam(required = false) Integer type){
        List<Category> categories = categoryService.ByType(type);
        return Result.success(categories);
    }

}
