package privatecode.reggie_takeout.mapper;

import org.springframework.stereotype.Repository;
import privatecode.reggie_takeout.entity.Dish;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
* @author 17305
* @description 针对表【dish(菜品管理)】的数据库操作Mapper
* @createDate 2022-11-01 10:22:54
* @Entity privatecode.reggie_takeout.entity.Dish
*/
@Repository
public interface DishMapper extends BaseMapper<Dish> {

}




