package jiaruchun.cangqiongservice.mapper;

import jiaruchun.pojo.Order;
import jiaruchun.pojo.admin.dto.ExcellREportDetailDTO;
import jiaruchun.pojo.admin.dto.OrderCancelDTO;
import jiaruchun.pojo.admin.dto.OrderRejectionDTO;
import org.apache.ibatis.annotations.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Mapper
public interface OrderMapper {

    @Options(useGeneratedKeys = true,keyProperty = "id",keyColumn = "id")
    @Insert("INSERT INTO orders (" +
            "number, status, user_id, address_book_id, order_time, checkout_time, " +
            "pay_method, pay_status, amount, remark, phone, address, user_name, " +
            "consignee, cancel_reason, rejection_reason, cancel_time, " +
            "estimated_delivery_time, delivery_status, delivery_time, " +
            "pack_amount, tableware_number, tableware_status" +
            ") VALUES (" +
            "#{number}, #{status}, #{userId}, #{addressBookId}, #{orderTime}, #{checkoutTime}, " +
            "#{payMethod}, #{payStatus}, #{amount}, #{remark}, #{phone}, #{address}, #{userName}, " +
            "#{consignee}, #{cancelReason}, #{rejectionReason}, #{cancelTime}, " +
            "#{estimatedDeliveryTime}, #{deliveryStatus}, #{deliveryTime}, " +
            "#{packAmount}, #{tablewareNumber}, #{tablewareStatus}" +
            ")")
    void add(Order order);

    @Select("select * from orders where id = #{id}")
    Order getOrder(Long id);

    @Select("select * from orders where number = #{orderNumber}")
    Order getOrderByOrderNumber(String orderNumber);

    //支付成功后更新订单状态
    @Select("update orders set status = 2 , pay_status = 1,checkout_time = now()where number = #{orderNumber}")
    void upStatusOnPaged(String orderNumber);

    List<Order> getAllOrder(Integer status);

    //超出15分钟未支付，更新订单状态为取消
    @Update("update orders set status = 6, cancel_reason = '超时未支付', cancel_time = now() where status = 1 and order_time < now() - interval 15 minute")
    void upPaymentTimeoutOrders();

    List<Order> reAll(LocalTime beginTime, LocalTime endTime, Integer number, String phone, Integer status);

    @Select("select count(*) from orders where status = 3 ")
    Integer getconfirmedNumber();

    @Select("select count(*) from orders where status = 4 ")
    Integer getDeliveryNumber();

    @Select("select count(*) from orders where status = 2 ")
    Integer getToBeConfirmedNumber();

    @Select("select * from orders where id = #{id}")
    Order gteDEtails(Long id);

    @Update("update orders set status = 6,cancel_time = now(),cancel_reason = #{cancelReason} where id = #{id}")
    void cancel(OrderCancelDTO orderCancelDTO);

    @Update("update orders set status = 5 where id = #{id}")
    void complete(Long id);

    @Update("update orders set status = 6 ,rejection_reason = #{rejectionReason},cancel_time = now() where id =#{id}")
    void rejection(OrderRejectionDTO orderRejectionDTO);

    @Update("update orders set status = 3 where id = #{orderId}")
    void confirm(Long orderId);

    @Update("update orders set status = 4 where id = #{orderId}")
    void delivery(Long orderId);

    //获取当日订单完成率
    @Select("SELECT ROUND(IFNULL((SUM(IF(status = 5 AND DATE(order_time) = CURDATE(), 1, 0)) / COUNT(CASE WHEN DATE(order_time) = CURDATE() THEN 1 END)), 0), 2) AS order_complete_rate FROM orders")
    BigDecimal getOrderCompleteRate();

    //获取当日订单平均单价
    @Select("SELECT IFNULL(SUM(amount) / COUNT(*), 0) FROM orders WHERE status = 5 AND DATE(order_time) = CURDATE()")
    Double getUnitPrice();

    ////当日有效订单数
    @Select("select count(*) from orders where status = 5 and order_time >= date_sub(now(), interval 1 day)")
    Integer getValidOrderCount();

    //当日营业额，保留两位小数
    @Select("select round(IFNULL(sum(amount), 0), 2) from orders where status = 5 and order_time >= date_sub(now(), interval 1 day)")
    Double getTurnover();

    //获取当日全部订单数
    @Select("select count(*) from orders where order_time >= date_sub(now(), interval 1 day)")
    Integer getAllOrders();

    //获取当日取消订单数
    @Select("select count(*) from orders where status = 6 and order_time >= date_sub(now(), interval 1 day)")
    Integer getCancelledOrders();

    //获取当日完成订单数
    @Select("select count(*) from orders where status = 5 and order_time >= date_sub(now(), interval 1 day)")
    Integer getCompletedOrders();

    //获取当日待派送订单数
    @Select("select count(*) from orders where status = 3 and order_time >= date_sub(now(), interval 1 day)")
    Integer getDeliveredOrders();

    //获取当日待接单订单数
    @Select("select count(*) from orders where status = 2 and order_time >= date_sub(now(), interval 1 day)")
    Integer getWaitingOrders();

    //获取begin,end时间内每日营业额，用逗号分隔
    @Select("""
            SELECT
                GROUP_CONCAT(sub.turnover ORDER BY sub.order_date SEPARATOR ',') AS turnover_list
            FROM (
                SELECT
                    DATE(o.order_time) AS order_date,
                    SUM(o.amount) AS turnover
                FROM
                    orders o
                WHERE
                    o.status = 5  -- 只统计已完成订单
                    AND o.order_time BETWEEN #{begin} AND #{end}
                GROUP BY
                    DATE(o.order_time)
                ORDER BY
                    DATE(o.order_time)
            ) AS sub""")
    String getTurnoverList(LocalDate begin, LocalDate end);

    //从begin到end每日总订单数量，用逗号分隔
    @Select("""
            SELECT
                GROUP_CONCAT(sub.order_count ORDER BY sub.order_date SEPARATOR ',') AS order_count_list
            FROM (
                SELECT
                    DATE(o.order_time) AS order_date,
                    COUNT(o.id) AS order_count
                FROM
                    orders o
                WHERE
                    o.order_time BETWEEN #{begin} AND #{end}
                GROUP BY
                    DATE(o.order_time)
                ORDER BY
                    DATE(o.order_time)
            ) AS sub""")
    String getOrderCountList(LocalDate begin, LocalDate end);

    //从begin到end每日有效订单数量，用逗号分隔
    @Select("""
            SELECT
                GROUP_CONCAT(sub.valid_order_count ORDER BY sub.order_date SEPARATOR ',') AS valid_order_count_list
            FROM (
                SELECT
                    DATE(o.order_time) AS order_date,
                    COUNT(o.id) AS valid_order_count
                FROM
                    orders o
                WHERE
                    o.status = 5  -- 只统计已完成订单
                    AND o.order_time BETWEEN #{begin} AND #{end}
                GROUP BY
                    DATE(o.order_time)
                ORDER BY
                    DATE(o.order_time)
            ) AS sub""")
    String getValidOrderCountList(LocalDate begin, LocalDate end);

    //从begin到end总订单数量
    @Select("""
            SELECT
                COUNT(o.id)
            FROM
                orders o
            WHERE
                o.order_time BETWEEN #{begin} AND #{end}""")
    Integer getTotalOrderCount(LocalDate begin, LocalDate end);

    //从begin到end总有效订单数量
    @Select("""
            SELECT
                COUNT(o.id)
            FROM
                orders o
            WHERE
                o.status = 5  -- 只统计已完成订单
                AND o.order_time BETWEEN #{begin} AND #{end}""")
    Integer getAllValidOrderCount(LocalDate begin, LocalDate end);

    //从begin到end完成的订单总额
    @Select("""
            SELECT
                SUM(o.amount)
            FROM
                orders o
            WHERE
                o.status = 5
             AND    o.order_time BETWEEN #{begin} AND #{end}""")
    double getTotalRevenue(LocalDate begin, LocalDate end);

    //从begin到end的总有效订单数量
    @Select("""
            SELECT
                COUNT(o.id)
            FROM
                orders o
            WHERE
                o.status = 5
             AND    o.order_time BETWEEN #{begin} AND #{end}""")
    Integer getAllOrderNumber(LocalDate begin, LocalDate end);

    //从begin到end的平均客单价
    @Select("""
            SELECT
                IFNULL(SUM(o.amount) / COUNT(*), 0)
            FROM
                orders o
            WHERE
                o.status = 5
             AND    o.order_time BETWEEN #{begin} AND #{end}""")
    double getAvgOrder(LocalDate begin, LocalDate end);

    //excel导出
    List<ExcellREportDetailDTO> getDetailList(@Param("begin") LocalDate begin, @Param("end") LocalDate end);


    //他自己的订单更新，兼容之后的状态更新
    /*void update(Order order);*/
}
