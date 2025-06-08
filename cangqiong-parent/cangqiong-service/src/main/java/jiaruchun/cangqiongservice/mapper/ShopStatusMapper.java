package jiaruchun.cangqiongservice.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface ShopStatusMapper {

    @Select("select status from shop")
    Integer getStatus();

    @Update("update shop set status = #{status}")
    void setStatus(Integer status);
}
