package jiaruchun.pojo.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShoppingCartDTO implements Serializable {
    private String dishFlavor;//口味
    private Integer dishId;//菜品id
    private Integer setmealId;//套餐id
}
