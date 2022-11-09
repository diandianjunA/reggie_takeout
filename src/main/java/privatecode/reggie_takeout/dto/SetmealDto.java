package privatecode.reggie_takeout.dto;

import lombok.Data;
import privatecode.reggie_takeout.entity.Setmeal;
import privatecode.reggie_takeout.entity.SetmealDish;

import java.util.List;

@Data
public class SetmealDto extends Setmeal {

    private List<SetmealDish> setmealDishes;

    private String categoryName;
}
