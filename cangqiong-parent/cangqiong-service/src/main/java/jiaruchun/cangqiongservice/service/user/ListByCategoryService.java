package jiaruchun.cangqiongservice.service.user;

import jiaruchun.cangqiongservice.mapper.DishMapper;
import jiaruchun.cangqiongservice.mapper.FlavorMapper;
import jiaruchun.pojo.admin.entity.Dish;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class ListByCategoryService {

    @Autowired
    private DishMapper dishMapper;

    @Autowired
    private FlavorMapper flavorMapper;

    /*根据分类id查询菜品*/
    @Cacheable(cacheNames = "dishes_categoryId",key = "#categoryId")
    public List<Dish> listByCategory(Integer categoryId) {
        List<Dish> dishes = dishMapper.ByCategoryId(categoryId);
        for (Dish dish : dishes) {
            dish.setFlavors(flavorMapper.ByIDSelect(dish.getId()));
        }
        log.info("dishes:{}",dishes);
        return dishes;
    }
}