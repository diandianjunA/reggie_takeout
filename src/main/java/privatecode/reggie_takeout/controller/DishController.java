package privatecode.reggie_takeout.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import privatecode.reggie_takeout.common.R;
import privatecode.reggie_takeout.dto.DishDto;
import privatecode.reggie_takeout.entity.Category;
import privatecode.reggie_takeout.entity.Dish;
import privatecode.reggie_takeout.entity.DishFlavor;
import privatecode.reggie_takeout.service.CategoryService;
import privatecode.reggie_takeout.service.DishFlavorService;
import privatecode.reggie_takeout.service.DishService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/dish")
public class DishController {

    private final DishService dishService;
    private final CategoryService categoryService;
    private final DishFlavorService dishFlavorService;

    @Autowired
    public DishController(DishService dishService, CategoryService categoryService, DishFlavorService dishFlavorService) {
        this.dishService = dishService;
        this.categoryService = categoryService;
        this.dishFlavorService = dishFlavorService;
    }

    /**
     * 菜品分页查询
     * @param page 第几页
     * @param pageSize 每页多少条信息
     * @param name 模糊查询关键字
     * @return 分页信息
     */
    @GetMapping("/page")
    public R<Page<DishDto>> getPage(int page,int pageSize,String name){
        //先查到dish的数据
        Page<Dish> dishPage = new Page<Dish>(page,pageSize);
        Page<DishDto> dishDtoPage = new Page<>();
        LambdaQueryWrapper<Dish> dishLambdaQueryWrapper = new LambdaQueryWrapper<>();
        dishLambdaQueryWrapper.like(name!=null,Dish::getName,name);
        dishLambdaQueryWrapper.orderByDesc(Dish::getPrice);
        dishService.page(dishPage,dishLambdaQueryWrapper);
        //将dish的数据拷贝到dishDto中，但records不能拷贝过去，要自己填dishDto类型的数据
        BeanUtils.copyProperties(dishPage,dishDtoPage,"records");
        ArrayList<DishDto> dishDtoList = new ArrayList<>();
        //通过查询数据库，将每个dishDto的categoryName完善
        for (Dish dish : dishPage.getRecords()) {
            Long categoryId = dish.getCategoryId();
            Category category = categoryService.getById(categoryId);
            DishDto dishDto = new DishDto();
            BeanUtils.copyProperties(dish,dishDto);
            dishDto.setCategoryName(category.getName());
            dishDtoList.add(dishDto);
        }
        dishDtoPage.setRecords(dishDtoList);
        return R.success(dishDtoPage);
    }

    @PostMapping
    public R<String> save(@RequestBody DishDto dishDto){
        dishService.saveWithFlavor(dishDto);
        return R.success("保存成功");
    }

    /**
     * 根据id查询菜品信息和对应的口味信息
     * @param id 菜品id
     * @return 菜品数据
     */
    @GetMapping("/{id}")
    public R<DishDto> getInfo(@PathVariable Long id){
        DishDto dishDto = dishService.getByIdWithFlavor(id);
        return R.success(dishDto);
    }

    @PutMapping
    public R<String> update(@RequestBody DishDto dishDto){
        dishService.updateWithFlavor(dishDto);
        return R.success("修改成功");
    }

    /**
     * 批量起售停售
     * @param status 修改状态
     * @param ids 需要修改状态的菜品的id
     * @return 处理状态
     */
    @PostMapping("/status/{status}")
    public R<String> updateStatus(@PathVariable Integer status,String ids){
        for (Long id : dishService.getIds(ids)) {
            Dish dish = dishService.getById(id);
            dish.setStatus(status);
            dishService.updateById(dish);
        }
        return R.success("修改成功");
    }

    /**
     * 根据分类查询菜品信息
     * @param dish 分类id
     * @return 菜品信息
     */
//    @GetMapping("/list")
//    public R<List<Dish>> list(Dish dish){
//        LambdaQueryWrapper<Dish> dishLambdaQueryWrapper = new LambdaQueryWrapper<>();
//        dishLambdaQueryWrapper.eq(Dish::getCategoryId,dish.getCategoryId());
//        //只查询状态为1的，也就是正在售卖的菜品
//        dishLambdaQueryWrapper.eq(Dish::getStatus,1);
//        List<Dish> list = dishService.list(dishLambdaQueryWrapper);
//        return R.success(list);
//    }

    /**
     * 根据分类查询菜品信息,含口味
     * @param dish 分类id
     * @return 菜品信息
     */
    @GetMapping("/list")
    public R<List<DishDto>> list(Dish dish){
        LambdaQueryWrapper<Dish> dishLambdaQueryWrapper = new LambdaQueryWrapper<>();
        dishLambdaQueryWrapper.eq(Dish::getCategoryId,dish.getCategoryId());
        //只查询状态为1的，也就是正在售卖的菜品
        dishLambdaQueryWrapper.eq(Dish::getStatus,1);
        List<Dish> list = dishService.list(dishLambdaQueryWrapper);
        ArrayList<DishDto> dishDtoList = new ArrayList<>();
        //通过查询数据库，将每个dishDto的categoryName完善
        for (Dish dishInList : list) {
            Long dishId = dishInList.getId();
            LambdaQueryWrapper<DishFlavor> dishFlavorLambdaQueryWrapper = new LambdaQueryWrapper<>();
            dishFlavorLambdaQueryWrapper.eq(DishFlavor::getDishId,dishId);
            List<DishFlavor> dishFlavorList = dishFlavorService.list(dishFlavorLambdaQueryWrapper);
            DishDto dishDto = new DishDto();
            BeanUtils.copyProperties(dishInList,dishDto);
            dishDto.setFlavors(dishFlavorList);
            dishDtoList.add(dishDto);
        }
        return R.success(dishDtoList);
    }
}
