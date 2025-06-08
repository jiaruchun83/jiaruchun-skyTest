package jiaruchun.cangqiongservice.mapper;

import jiaruchun.pojo.admin.entity.SetMeal;
import jiaruchun.pojo.user.vo.SetmealVO;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface SetMealMapper {

    @Options(useGeneratedKeys = true, keyProperty = "id")
    @Insert("insert into setmeal (category_id, name, price, description, image, create_time, update_time, create_user, update_user)" +
            " VALUES (#{categoryId},#{name},#{price},#{description},#{image},now(),now(),#{createUser},#{updateUser})")
    void add(SetMeal setMeal);

    List<SetMeal> reAll(Integer categoryId, String name, Integer status);

    void upSetmeal(SetMeal setMeal);

    @Select("select * from setmeal where id = #{id}")
    SetMeal ById(Long id);

    @Update("update setmeal set status = #{status} ,update_user = #{aLong}, update_time = now() where id = #{id}")
    void upStatus(Integer status, Long id, Long aLong);

    void deletes(List<Long> ids);

    @Select("select * from setmeal where category_id = #{categoryId}")
    List<SetmealVO> ByCategoryId(Integer categoryId);

    @Select("select count(*) from setmeal where status = 0")
    Integer getDiscontinuedSetmeal();

    @Select("select count(*) from setmeal where status = 1")
    Integer getSoldSetmeal();

    @Select("select count(*) from dish where status = 0")
    Integer getDiscontinuedDish();

    @Select("select count(*) from dish where status = 1")
    Integer getSoldDish();
}
