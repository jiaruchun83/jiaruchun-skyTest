package jiaruchun.cangqiongservice.mapper;

import jiaruchun.pojo.admin.entity.Dish;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface DishMapper {

    @Options(useGeneratedKeys = true, keyProperty = "id")
    @Insert("insert into dish (name, category_id, price, image, description, status, create_time, update_time, create_user, update_user) values (#{name}, #{categoryId}, #{price}, #{image}, #{description}, #{status}, now(), now(), #{createUser}, #{updateUser})")
    void addDish(Dish dish);

    void upDish(Dish dish);

    List<Dish> reAll(Integer categoryId, String name, Integer status);

    @Select("select * from dish where id = #{id}")
    Dish ByIDSelect(Long id);

    void delete(List<Long> ids);

    @Select("select * from dish where category_id = #{categoryId}")
    List<Dish> ByCategoryId(Integer categoryId);

    @Update("update dish set status = #{status} ,update_user = #{aLong} ,update_time = now() where id = #{id}")
    void upStatus(Integer status, Long id, Long aLong);
}
