package jiaruchun.pojo.user.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

/*订单中不重复的每一样菜的信息*/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetail implements Serializable {
    private Long id;
    private String name;
    private String image;
    private Long orderId;
    private Long DishId;
    private Long setmealId;
    private String dishFlavor;
    private Integer number;
    private BigDecimal amount;

}
