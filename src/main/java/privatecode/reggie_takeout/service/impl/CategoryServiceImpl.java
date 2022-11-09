package privatecode.reggie_takeout.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import privatecode.reggie_takeout.entity.Category;
import privatecode.reggie_takeout.entity.Dish;
import privatecode.reggie_takeout.entity.Setmeal;
import privatecode.reggie_takeout.exception.CustomException;
import privatecode.reggie_takeout.service.CategoryService;
import privatecode.reggie_takeout.mapper.CategoryMapper;
import org.springframework.stereotype.Service;
import privatecode.reggie_takeout.service.DishService;
import privatecode.reggie_takeout.service.SetmealService;

import java.util.Map;

/**
* @author 17305
* @description 针对表【category(菜品及套餐分类)】的数据库操作Service实现
* @createDate 2022-10-31 19:30:24
*/
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category>
    implements CategoryService{

    private final DishService dishService;

    private final SetmealService setmealService;

    @Autowired
    public CategoryServiceImpl(DishService dishService, SetmealService setmealService) {
        this.dishService = dishService;
        this.setmealService = setmealService;
    }

    /**
     * 根据id来删除分类，但在删除前要先查询是否有菜品和套餐关联了该分类
     * @param id 要删除的分类的id
     */
    @Override
    public void remove(Long id) {
        //查询是否有菜品关联了此分类
        LambdaQueryWrapper<Dish> dishLambdaQueryWrapper = new LambdaQueryWrapper<Dish>();
        dishLambdaQueryWrapper.eq(Dish::getCategoryId,id);
        long count = dishService.count(dishLambdaQueryWrapper);
        if(count>0){
            throw new CustomException("删除失败，当前分类关联了菜品");
        }
        //查询是否有套餐关联了此分类
        LambdaQueryWrapper<Setmeal> setmealLambdaQueryWrapper = new LambdaQueryWrapper<>();
        setmealLambdaQueryWrapper.eq(Setmeal::getCategoryId,id);
        count = setmealService.count(setmealLambdaQueryWrapper);
        if(count>0){
            throw new CustomException("删除失败，当前分类关联了套餐");
        }
        removeById(id);
    }
}




