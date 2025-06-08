package jiaruchun.cangqiongservice.mapper;

import jiaruchun.pojo.admin.entity.SetMealDish;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface SetmealDishMapper {

    void add(List<SetMealDish> setMealDishes);

    @Delete("delete from setmeal_dish where setmeal_id = #{id}")
    void delete(Long id);

    @Select("select * from setmeal_dish where setmeal_id = #{id}")
    List<SetMealDish> ById(Long id);

    void deletes(List<Long> ids);
}
