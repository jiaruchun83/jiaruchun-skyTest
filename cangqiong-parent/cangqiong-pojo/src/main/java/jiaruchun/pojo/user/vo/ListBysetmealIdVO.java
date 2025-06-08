package jiaruchun.pojo.user.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ListBysetmealIdVO implements Serializable {
    private Integer copies;
    private String description;
    private String image;
    private String name;
}
