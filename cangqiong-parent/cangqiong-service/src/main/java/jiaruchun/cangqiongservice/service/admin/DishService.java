package jiaruchun.cangqiongservice.service.admin;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import jiaruchun.cangqiongservice.mapper.DishMapper;
import jiaruchun.cangqiongservice.mapper.FlavorMapper;
import jiaruchun.common.exce.DishNameException;
import jiaruchun.common.utils.ThreadLocalUtil;
import jiaruchun.pojo.admin.entity.Dish;
import jiaruchun.pojo.admin.entity.Flavor;
import jiaruchun.pojo.PageResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class DishService {

    @Autowired
    private DishMapper dishMapper;

    @Autowired
    private FlavorMapper flavorMapper;

    @CacheEvict(cacheNames = "dishes_categoryId",allEntries = true)
    public void addDish(Dish dish) {
        log.info("新增菜品：{}", dish);
        if(dish.getDescription() == null){
            dish.setDescription("暂无描述。");
        }

        dish.setCreateUser(ThreadLocalUtil.get());
        dish.setUpdateUser(ThreadLocalUtil.get());
        if(dish.getStatus() != null){
            dish.setStatus(1);
        }
        try {
            dishMapper.addDish(dish);
        } catch (Exception e) {
            throw new DishNameException("菜名重复，重新输入！");
        }
        log.info("dish:{}" ,dish);
        if(!dish.getFlavors().isEmpty()){
            List<Flavor> flavors = dish.getFlavors();
            for (Flavor flavor : flavors) {
                flavor.setDishId(dish.getId());
            }
            log.info("falcor:{}",dish.getFlavors());
            flavorMapper.addFlavors(dish.getFlavors());
        }

    }

    @CacheEvict(cacheNames = "dishes_categoryId",allEntries = true)
    public void upDish(Dish dish) {
        log.info("传进dish:{}",dish);
        dish.setUpdateUser(ThreadLocalUtil.get());
        if(!dish.getFlavors().isEmpty()){
            //删除,菜id
            if(!flavorMapper.ByIDSelect(dish.getId()).isEmpty()){
                flavorMapper.delete(dish.getId());
            }
            //插入
            for (Flavor flavor : dish.getFlavors()) {
                flavor.setDishId(dish.getId());
            }
            flavorMapper.addFlavors(dish.getFlavors());
        }
        log.info("dish:{}",dish);
        dishMapper.upDish(dish);
    }

    public PageResult<Dish> reAll(Integer categoryId, String name, Integer page, Integer pageSize, Integer status) {
        PageHelper.startPage(page,pageSize);
        List<Dish> dishes = dishMapper.reAll(categoryId, name, status);
        Page<Dish> dishes1 = (Page<Dish>) dishes;
        return new PageResult<>(dishes1.getTotal(),dishes1.getResult());
    }

    public Dish ByIDSelect(Long id) {
        Dish dish = dishMapper.ByIDSelect(id);
        log.info("dish:{}",dish);
        List<Flavor> flavors = flavorMapper.ByIDSelect(id);
        dish.setFlavors(flavors);
        log.info("dish:{}",dish);
        return dish;
    }

    @CacheEvict(cacheNames = "dishes_categoryId",allEntries = true)
    public void delete(List<Long> ids) {
        if (!ids.isEmpty()) {
            dishMapper.delete(ids);
        }
        flavorMapper.deleteIds(ids);
    }

    public List<Dish> ByCategoryId(Integer categoryId) {
        return dishMapper.ByCategoryId(categoryId);
    }

    @CacheEvict(cacheNames = "dishes_categoryId",allEntries = true)
    public void upStatus(Integer status, Long id) {
        dishMapper.upStatus(status,id,ThreadLocalUtil.get());
    }
}
