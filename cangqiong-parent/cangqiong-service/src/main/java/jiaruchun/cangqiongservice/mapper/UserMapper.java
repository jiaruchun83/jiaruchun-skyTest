package jiaruchun.cangqiongservice.mapper;

import jiaruchun.pojo.user.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDate;

@Mapper
public interface UserMapper {

    @Select("select * from user where openid = #{openid}")
    User ByOpenId(String openid);

    @Options(useGeneratedKeys = true, keyProperty = "id")
    @Insert("insert into user (openid,create_time) values (#{openid},now())")
    void addUser(User user);

    @Select("select * from user where id = #{userId}")
    User reById(Long userId);

    @Select("select count(*) from user where create_time >= date_sub(now(), interval 1 day)")
    Integer getNewUser();

    //当日新增用户数量，用逗号分隔
    String getNewUserList(LocalDate begin, LocalDate end);

    //当日总用户数量，用逗号分隔
    @Select("""
            WITH RECURSIVE date_series AS (
                SELECT DATE('2025-05-01') AS day
                UNION ALL
                SELECT DATE_ADD(day, INTERVAL 1 DAY)
                FROM date_series
                WHERE day < DATE('2025-06-06')
            )
            SELECT GROUP_CONCAT(
                       (SELECT COUNT(*) FROM user u WHERE DATE(u.create_time) <= ds.day)
                       ORDER BY ds.day
                       SEPARATOR ','
                   ) AS cumulative_user_counts
            FROM date_series ds""")
    String getTotalUserList(LocalDate begin, LocalDate end);

    //begin到end的总新增用户数量
    @Select("""
            SELECT COUNT(*) 
            FROM user 
            WHERE DATE(create_time) BETWEEN #{begin} AND #{end}""")
    Integer getAllNewUserCount(LocalDate begin, LocalDate end);
}
