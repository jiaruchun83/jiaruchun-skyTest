package jiaruchun.cangqiongservice.service.user;

import jiaruchun.cangqiongservice.mapper.DishMapper;
import jiaruchun.cangqiongservice.mapper.SetmealDishMapper;
import jiaruchun.pojo.admin.entity.Dish;
import jiaruchun.pojo.admin.entity.SetMealDish;
import jiaruchun.pojo.user.vo.ListBysetmealIdVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
@Slf4j
@Service
public class ListBySetmealIdService {

    @Autowired
    private SetmealDishMapper setmealDishMapper;

    @Autowired
    private DishMapper dishMapper;

    /*/*1根据套餐id查询包含的菜品*/
    @Cacheable(cacheNames = "dishes_setmealId",key = "#id")
    public List<ListBysetmealIdVO> listBySetmealId(Integer id) {
        List<SetMealDish> setMealDishes = setmealDishMapper.ById(Long.valueOf(id));
        return setMealDishes.stream().map(setMealDish -> {
            Dish dish = dishMapper.ByIDSelect(setMealDish.getDishId());
            return new ListBysetmealIdVO(setMealDish.getCopies(), dish.getDescription(), dish.getName(), setMealDish.getName());
        }).toList();
    }
}
