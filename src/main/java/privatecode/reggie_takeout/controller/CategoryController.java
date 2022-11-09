package privatecode.reggie_takeout.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import privatecode.reggie_takeout.common.R;
import privatecode.reggie_takeout.entity.Category;
import privatecode.reggie_takeout.entity.Employee;
import privatecode.reggie_takeout.service.CategoryService;

import java.util.List;

@RestController
@RequestMapping("category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    /**
     * 新增菜品/套餐分类
     * @param category 前端获取的分类数据
     * @return 返回保存情况
     */
    @PostMapping
    public R<String> save(@RequestBody Category category){
        categoryService.save(category);
        return R.success("新增分类成功");
    }

    /**
     * 获取分页信息
     * @param page 当前第几页
     * @param pageSize 每页多少条数据
     * @return 分页信息与数据
     */
    @GetMapping("/page")
    public R<Page<Category>> getPage(int page, int pageSize){
        Page<Category> categoryPage = new Page<>(page, pageSize);
        LambdaQueryWrapper<Category> categoryLambdaQueryWrapper = new LambdaQueryWrapper<>();
        categoryLambdaQueryWrapper.orderByAsc(Category::getSort);
        categoryService.page(categoryPage,categoryLambdaQueryWrapper);
        return R.success(categoryPage);
    }

    /**
     * 根据id删除分类，但是关联了菜品和套餐的分类不能删除
     * @param ids 分类的id
     * @return 处理情况
     */
    @DeleteMapping
    public R<String> delete(Long ids){
        categoryService.remove(ids);
        return R.success("删除成功");
    }

    /**
     * 根据id修改分类的信息
     * @param category 前端传来的分类实体
     * @return 返回修改结果
     */
    @PutMapping
    public R<String> update(@RequestBody Category category){
        categoryService.updateById(category);
        return R.success("修改成功");
    }

    /**
     * 获取分类数据
     * @param category 分类信息
     * @return 分类数据
     */
    @GetMapping("/list")
    public R<List<Category>> list(Category category){
        //添加条件构造器
        LambdaQueryWrapper<Category> categoryLambdaQueryWrapper = new LambdaQueryWrapper<>();
        //添加条件
        categoryLambdaQueryWrapper.eq(category.getType()!=null,Category::getType,category.getType());
        //添加排序条件
        categoryLambdaQueryWrapper.orderByAsc(Category::getSort);
        categoryLambdaQueryWrapper.orderByDesc(Category::getUpdateTime);
        List<Category> list = categoryService.list(categoryLambdaQueryWrapper);
        return R.success(list);
    }
}
