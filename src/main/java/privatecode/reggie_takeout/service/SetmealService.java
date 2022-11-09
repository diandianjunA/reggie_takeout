package privatecode.reggie_takeout.service;

import privatecode.reggie_takeout.dto.SetmealDto;
import privatecode.reggie_takeout.entity.Setmeal;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author 17305
* @description 针对表【setmeal(套餐)】的数据库操作Service
* @createDate 2022-11-01 10:23:00
*/
public interface SetmealService extends IService<Setmeal> {
    void saveWithDish(SetmealDto setmealDto);

    void removeWithDish(List<Long> ids);
}
