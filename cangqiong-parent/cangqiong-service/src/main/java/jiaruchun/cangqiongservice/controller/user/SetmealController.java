package jiaruchun.cangqiongservice.controller.user;

import jiaruchun.cangqiongservice.service.user.ListBySetmealIdService;
import jiaruchun.cangqiongservice.service.user.SetmealListByCategoryService;
import jiaruchun.pojo.Result;
import jiaruchun.pojo.user.vo.ListBysetmealIdVO;
import jiaruchun.pojo.user.vo.SetmealVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/user/setmeal")
public class SetmealController {

    @Autowired
    private SetmealListByCategoryService setmealListByCategoryService;

    @Autowired
    private ListBySetmealIdService listBySetmealIdService;


    /*根据分类id查询套餐*/
    @GetMapping("/list")
    public Result SetmealListByCategory(@RequestParam Integer categoryId){
        List<SetmealVO> setMealVOS = setmealListByCategoryService.SetmealListByCategory(categoryId);
        log.info("setmealInfos:{}", setMealVOS);
        return Result.success(setMealVOS);
    }

    /*1根据套餐id查询包含的菜品*/
    @GetMapping("/dish/{id}")
    public Result listBySetmealId(@PathVariable Integer id){
        List<ListBysetmealIdVO> listBysetmealIdVOS = listBySetmealIdService.listBySetmealId(id);
        return Result.success(listBysetmealIdVOS);
    }
}
