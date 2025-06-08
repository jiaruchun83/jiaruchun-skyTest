package jiaruchun.cangqiongservice.controller.user;

import jiaruchun.cangqiongservice.service.user.ListByCategoryService;
import jiaruchun.pojo.Result;
import jiaruchun.pojo.admin.entity.Dish;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
/*根据分类id查询菜品*/
@Slf4j
@RestController
@RequestMapping("/user/dish/list")
public class UserDishController {

    @Autowired
    private ListByCategoryService listByCategoryService;

    @GetMapping
    public Result listByCategory(@RequestParam Integer categoryId){
        log.info("categoryId:{}",categoryId);
        List<Dish> dishes = listByCategoryService.listByCategory(categoryId);
        return Result.success(dishes);
    }
}
