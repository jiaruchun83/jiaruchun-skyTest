package jiaruchun.cangqiongservice.mapper;

import jiaruchun.pojo.admin.entity.Employee;
import jiaruchun.pojo.admin.vo.AdminLoginVO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface EmployeeMapper {

    @Insert("insert into employee(name, username, password, phone, sex, id_number, status, create_time, update_time,creat_user,update_user) values (#{name}, #{username}, #{password}, #{phone}, #{sex}, #{idNumber}, #{status}, #{createTime}, #{updateTime},#{createUser},#{updateUser})")
    void addEmp(Employee employee);

    @Select("select username from employee where username = #{username} ")
    String loginUsername(AdminLoginVO adminLoginVO);

    List<Employee> reAll(String name);

    @Update("update employee set status = #{status},update_time = now(),update_user = #{updateUser} where id = #{id}")
    void upStatus(Integer status, Long id,Long updateUser);

    @Update("update employee set id_number = #{idNumber},name = #{name},phone = #{phone},sex = #{sex},username = #{username},update_time = now() ,update_user = #{updateUser} where id = #{id}")
    void upEmp(Employee employee);

    @Select("select * from employee where username = #{username} and password = #{password}")
    AdminLoginVO loginAll(AdminLoginVO adminLoginVO);

    @Select("select employee.status from employee where id = #{id}")
    Integer reStatus(Long id);
}
