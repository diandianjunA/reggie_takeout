package privatecode.reggie_takeout.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import privatecode.reggie_takeout.common.R;
import privatecode.reggie_takeout.dto.SetmealDto;
import privatecode.reggie_takeout.entity.Setmeal;
import privatecode.reggie_takeout.entity.SetmealDish;
import privatecode.reggie_takeout.exception.CustomException;
import privatecode.reggie_takeout.service.SetmealDishService;
import privatecode.reggie_takeout.service.SetmealService;
import privatecode.reggie_takeout.mapper.SetmealMapper;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author 17305
* @description 针对表【setmeal(套餐)】的数据库操作Service实现
* @createDate 2022-11-01 10:23:00
*/
@Service
public class SetmealServiceImpl extends ServiceImpl<SetmealMapper, Setmeal>
    implements SetmealService{

    private final SetmealDishService setmealDishService;

    @Autowired
    public SetmealServiceImpl(SetmealDishService setmealDishService) {
        this.setmealDishService = setmealDishService;
    }

    /**
     * 新增套餐，同时需要保存套餐与菜品的关联关系
     * @param setmealDto 浏览器传来的数据
     */
    @Override
    @Transactional
    public void saveWithDish(SetmealDto setmealDto) {
        //保存套餐的基本信息
        save(setmealDto);
        //保存套餐与菜品的关联关系
        for (SetmealDish setmealDish : setmealDto.getSetmealDishes()) {
            setmealDish.setSetmealId(setmealDto.getId());
        }
        setmealDishService.saveBatch(setmealDto.getSetmealDishes());
    }

    /**
     * 根据id删除套餐以及它对应的菜品关系
     * @param ids 要删除的套餐的id
     */
    @Override
    public void removeWithDish(List<Long> ids) {
        //查询套餐状态，判断是否可删除
        LambdaQueryWrapper<Setmeal> setmealLambdaQueryWrapper = new LambdaQueryWrapper<>();
        setmealLambdaQueryWrapper.in(Setmeal::getId,ids);
        setmealLambdaQueryWrapper.eq(Setmeal::getStatus,1);
        long count = count(setmealLambdaQueryWrapper);
        if(count>0){
            throw new CustomException("套餐正在售卖中，无法删除");
        }
        removeBatchByIds(ids);
        LambdaQueryWrapper<SetmealDish> setmealDishLambdaQueryWrapper = new LambdaQueryWrapper<>();
        setmealDishLambdaQueryWrapper.in(SetmealDish::getSetmealId,ids);
        setmealDishService.remove(setmealDishLambdaQueryWrapper);
    }


}




