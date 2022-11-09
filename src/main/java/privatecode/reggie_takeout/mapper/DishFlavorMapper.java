package privatecode.reggie_takeout.mapper;

import org.springframework.stereotype.Repository;
import privatecode.reggie_takeout.entity.DishFlavor;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
* @author 17305
* @description 针对表【dish_flavor(菜品口味关系表)】的数据库操作Mapper
* @createDate 2022-11-03 14:48:00
* @Entity privatecode.reggie_takeout.entity.DishFlavor
*/
@Repository
public interface DishFlavorMapper extends BaseMapper<DishFlavor> {

}




