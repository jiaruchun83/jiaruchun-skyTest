package jiaruchun.cangqiongservice.controller.admin;

import jiaruchun.cangqiongservice.service.admin.DishService;
import jiaruchun.pojo.admin.entity.Dish;
import jiaruchun.pojo.PageResult;
import jiaruchun.pojo.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/admin/dish")
public class DishController {

    @Autowired
    private DishService dishService;

    @PostMapping
    public Result addDish(@RequestBody Dish dish){
        dishService.addDish(dish);
        return Result.success();
    }

    @PutMapping
    public Result uoDish(@RequestBody Dish dish){
        log.info("传进的dish：{}",dish);
        dishService.upDish(dish);
        return Result.success();
    }

    @GetMapping("/page")
    public Result reAll(@RequestParam(required = false) Integer categoryId,
                        @RequestParam(required = false) String name,
                        @RequestParam(defaultValue = "1") Integer page,
                        @RequestParam(defaultValue = "10") Integer pageSize,
                        @RequestParam(required = false) Integer status){

        PageResult<Dish> dishPageResult = dishService.reAll(categoryId, name, page, pageSize, status);
        return Result.success(dishPageResult);
    }

    @GetMapping("/{id}")
    public Result ByIDSelect(@PathVariable Long id){
        Dish dish = dishService.ByIDSelect(id);
        return Result.success(dish);
    }

    @DeleteMapping
    public Result delete(@RequestParam("ids") List<Long> ids){
        dishService.delete(ids);
        return Result.success();
    }

    @GetMapping("/list")
    public Result ByCategoryId(Integer categoryId){
        List<Dish> dishes = dishService.ByCategoryId(categoryId);
        log.info("分类id查询");
        return Result.success(dishes);
    }

    @PostMapping("/status/{status}")
    public Result upStatus(@PathVariable Integer status,@RequestParam Long id){
        dishService.upStatus(status,id);
        return Result.success();
    }

}
