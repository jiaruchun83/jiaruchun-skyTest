package jiaruchun.cangqiongservice.mapper;

import jiaruchun.pojo.admin.entity.Category;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CategoryMapper {

    @Insert("insert into category (type, name, sort, status, create_time, update_time, create_user, update_user) " +
                           "values (#{type}, #{name}, #{sort}, #{status}, now(), now(), #{createUser}, #{updateUser});")
    void addCategory(Category category);

    @Update("update category set status = #{status},update_time = now() where id = #{id}")
    void upStatus(Integer status, Long id);

    List<Category> reAll(String name, Integer type);

    @Update("update category set name = #{name},sort = #{sort} ,type = #{type},update_user = #{updateUser},update_time = now() where id = #{id}")
    void upCategory(Category category);

    @Delete("delete from category where id = #{id}")
    void delete(Long id);

    List<Category> ByType(Integer type);
}
