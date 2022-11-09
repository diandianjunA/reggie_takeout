package privatecode.reggie_takeout.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import privatecode.reggie_takeout.dto.DishDto;
import privatecode.reggie_takeout.entity.Dish;
import privatecode.reggie_takeout.entity.DishFlavor;
import privatecode.reggie_takeout.service.DishFlavorService;
import privatecode.reggie_takeout.service.DishService;
import privatecode.reggie_takeout.mapper.DishMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
* @author 17305
* @description 针对表【dish(菜品管理)】的数据库操作Service实现
* @createDate 2022-11-01 10:22:54
*/
@Service
@Transactional
public class DishServiceImpl extends ServiceImpl<DishMapper, Dish>
    implements DishService{

    private final DishFlavorService dishFlavorService;

    @Autowired
    public DishServiceImpl(DishFlavorService dishFlavorService) {
        this.dishFlavorService = dishFlavorService;
    }

    /**
     * 将菜品存入数据库中，同时将其对应的口味也存入数据库中，在这之前先将菜品的id赋给其对应的口味
     * @param dishDto 浏览器传来的总数据
     */
    @Override
    public void saveWithFlavor(DishDto dishDto) {
        //先将菜品的内容存进数据库中
        save(dishDto);
        //将Flavor存入Flavor表中
        //先把当前菜品的id赋给flavor
        Long dishId = dishDto.getId();
        for (DishFlavor flavor : dishDto.getFlavors()) {
            flavor.setDishId(dishId);
        }
        //调用FlavorService将这些Flavor存入数据库
        dishFlavorService.saveBatch(dishDto.getFlavors());
    }

    @Override
    public DishDto getByIdWithFlavor(Long id) {
        //查询菜品基本信息
        Dish dish = getById(id);
        //查询菜品口味信息
        LambdaQueryWrapper<DishFlavor> dishFlavorLambdaQueryWrapper = new LambdaQueryWrapper<>();
        dishFlavorLambdaQueryWrapper.eq(DishFlavor::getDishId,id);
        List<DishFlavor> list = dishFlavorService.list(dishFlavorLambdaQueryWrapper);
        //创建DishDto对象，将数据存进去
        DishDto dishDto = new DishDto();
        BeanUtils.copyProperties(dish,dishDto);
        dishDto.setFlavors(list);
        return dishDto;
    }

    @Override
    @Transactional
    public void updateWithFlavor(DishDto dishDto) {
        //更新dish表的相关信息
        updateById(dishDto);
        //清理当前菜品对应的所有口味信息
        LambdaQueryWrapper<DishFlavor> dishFlavorLambdaQueryWrapper = new LambdaQueryWrapper<>();
        dishFlavorLambdaQueryWrapper.eq(DishFlavor::getDishId,dishDto.getId());
        dishFlavorService.remove(dishFlavorLambdaQueryWrapper);
        //添加提交过来的所有口味信息
        for (DishFlavor flavor : dishDto.getFlavors()) {
            flavor.setDishId(dishDto.getId());
        }
        dishFlavorService.saveBatch(dishDto.getFlavors());
    }

    /**
     * 浏览器通过get方式传来的参数是一串字符串，要将这串字符串转换成多个Long型的id
     * @param ids 浏览器传来的多个id的字符串
     * @return 处理后得到的Long型id的集合
     */
    @Override
    public List<Long> getIds(String ids) {
        ArrayList<Long> res = new ArrayList<>();
        for (String s : ids.split(",")) {
            res.add(Long.parseLong(s));
        }
        return res;
    }
}




