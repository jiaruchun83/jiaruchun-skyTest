package jiaruchun.cangqiongservice.service.user;

import jiaruchun.cangqiongservice.mapper.SetMealMapper;
import jiaruchun.pojo.user.vo.SetmealVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class SetmealListByCategoryService {

    @Autowired
    private SetMealMapper setMealMapper;

    /*根据分类id查询套餐*/
    @Cacheable(cacheNames = "setmeal-categoryId",key = "#categoryId")
    public List<SetmealVO> SetmealListByCategory(Integer categoryId) {
        List<SetmealVO> setMealVOS = setMealMapper.ByCategoryId(categoryId);
        log.info("setMeals:{}", setMealVOS);
        return setMealVOS;
    }
}
