package jiaruchun.cangqiongservice.mapper;


import jiaruchun.pojo.user.entity.AddressBook;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface AddressBookMapper {


    @Insert("insert into address_book (user_id,consignee,sex,phone,province_code,province_name,city_code,city_name,district_code,district_name,detail,label,is_default) " +
            "values (#{userId},#{consignee},#{sex},#{phone},#{provinceCode},#{provinceName},#{cityCode},#{cityName},#{districtCode},#{districtName},#{detail},#{label},#{isDefault})")
    void add(AddressBook addressBook);
    /*查询当前登录用户的所有地址信息*/
    @Select("select * from address_book where user_id = #{userId} order by is_default desc,id desc")
    List<AddressBook> reAll(Long userId);

    @Select("select * from address_book where user_id = #{userId} and is_default = 1")
    AddressBook reDefault(Long userId);

    @Update("update address_book set is_default = 1 where id = #{id}")
    void setDefault(Integer id);

    @Select("select * from address_book where id = #{id}")
    AddressBook reById(Integer id);

    void updateById(AddressBook addressBook);

    @Delete("delete from address_book where id = #{id}")
    void ByIdDelete(Integer id);

    @Update("update address_book set is_default = 0 where user_id = #{userId} and is_default = 1")
    void canselDefault(Long userId);
}
