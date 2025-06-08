package jiaruchun.cangqiongservice.service.admin;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import jiaruchun.cangqiongservice.mapper.SetMealMapper;
import jiaruchun.cangqiongservice.mapper.SetmealDishMapper;
import jiaruchun.common.exce.DeleteIdsIsEmpty;
import jiaruchun.common.exce.SetMealNameErrorException;
import jiaruchun.common.utils.ThreadLocalUtil;
import jiaruchun.pojo.PageResult;
import jiaruchun.pojo.admin.entity.SetMeal;
import jiaruchun.pojo.admin.entity.SetMealDish;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class SetMealService {

    @Autowired
    private SetMealMapper setMealMapper;

    @Autowired
    private SetmealDishMapper setmealDishMapper;

    @CacheEvict(cacheNames = "setmeal-categoryId",allEntries = true)
    public void addSetMeal(SetMeal setMeal) {
        //补充
        setMeal.setCreateUser(ThreadLocalUtil.get());
        setMeal.setUpdateUser(ThreadLocalUtil.get());
        setMeal.setStatus(1);
        //插入基本
        try {
            setMealMapper.add(setMeal);
        } catch (Exception e) {
            throw new SetMealNameErrorException("套餐名重复，请重新输入！");
        }
        //插菜数组
            if(!setMeal.getSetmealDishes().isEmpty()){
                for (SetMealDish setmealDish : setMeal.getSetmealDishes()) {
                    setmealDish.setSetmealId(setMeal.getId());
                }
                setmealDishMapper.add(setMeal.getSetmealDishes());
            }
    }

    public PageResult<SetMeal> reAll(Integer categoryId, String name, Integer page, Integer pageSize, Integer status) {
        PageHelper.startPage(page, pageSize);
        List<SetMeal> setMeals = setMealMapper.reAll(categoryId, name, status);
        Page<SetMeal> setMealPage = (Page<SetMeal>) setMeals;
        return new PageResult<>(setMealPage.getTotal(), setMealPage.getResult());
    }

    @CacheEvict(cacheNames = "setmeal-categoryId",allEntries = true)
    public void upSetMeal(SetMeal setMeal) {
        //更新基础
        setMeal.setUpdateUser(ThreadLocalUtil.get());
        setMealMapper.upSetmeal(setMeal);
        //更新菜数组
          //删除
        setmealDishMapper.delete(setMeal.getId());
         //插入
        setmealDishMapper.add(setMeal.getSetmealDishes());

    }

    public SetMeal ById(Long id) {
        SetMeal setMeal = setMealMapper.ById(id);
        List<SetMealDish> setMealDishes = setmealDishMapper.ById(id);
        setMeal.setSetmealDishes(setMealDishes);
        return setMeal;
    }

    @CacheEvict(cacheNames = "setmeal-categoryId",allEntries = true)
    public void upStatus(Integer status,Long id) {
        setMealMapper.upStatus(status,id,ThreadLocalUtil.get());
    }

    @CacheEvict(cacheNames = "setmeal-categoryId",allEntries = true)
    public void delete(List<Long> ids) {
        try {
            setmealDishMapper.deletes(ids);
            setMealMapper.deletes(ids);
        } catch (Exception e) {
            throw new DeleteIdsIsEmpty("请选择要删除的套餐");
        }
    }
}
