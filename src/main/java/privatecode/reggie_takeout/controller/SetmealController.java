package privatecode.reggie_takeout.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import privatecode.reggie_takeout.common.R;
import privatecode.reggie_takeout.dto.SetmealDto;
import privatecode.reggie_takeout.entity.Category;
import privatecode.reggie_takeout.entity.Dish;
import privatecode.reggie_takeout.entity.Setmeal;
import privatecode.reggie_takeout.service.CategoryService;
import privatecode.reggie_takeout.service.SetmealDishService;
import privatecode.reggie_takeout.service.SetmealService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/setmeal")
public class SetmealController {
    private SetmealService setmealService;
    private SetmealDishService setmealDishService;
    private final CategoryService categoryService;

    @Autowired
    public SetmealController(SetmealService setmealService, SetmealDishService setmealDishService, CategoryService categoryService) {
        this.setmealService = setmealService;
        this.setmealDishService = setmealDishService;
        this.categoryService = categoryService;
    }

    @PostMapping
    public R<String> save(@RequestBody SetmealDto setmealDto){
        setmealService.saveWithDish(setmealDto);
        return R.success("保存成功");
    }

    /**
     * 套餐分页查询
     * @param page 第几页
     * @param pageSize 每页多少条信息
     * @param name 模糊查询的关键字
     * @return 分页数据
     */
    @GetMapping("/page")
    public R<Page<SetmealDto>> page(int page,int pageSize,String name){
        //获取到套餐信息
        Page<Setmeal> setmealPage = new Page<>(page, pageSize);
        Page<SetmealDto> setmealDtoPage = new Page<>();
        LambdaQueryWrapper<Setmeal> setmealLambdaQueryWrapper = new LambdaQueryWrapper<>();
        setmealLambdaQueryWrapper.like(name!=null,Setmeal::getName,name);
        setmealLambdaQueryWrapper.orderByDesc(Setmeal::getUpdateTime);
        setmealService.page(setmealPage,setmealLambdaQueryWrapper);
        //将套餐信息的分页数据全部拷贝到dto中
        BeanUtils.copyProperties(setmealPage,setmealDtoPage,"records");
        ArrayList<SetmealDto> setmealDtos = new ArrayList<>();
        //根据每个套餐的分类id查到分类名，放入dto中
        for (Setmeal record : setmealPage.getRecords()) {
            Long categoryId = record.getCategoryId();
            Category category = categoryService.getById(categoryId);
            if(category!=null){
                String categoryName = category.getName();
                SetmealDto setmealDto = new SetmealDto();
                BeanUtils.copyProperties(record,setmealDto);
                setmealDto.setCategoryName(categoryName);
                setmealDtos.add(setmealDto);
            }
        }
        //将dto数据放入分页数据中
        setmealDtoPage.setRecords(setmealDtos);
        return R.success(setmealDtoPage);
    }

    @DeleteMapping
    public R<String> deleteSetmeal(@RequestParam List<Long> ids){
        setmealService.removeWithDish(ids);
        return R.success("删除成功");
    }

    /**
     * 批量停售起售
     * @param status 修改状态
     * @param ids 套餐id
     * @return 修改情况
     */
    @PostMapping("/status/{status}")
    public R<String> updateStatus(@PathVariable Integer status, @RequestParam List<Long> ids){
        LambdaQueryWrapper<Setmeal> setmealLambdaQueryWrapper = new LambdaQueryWrapper<>();
        setmealLambdaQueryWrapper.in(Setmeal::getId,ids);
        for (Setmeal setmeal : setmealService.list(setmealLambdaQueryWrapper)) {
            setmeal.setStatus(status);
            setmealService.updateById(setmeal);
        }
        return R.success("修改成功");
    }

    /**
     * 根据分类查询套餐信息
     * @param setmeal 分类id
     * @return 菜品信息
     */
    @GetMapping("/list")
    public R<List<Setmeal>> list(Setmeal setmeal){
        LambdaQueryWrapper<Setmeal> setmealLambdaQueryWrapper = new LambdaQueryWrapper<>();
        setmealLambdaQueryWrapper.eq(Setmeal::getStatus,setmeal.getStatus());
        setmealLambdaQueryWrapper.eq(Setmeal::getCategoryId,setmeal.getCategoryId());
        List<Setmeal> list = setmealService.list(setmealLambdaQueryWrapper);
        return R.success(list);
    }
}
