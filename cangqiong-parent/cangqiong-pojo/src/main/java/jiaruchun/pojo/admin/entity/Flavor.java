package jiaruchun.pojo.admin.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Flavor implements Serializable {
    private Long id;
    private Long  dishId;
    private String name;
    private String value;
}
