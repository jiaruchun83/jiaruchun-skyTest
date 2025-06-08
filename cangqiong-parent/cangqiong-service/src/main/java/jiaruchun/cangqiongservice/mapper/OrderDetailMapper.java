package jiaruchun.cangqiongservice.mapper;

import jiaruchun.pojo.user.entity.OrderDetail;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDate;
import java.util.List;

@Mapper
public interface OrderDetailMapper {

    @Select("select * from order_detail where order_id = #{id}")
    List<OrderDetail> getOrderDetails(Long id);

    void add(List<OrderDetail> orderDetailList);

    //查询在begin和end时间内销量排名top10的商品名称,用逗号分隔
    @Select("SELECT\n" +
            "    GROUP_CONCAT(sub.name ORDER BY sub.total_sales DESC SEPARATOR ',') AS top10_products\n" +
            "FROM (\n" +
            "    SELECT\n" +
            "        od.name,\n" +
            "        SUM(od.number) AS total_sales\n" +
            "    FROM\n" +
            "        orders o\n" +
            "    JOIN\n" +
            "        order_detail od ON o.id = od.order_id\n" +
            "    WHERE\n" +
            "        o.status = 5  -- 只统计已完成订单\n" +
            "        AND o.order_time BETWEEN #{begin} AND #{end}" +
            "    GROUP BY\n" +
            "        od.name\n" +
            "    ORDER BY\n" +
            "        total_sales DESC\n" +
            "    LIMIT 10\n" +
            ") AS sub")
    String getTop10Name(LocalDate begin, LocalDate end);
    //查询begin和end时间内销量排名top10的商品数量,用逗号分隔
    @Select("""
            SELECT
                GROUP_CONCAT(sub.total_sales ORDER BY sub.total_sales DESC SEPARATOR ',') AS top10_sales_only
            FROM (
                SELECT
                    SUM(od.number) AS total_sales
                FROM
                    orders o
                JOIN
                    order_detail od ON o.id = od.order_id
                WHERE
                    o.status = 5
                    AND o.order_time BETWEEN #{begin} AND #{end}
                GROUP BY
                    od.name
                ORDER BY
                    total_sales DESC
                LIMIT 10
            ) AS sub""")
    String getTop10Number(LocalDate begin, LocalDate end);
}
