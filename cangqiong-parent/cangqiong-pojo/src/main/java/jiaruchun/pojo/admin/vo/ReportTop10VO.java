package jiaruchun.pojo.admin.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/*查询销量排名top10接口*/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReportTop10VO {
    private String nameList;//商品名称列表,用逗号分隔
    private String numberList;//销量列表,用逗号分隔
}
