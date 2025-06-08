package jiaruchun.cangqiongservice.controller.admin;

import jiaruchun.cangqiongservice.service.admin.SetMealService;
import jiaruchun.pojo.PageResult;
import jiaruchun.pojo.Result;
import jiaruchun.pojo.admin.entity.SetMeal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/admin/setmeal")
public class SetMealController {
    @Autowired
    private SetMealService setMealService;

    @PostMapping
    public Result addSetMeal(@RequestBody SetMeal setMeal){
        setMealService.addSetMeal(setMeal);
        return Result.success();
    }

    @GetMapping("/page")
    public Result reAll(@RequestParam(required = false) Integer categoryId,
                        @RequestParam(required = false) String name,
                        @RequestParam(defaultValue = "1") Integer page,
                        @RequestParam(defaultValue = "10") Integer pageSize,
                        @RequestParam(required = false) Integer status){
        PageResult<SetMeal> setMealPageResult = setMealService.reAll(categoryId, name, page, pageSize, status);
        return Result.success(setMealPageResult);
    }

    @PutMapping
    public Result upSetMeal(@RequestBody SetMeal setMeal){
        setMealService.upSetMeal(setMeal);
        return Result.success();
    }

    @GetMapping("/{id}")
    public Result ById(@PathVariable Long id){
        SetMeal setMeal = setMealService.ById(id);
        return Result.success(setMeal);
    }

    @PostMapping("/status/{status}")
    public Result upStatus(@PathVariable Integer status,Long id){
        setMealService.upStatus(status,id);
        return Result.success();
    }

    @DeleteMapping
    public Result delete(@RequestParam List<Long> ids){
            setMealService.delete(ids);
            return Result.success();
    }
}
