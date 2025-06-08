package jiaruchun.pojo.user.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddressBook  implements Serializable {
    private Long id;
    private Long userId;
    private String consignee;//收货人
    private String sex;
    private String phone;
    private String provinceCode;//省份编码
    private String provinceName;//省份名称
    private String cityCode;//城市编码
    private String cityName;//城市名称
    private String districtCode;//区县编码
    private String districtName;//区县名称
    private String detail;//详细地址
    private String label;//标签，家，学校，公司
    private Integer isDefault;//是否默认地址1,0
}