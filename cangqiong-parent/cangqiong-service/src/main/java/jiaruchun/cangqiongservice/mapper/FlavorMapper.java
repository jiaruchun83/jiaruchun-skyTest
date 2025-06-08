package jiaruchun.cangqiongservice.mapper;

import jiaruchun.pojo.admin.entity.Flavor;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface FlavorMapper {

    void addFlavors(List<Flavor> flavors);

    @Delete("delete from dish_flavor where dish_id = #{id}")
    void delete(Long id);

    @Select("select * from dish_flavor where dish_id = #{id}")
    List<Flavor> ByIDSelect(Long id);

    void deleteIds(List<Long> ids);
}
