package privatecode.reggie_takeout.dto;


import lombok.Data;
import privatecode.reggie_takeout.entity.Dish;
import privatecode.reggie_takeout.entity.DishFlavor;

import java.util.ArrayList;
import java.util.List;

@Data
public class DishDto extends Dish {

    private List<DishFlavor> flavors = new ArrayList<>();

    private String categoryName;

    private Integer copies;
}
