package privatecode.reggie_takeout.service;

import org.springframework.beans.factory.annotation.Autowired;
import privatecode.reggie_takeout.dto.DishDto;
import privatecode.reggie_takeout.entity.Dish;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author 17305
* @description 针对表【dish(菜品管理)】的数据库操作Service
* @createDate 2022-11-01 10:22:54
*/
public interface DishService extends IService<Dish> {
    void saveWithFlavor(DishDto dishDto);

    DishDto getByIdWithFlavor(Long id);

    void updateWithFlavor(DishDto dishDto);

    List<Long> getIds(String ids);
}
