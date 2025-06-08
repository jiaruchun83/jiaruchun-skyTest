package jiaruchun.pojo.admin.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/*查询订单管理数据，返回的对象*/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OverviewOrdersVO {
    private Integer allOrders;//总订单数
    private Integer cancelledOrders;//已取消订单数
    private Integer completedOrders;//已完成订单数
    private Integer deliveredOrders;//已派送订单数
    private Integer waitingOrders;//待接单订单数
}
