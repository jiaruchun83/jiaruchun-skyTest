package jiaruchun.pojo.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
* 订单支付接受对象
* */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderPayDTO {
    private String orderNumber;//订单号
    private Integer payMethod;
}
