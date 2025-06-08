package jiaruchun.pojo.admin.vo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/*套餐查询套餐总览接口返回的对象*/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OverviewSetmealsVO {
    private Integer discontinued;//停售套餐数量
    private Integer sold;//已售套餐数量
}
