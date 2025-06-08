package jiaruchun.pojo.admin.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/*查询菜品总览返回的对象*/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OverviewDishesVO {
    private Integer sold;//已售菜品数量
    private Integer discontinued;//停售菜品数量
}
