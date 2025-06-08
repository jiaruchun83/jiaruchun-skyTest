package jiaruchun.cangqiongservice.mapper;

import jiaruchun.pojo.user.entity.ShoppingCart;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ShoppingCartMapper {


    @Select("select * from shopping_cart where user_id = #{userId}")
    List<ShoppingCart> reShoppingCart(Long userId);
    //查指定菜数量
    @Select("select count(*) from shopping_cart where user_id = #{userId} and dish_id = #{dishId}")
    Integer reDishCount(Long userId,Long dishId);
    //查指定套餐数量
    @Select("select count(*) from shopping_cart where user_id = #{userId} and setmeal_id = #{setmealId}")
    Integer reSetmealCount(Long userId,Long setmealId);

    @Insert("insert into shopping_cart (name,image,user_id,dish_id,setmeal_id,dish_flavor,number,amount,create_time)" +
                        " values (#{name},#{image},#{userId},#{dishId},#{setmealId},#{dishFlavor},#{number},#{amount},now())")
    void addShoppingCart(ShoppingCart shoppingCart);

    void remove(ShoppingCart shoppingCart);

    @Delete("delete from shopping_cart where user_id = #{userId}")
    void removeAll(Long userId);

    @Update("update shopping_cart set number = number + 1 where user_id = #{userId} and dish_id = #{dishId}")
    void addDishCount(Long userId, Integer dishId);

    @Update("update shopping_cart set number = number + 1 where user_id = #{userId} and setmeal_id = #{setmealId}")
    void addSetmealCount(Long userId, Integer setmealId);

    @Update("update shopping_cart set number = number - 1 where user_id = #{userId} and dish_id = #{dishId}")
    void deDishCount(Long userId, Integer dishId);

    @Update("update shopping_cart set number = number - 1 where user_id = #{userId} and setmeal_id = #{setmealId}")
    void deSetmealCount(Long userId, Integer setmealId);

    @Delete("delete from shopping_cart where user_id = #{userId} and dish_id = #{dishId}")
    void removeAllDish(Long userId, Integer dishId);

    @Delete("delete from shopping_cart where user_id = #{userId} and setmeal_id = #{setmealId}")
    void removeAllSetmeall(Long userId, Integer setmealId);

    @Select("select dish_flavor from shopping_cart where user_id = #{aLong} and dish_id = #{dishId}")
    String reDishFlavor(Long aLong, Integer dishId);

    @Select("select number from shopping_cart where user_id = #{userId} and dish_id = #{dishId} and dish_flavor = #{dishFlavor}")
    Integer reDishCountFlavor(Long userId, Long dishId, String dishFlavor);

    @Select("select number from shopping_cart where user_id = #{userId} and dish_id = #{dishId} and dish_flavor IS NULL")
    Integer reDishCountNotFlavor(Long userId, Long dishId);

    @Update("update shopping_cart set number = number - 1 where user_id = #{userId} and dish_id = #{dishId} and dish_flavor = #{dishFlavor}")
    void deDishCountFlavor(Long userId, Integer dishId, String dishFlavor);

    @Delete("delete from shopping_cart where user_id = #{userId} and dish_id = #{dishId} and dish_flavor = #{dishFlavor}")
    void removeAllDishFlavor(Long userId, Integer dishId, String dishFlavor);

    @Select("select count(*) from shopping_cart where user_id = #{userId}")
    Integer reShoppingCartCount(Long userId);
}
