package privatecode.reggie_takeout.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import privatecode.reggie_takeout.entity.DishFlavor;
import privatecode.reggie_takeout.service.DishFlavorService;
import privatecode.reggie_takeout.mapper.DishFlavorMapper;
import org.springframework.stereotype.Service;

/**
* @author 17305
* @description 针对表【dish_flavor(菜品口味关系表)】的数据库操作Service实现
* @createDate 2022-11-03 14:48:00
*/
@Service
public class DishFlavorServiceImpl extends ServiceImpl<DishFlavorMapper, DishFlavor>
    implements DishFlavorService{

}




